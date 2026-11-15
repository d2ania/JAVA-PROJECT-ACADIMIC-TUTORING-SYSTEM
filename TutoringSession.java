// TutoringSession class
public class TutoringSession extends Activity {

private String subject;
private Tutor tutor;

public TutoringSession(String id, String title, int hours, String subject, Tutor tutor){
super(id, title,hours);
this.subject=subject;
this.tutor=tutor;}

public TutoringSession(TutoringSession other)
{
super(other.activityID,other.title  ,other.durationHours );
this.subject=other.subject;
this.tutor=other.tutor;}

public double calculateFee(){
double fee= durationHours*tutor.getHourlyRate();
return fee;}

public String getSubject(){
return subject;
}

public void setSubject(String subject){
this.subject=subject;
}

public Tutor getTutor(){
return tutor;}

public void setTutor(Tutor tutor){
this.tutor=tutor;
}

public String toString(){
return super.toString()+",subject: "+subject+" ,Tutor name: "+tutor.getName()+ " ,Tutor ID: " + tutor.getTutorID() + ", Hourly Rate: "+tutor.getHourlyRate();}
}