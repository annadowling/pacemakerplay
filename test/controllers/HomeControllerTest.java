package controllers;

import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;

import org.junit.Test;
import play.mvc.Result;

public class HomeControllerTest {

    @Test
    //Testing the index method to ensure the home page of the 
    //application is rendered correctly
    public void testIndex() {
      Result result = new HomeController().index();
      assertEquals(OK, result.status());
      assertEquals("text/html", result.contentType().get());
      assertEquals("utf-8", result.charset().get());
      assertTrue(contentAsString(result).contains("Welcome to Pacemaker Web"));
    }

}
