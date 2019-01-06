package com.github.ajshepley;

import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlClass;

/**
 * AlwaysRunAnnotationTestListener
 *
 * This listener will adjust any Before/After test configurations to ensure that alwaysRun is set to
 * true.
 *
 * It will run on all tests provided the listener is specified in TestNG.xml or added to any test
 * via `@Listeners(AlwaysRunAnnotationTestListener)`
 *
 * @see AnnotationAdjuster
 * @see AlwaysRunAnnotationTransformer
 */
public class AlwaysRunAnnotationTestListener implements ITestListener {

  private final AnnotationAdjuster annotationAdjuster = new AnnotationAdjuster();

  @Override
  public void onTestStart(final ITestResult result) {
    // no-op
  }

  @Override
  public void onTestSuccess(final ITestResult result) {
    // no-op
  }

  @Override
  public void onTestFailure(final ITestResult result) {
    // no-op
  }

  @Override
  public void onTestSkipped(final ITestResult result) {
    // no-op
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(final ITestResult result) {
    // no-op
  }

  @Override
  public void onStart(final ITestContext context) {
    final List<XmlClass> xmlClasses = context.getCurrentXmlTest().getXmlClasses();
    xmlClasses.stream()
      .map(XmlClass::getSupportClass)
      .forEach(this.annotationAdjuster::adjustRelevantAnnotations);
  }

  @Override
  public void onFinish(final ITestContext context) {
    // no-op
  }
}
