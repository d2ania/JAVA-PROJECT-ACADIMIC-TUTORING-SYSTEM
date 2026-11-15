
//Acivity class
public abstract class Activity {
protected String activityID;
protected String title;
protected int durationHours;
//constructer
public Activity(String id,String title, int hours){
this.activityID=id;
this.title=title;
this.durationHours=hours;
}
//setters and getters 
public String getactivityID(){
return activityID;
}
public void setactivityID(String activityID){
this.activityID=activityID;
}
public String gettitle(){
return title;
}
public void settitle(String title){
this.title=title;
}
public int getdurationHours(){
return durationHours;
}
public void setdurationHours(int durationHours){
this.durationHours=durationHours;
}
public abstract double calculateFee(); 

public String toString(){
return "ID:" + activityID+", Title: " +title+ ", Hours: "+durationHours;
}
 
}

