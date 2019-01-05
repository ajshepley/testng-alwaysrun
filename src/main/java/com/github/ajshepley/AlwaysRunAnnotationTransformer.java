package com.github.ajshepley;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.IAnnotationTransformer;
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
import org.testng.annotations.ITestAnnotation;

/**
 * AlwaysRunAnnotationTransformer
 *
 * This listener will adjust any Before/After test configurations to ensure that alwaysRun
 * is set to true.
 */
public class AlwaysRunAnnotationTransformer implements IAnnotationTransformer {

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

  @Override
  public void transform(
      final ITestAnnotation annotation,
      final Class testClass,
      final Constructor testConstructor,
      final Method testMethod
  ) {
    final Annotation[] annotations = testClass.getAnnotations();
    Stream.of(annotations)
        .forEach(this::adjustAnnotation);
  }

  private void adjustAnnotation(final Annotation annotation) {
    final Class<? extends Annotation> annotationClass = annotation.getClass();

    if (CONFIGURATION_ANNOTATIONS.contains(annotationClass)) {
      System.out.println("Setting alwaysRun on annotation " + annotation.toString() + " to true.");

      try {
        final Field alwaysRunField = annotationClass.getField("alwaysRun");
        alwaysRunField.setBoolean(annotation, true);
      } catch (final NoSuchFieldException | IllegalAccessException e) {
        System.out.println("Could not set alwaysRun field on annotation: " + annotation.toString() + ". Exception: " + e);
      }
    }
  }
}
