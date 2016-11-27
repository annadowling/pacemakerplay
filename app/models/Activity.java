package models;

import static com.google.common.base.Objects.toStringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;
import javax.persistence.*;
import com.avaje.ebean.Model;

@SuppressWarnings("serial")
@Entity
@Table(name="activity")
public class Activity extends Model
{
  @Id
  @GeneratedValue
  public Long id;
  public String location;
  public double distance;
  public Date date;
  public double duration;
  public String type;
  
  @OneToMany(cascade=CascadeType.ALL)
  public List<Location> route = new ArrayList<Location>();
  
  public Activity()
  {
  }
   
  public Activity(String type, String location, double distance, Date date, double duration)
  {
    this.type      = type;
    this.location  = location;
    this.distance  = distance;
    this.date = date;
    this.duration = duration;
  }
  
  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id)
                               .addValue(location)
                               .addValue(distance)
                               .addValue(date)
                               .addValue(duration)
                               .addValue(type)
                               .toString();
  }
  
  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type) 
          && Objects.equal(location,  other.location)
          && Objects.equal(distance,  other.distance)  
          && Objects.equal(date,  other.date) 
          && Objects.equal(duration,  other.duration) ; 
    }
    else
    {
      return false;
    }
  }
  
  @Override  
  public int hashCode()  
  {  
     return Objects.hashCode(this.id, this.type, this.location, this.distance, this.date, this.duration);  
  } 
  
  public static List<Activity> findAll()
  {
    return find.all();
  }
  
  public static Activity findById(Long id)
  {
    return find.where().eq("id", id).findUnique();
  }
  public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(String.class, Activity.class);
}
