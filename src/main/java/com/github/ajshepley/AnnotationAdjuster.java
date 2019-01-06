package com.github.ajshepley;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

/**
 * AnnotationAdjuster
 *
 * This class will find @After and @Before configuration methods in a given class, and will
 * reflectively change their alwaysRun value(s) to true.
 *
 * If anything fails, the exception is logged.
 *
 * @see AlwaysRunAnnotationTransformer
 * @see AlwaysRunAnnotationTestListener
 */
public class AnnotationAdjuster {

  private static final Set<Class> CONFIGURATION_ANNOTATIONS = Stream.of(
      BeforeMethod.class,
      BeforeClass.class,
      BeforeGroups.class,
      BeforeSuite.class,
      BeforeTest.class,
      AfterMethod.class,
      AfterClass.class,
      AfterGroups.class,
      AfterSuite.class,
      AfterTest.class
  ).collect(Collectors.toSet());

  void adjustRelevantAnnotations(final Class testClass) {
    final Method[] classMethods = testClass.getMethods();
    this.getAnnotationsForMethods(classMethods)
        .forEach(this::adjustAnnotation);
  }

  private Stream<Annotation> getAnnotationsForMethods(final Method[] methods) {
    return Stream.of(methods)
        .map(AccessibleObject::getAnnotations)
        .flatMap(Arrays::stream);
  }

  private void adjustAnnotation(final Annotation annotation) {
    final Class<? extends Annotation> typeClass = annotation.annotationType();

    if (CONFIGURATION_ANNOTATIONS.contains(typeClass)) {
      System.out.println("Setting alwaysRun on annotation " + annotation.toString() + " to true.");

      final InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
      try {
        // AnnotationInvocationHandler is hidden.
        final Field memberValueField = invocationHandler.getClass()
            .getDeclaredField("memberValues");

        // TODO: Try using trySetAccessible() with Java9.
        memberValueField.setAccessible(true);

        @SuppressWarnings("unchecked") final Map<String, Object> memberValues =
            (Map<String, Object>) memberValueField.get(invocationHandler);

        memberValues.computeIfPresent("alwaysRun", (key, value) -> true);

        memberValueField.setAccessible(false);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        System.out.println(
            "Could not set alwaysRun field on annotation: "
            + annotation.toString()
            + ". Exception: "
            + e
        );
      }
    }
  }
}
