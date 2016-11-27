package controllers;

import models.Activity;
import models.User;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Dashboard extends Controller
{
	
  public Result index()
  {
    String email = session().get("email");
    User user = User.findByEmail(email);
    return ok(dashboard_main.render(user.activities));
  }
  
  public Result uploadActivityForm()
  {
    return ok(dashboard_uploadactivity.render());
  }  

  public Result submitActivity()
  {
	Form<Activity> boundForm = Form.form(Activity.class).bindFromRequest();    
    Activity activity = boundForm.get();
    
    if(boundForm.hasErrors()) 
    {
      return badRequest();
    }

    String email = session().get("email");
    User user = User.findByEmail(email);

    user.activities.add(activity);
    user.save();
    return redirect (routes.Dashboard.index());
  }
}
