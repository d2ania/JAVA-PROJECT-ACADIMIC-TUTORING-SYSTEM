public class PracticeLab extends Activity implements Evaluable{
private static final double ratePerHour=15.0;
private String labSoftware; 
private boolean isPassed;
public PracticeLab(String id,String title,int hours,String labSoftware,boolean isPassed){
super(id,title,hours);
this.labSoftware=labSoftware;
this.isPassed=isPassed;
}
public PracticeLab(PracticeLab other)
{
super(other.activityID,other.title,other.durationHours);
this.labSoftware=other.labSoftware;
this.isPassed=other.isPassed;}

public double calculateFee(){
return durationHours*ratePerHour;
}
public double evaluate(){
return isPassed ? 100.0 : 0.0;
}
public String getlabSoftware(){
return labSoftware;
}
public void setlabSoftware(String labSoftware){
this.labSoftware= labSoftware;
}
public boolean isPassed(){
return isPassed;
}
public void setPassed(boolean passed){
this.isPassed=passed;
}
public String toString(){
return super.toString()+",software:"+labSoftware;
}
}