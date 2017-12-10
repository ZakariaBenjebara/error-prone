/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.errorprone.bugpatterns;

import com.google.errorprone.BugPattern;
import com.google.errorprone.VisitorState;
import com.google.errorprone.bugpatterns.BugChecker.MethodInvocationTreeMatcher;
import com.google.errorprone.fixes.SuggestedFix;
import com.google.errorprone.matchers.Description;
import com.google.errorprone.matchers.method.MethodMatchers.MethodNameMatcher;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.tools.javac.code.Symbol;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.Optional;

import static com.google.errorprone.BugPattern.Category.JDK;
import static com.google.errorprone.BugPattern.ProvidesFix.*;
import static com.google.errorprone.BugPattern.SeverityLevel.WARNING;
import static com.google.errorprone.matchers.Matchers.*;
import static com.google.errorprone.util.ASTHelpers.*;
import static java.util.Optional.*;

/** @author benjebarazakaria@gmail.com (Zakaria Benjebara) */
@BugPattern(
  name = "OptionalOfNullable",
  category = JDK,
  summary =
      "One should not use optional.of(nullable) use optional.ofNullable(nullable)",
  severity = WARNING,
  providesFix = REQUIRES_HUMAN_ATTENTION
)
public class OptionalOfNullable extends BugChecker implements MethodInvocationTreeMatcher {

  private static final MethodNameMatcher JAVA_OPTIONAL_OF = staticMethod().onClass("java.util.Optional").named("of");
  private static final MethodNameMatcher GUAVA_OPTIONAL_OF = staticMethod().onClass("com.google.common.base.Optional").named("of");

  @Override
  public Description matchMethodInvocation(MethodInvocationTree tree, VisitorState state) {

    if (anyOf(JAVA_OPTIONAL_OF, GUAVA_OPTIONAL_OF).matches(tree, state)) {

      Optional<Symbol> symbol = ofNullable(getSymbol(tree.getArguments().get(0)));

      if (symbol.isPresent()) {
        if (hasAnnotation(symbol.get(), Nonnull.class, state)) {
          return Description.NO_MATCH;
        }
        if (hasAnnotation(symbol.get(), Nullable.class, state)) {
          return buildDescription(tree).addFix(SuggestedFix.builder()
                  .replace(tree, "ofNullable").build()).build();
        }
      }
    }
    return Description.NO_MATCH;
  }
}
