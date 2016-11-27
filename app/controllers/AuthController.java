package controllers;

import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.*;
import play.mvc.*;

import java.util.Objects;
import java.util.Optional;
import javax.inject.Inject;

import models.User;

public class AuthController extends Controller {

	 private static Form<User> userForm;
	 private static Form<User> loginForm;
    
    @Inject 
    public AuthController(FormFactory formFactory) {
    	 this.userForm  = formFactory.form(User.class);
         this.loginForm = formFactory.form(User.class);
    }

    public Result login() {
    	return ok(login.render());
    }

    public Result logout() {
        session().clear();
        return redirect(routes.AuthController.login());
    }

    public Result authenticate() {
    	Form<User> boundForm = loginForm.bindFromRequest();    
    	
    	if(loginForm.hasErrors()) 
        {
    		return badRequest(login.render());
        } 
        else 
        {	
           session("email", boundForm.get().email);
           return redirect(routes.Dashboard.index());
        }
    }

    public Result signup() {
    	return ok(signup.render());
    }

    public Result newUser() {
    	Form<User> boundForm = userForm.bindFromRequest();    
    	if(loginForm.hasErrors()) 
        {
          return badRequest(login.render());
        }
        else
        {
          User user = boundForm.get();
          Logger.info ("User = " + user.toString());
          user.save();
          return redirect(routes.AuthController.login());
        }
    }

}

