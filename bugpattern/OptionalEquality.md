---
title: OptionalEquality
summary: Comparison using reference equality instead of value equality
layout: bugpattern
category: GUAVA
severity: ERROR
maturity: MATURE
---

<!--
*** AUTO-GENERATED, DO NOT MODIFY ***
To make changes, edit the @BugPattern annotation or the explanation in docs/bugpattern.
-->

## The problem
Optionals should be compared for value equality using `.equals()`, and not for reference equality using `==` and `!=`.

## Suppression
Suppress false positives by adding an `@SuppressWarnings("OptionalEquality")` annotation to the enclosing element.