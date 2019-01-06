package com.github.ajshepley;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * AlwaysRunAnnotationTransformer
 *
 * This transformer will adjust any Before/After test configurations to ensure that alwaysRun is set to
 * true.
 *
 * You must specify this transformer manually in your testng.xml file:
 *  <suite>
 *
 *   <listeners>
 *     <listener class-name="com.github.ajshepley.AlwaysRunAnnotationTransformer" />
 *   </listeners>
 *   ...
 *
 * Or you can specify it in a service loader.
 */
public class AlwaysRunAnnotationTransformer implements IAnnotationTransformer {

  private final AnnotationAdjuster annotationAdjuster = new AnnotationAdjuster();

  @Override
  public void transform(
      final ITestAnnotation annotation,
      final Class testClass,
      final Constructor testConstructor,
      final Method testMethod
  ) {
    this.annotationAdjuster.adjustRelevantAnnotations(testClass);
  }
}
