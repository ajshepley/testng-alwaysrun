package com.github.ajshepley;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

// Note that the listener only needs to be present on a single unit test within the executed suite
// for it to apply to all unit tests.
@Listeners(AlwaysRunAnnotationTestListener.class)
@SuppressWarnings("unused")
public class ExampleTest {

  @Test
  public void testSuccess1() {
    assertTrue(true);
  }

  @Test
  public void testSuccess2() {
    assertTrue(true);
  }

  @Test
  public void testSuccess3() {
    assertTrue(true);
  }

  @BeforeMethod
  public void aBeforeMethod() {
    System.out.println("Executing ExampleTest beforeMethod");
  }

  @AfterMethod
  public void anAfterMethod1() {
    System.out.println("Executing ExampleTest afterMethod");
  }

  @AfterClass
  public void anAfterClass() {
    System.out.println("Executing ExampleTest afterClass");
  }
}
