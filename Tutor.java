// Tutor class

public class Tutor implements Evaluable{

private String tutorID;
private String name;
private double hourlyRate;
private List<TutoringSession> sessions; //linked list instead of array
private int capacity; // maximum number of sessions(like array length phase 1)
private int nbSessions;

public Tutor (String tutorID, String name, double hourlyRate)
{
this.tutorID=tutorID;
this.name=name;
this.hourlyRate=hourlyRate;
this.sessions=new List<TutoringSession>();
this.capacity=10;
this.nbSessions=0;
}

public String getTutorID(){
return tutorID;
}
public void setTutorID(String tutorID){
this.tutorID=tutorID;
}


public String getName(){
return name;
}

public void setName(String name)
{
this.name=name;}

public double getHourlyRate()
{
return hourlyRate;}

public void setHourlyRate(double hourlyRate)
{
this.hourlyRate=hourlyRate;
}

public List<TutoringSession> getSessions(){
return sessions;}

public int getCapacity(){

return capacity;
}


public int getNbSessions(){
return nbSessions;}

public boolean addSession(TutoringSession s){
if(nbSessions==capacity)
return false;
sessions.insertAtBack(s);
nbSessions++;
return true;
}



public boolean removeSession(TutoringSession session){ // / removes the given session from linked list without shifting the list only relinks nodes
for(int i=0; i<nbSessions; i++){
if (sessions.get(i)==session){
sessions.remove(i);
nbSessions--;
return true;
}
}return false;
}


public double evaluate(){
double rating=hourlyRate/10.0;
if(rating>5.0)
rating=5.0;
return rating;
}


public static Tutor findTutor(Tutor[] t , int nbT , String id)
{

for(int i=0 ; i<nbT ; i++)
{if(t[i].getTutorID().equals(id))
{return t[i];}
}
return null;

}

public String toString(){
String s="";
for (int i=0; i<nbSessions;i++){
s+="\n "+sessions.get(i);
}
return "Tutor ID: "+tutorID+" , Name: "+name+", Hourly Rate: "+hourlyRate+", Rating: "+evaluate()+ " ,Number of Sessions: "+nbSessions + "\nSessions" + s;
}

}
