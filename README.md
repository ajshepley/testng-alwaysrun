# testng-alwaysrun

A Listener and AnnotationTransformer which will find TestNG configuration methods (`@AfterX` and `@BeforeX`) and change their alwaysRun fields to True.

If at least one test class in the project is marked with the Listener class, then all relevant test suite classes will be processed in this way.

```java
@Listeners(AlwaysRunAnnotationTestListener.class)
public class MyTestClass() {
  ...
}
```

An alternative annotation transformer is also provided, which can be used in your `testng.xml` to add this functionality at a per-suite level.

```xml
<suite>
  <listeners>
    <listener class-name="com.github.ajshepley.AlwaysRunAnnotationTransformer" />
  </listeners>
</suite>
```

If the field cannot be set, reflective exceptions will be logged but the test runner will continue.

____
## Rationale

The configuration methods provided by TestNG are, by default, not guaranteed to run depending on various factors.

There can be good reasons for this, but in JUnit and other test frameworks, the opposite behaviour is expected by default.

TestNG provides the `alwaysRun` field on these annotations to allow you to adjust this behaviour. It defaults to `false`.

Sometimes this cleanup or setup code is critical to the test or test resources, and so it is useful for an organization to have a way to ensure that these calls always run and do so by default.

Thus it is useful to have a Listener and Annotation Transformer to adjust this behaviour.
____

## Building and Dependencies

The included Gradle4 wrappers can be used to build the project (`./gradlew` or `.\gradlew.bat`).

Java 8 is required. See **Warning** below for more information.

This repo is meant more as an illustration of how to work around this TestNG behaviour than to serve directly as a dependency.

However, a classes dependency jar can be created using the `jar` task. This is included as a default build task.

The TestNG version can be tweaked in the `ext` block in `build.gradle`.

### Warning

When building, the following warning may appear:

```bash
WARNING: An illegal reflective access operation has occurred
...
WARNING: All illegal access operations will be denied in a future release
```

This occurs because the methodology used in changing the annotation field uses cross-module reflective accessibility changes, and therefore will not function correctly in Java 9 and later.

You can try using the flag `–illegal-access=permit` to force it to work.





____

## Project Setup

A `.idea/` directory is included, which has a variation of [Google's java style](https://google.github.io/styleguide/javaguide.html) called "GoogleStyle-Chop".

You can use that directory to instantiate the project with IntelliJ IDEA, or you can import the Gradle project in your IDE.
