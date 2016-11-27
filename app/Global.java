import play.*;
import play.libs.*;
import java.util.*;
import com.avaje.ebean.*;

import models.*;
@SuppressWarnings({ "unchecked", "deprecation" })
public class Global extends GlobalSettings
{
  public void onStart(Application app)
  {
    InitialData.insert(app);
  }

  static class InitialData
  {
    public static void insert(Application app)
    {
      if (Ebean.find(User.class).findRowCount() == 0)
      {
        Map<String, List<User>> all = (Map<String, List<User>>) Yaml.load("initial-data.yml");
        Ebean.saveAll(all.get("users"));
      }
    }
  }
}
