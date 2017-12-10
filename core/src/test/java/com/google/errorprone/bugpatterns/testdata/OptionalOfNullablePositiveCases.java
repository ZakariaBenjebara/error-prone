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
package com.google.errorprone.bugpatterns.testdata;


import javax.annotation.Nullable;
import java.util.Optional;

/** @author benjebarazakaria@gmail.com on 10/11/17. */
public class OptionalOfNullablePositiveCases {

  private @Nullable
  String var;

  public void test(@Nullable String testStr) {
    // BUG: Diagnostic contains: Optional
    Optional.of(testStr);
  }

  public void testVarDeclaredWithNullable(@Nullable String testStr) {
    // BUG: Diagnostic contains: Optional
    Optional.of(var);
  }

  public void testVarDeclaredWithNullable() {
    @Nullable String testStr = null;
    // BUG: Diagnostic contains: ofNullable
    Optional.of(testStr);
  }
}
