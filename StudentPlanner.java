import java.io.*;
import java.util.Scanner;
public class StudentPlanner { 
private String studentName;
private String studentId;
private int nbActivities;
private List<Activity> activityList;
private int capacity ;


public StudentPlanner(String name , String Id ,int cap)
{
studentName=name;
studentId = Id;
nbActivities=0;
capacity=cap;
activityList=new List<Activity>();
}

public StudentPlanner(StudentPlanner other) 
{
this.studentName = other.studentName;
this.studentId = other.studentId;
this.nbActivities = other.nbActivities;
this.nbActivities = 0;
this.capacity=other.capacity;
this.activityList=new List<Activity>();
for(int i = 0 ; i < other.nbActivities ; i++){
addActivity(other.activityList.get(i));
}
}


public Activity addActivity(Activity A)
{
if(nbActivities==capacity)
return null;
Activity add;
if(A instanceof OneOnOneSession)
add=new OneOnOneSession ( (OneOnOneSession)A) ;
else if(A instanceof  TutoringSession)
add=new TutoringSession(( TutoringSession)A) ;
else if(A instanceof PracticeLab)
add=new PracticeLab( (  PracticeLab)A) ;
else
return null;
activityList.insertAtBack(add);
nbActivities++;
return add;
}


public boolean removeActivity(String id){
int s = searchActivity(id);
if(s!=-1)
{
activityList.remove(s);
nbActivities--;
return true;}

return false;
}


public int searchActivity(String id)
{
for (int i = 0; i < nbActivities ; i++){
if(activityList.get(i).getactivityID().equalsIgnoreCase(id))
{
return i;
}
}
return -1;
}

public int countCompletedRecursive(int index)
{
if (index >= nbActivities || index < 0) {
return 0;}

int completed = 0;

if(activityList.get(index) instanceof PracticeLab)
{
PracticeLab lab = (PracticeLab) activityList.get(index);
if (lab.isPassed()) 
{completed = 1;}
}
return completed + countCompletedRecursive(index + 1);
}

public void displayAll() {
if (nbActivities == 0) {
System.out.println("No activities available.");
return;
}

System.out.println("Student Name: " + studentName);
System.out.println("Student ID: " + studentId);
System.out.println("----------------------------");
for (int i = 0; i < nbActivities; i++) {
System.out.println("Activity " + (i + 1) + ":" +activityList.get(i));
}
}

public double calculateStudentFees() {
double fee= 0;
for (int i = 0; i < nbActivities; i++) 
{
fee += activityList.get(i).calculateFee();
}
return fee;
}

public void setStudentName(String n)
{studentName=n;}



public String getStudentName()
{return studentName;}
 
public void setStudentId(String i)
{studentId=i;}



public String getStudentId()
{return studentId;}

public void setStudentPlanner(String name , String Id ,int cap)
{
studentName=name;
studentId=Id;
nbActivities=0;
capacity=cap;
activityList=new List<Activity>();
}

public int getNbActivities() {
return nbActivities;
}

public List<Activity> getActivityList() {
return activityList;
}

public int getCapacity()
{return capacity;};

public static StudentPlanner findStudent(StudentPlanner[] s ,  int nbS , String id)
{
for(int i=0 ; i<nbS ; i++){
if(s[i].getStudentId().equals(id))
{return s[i];}
}
return null;
}


public boolean saveToFile(String fileName)
{
try{
File outFile=new File (fileName);
FileOutputStream fos= new FileOutputStream(outFile);
PrintWriter pw=new PrintWriter(fos);
for(int i=0 ;i<nbActivities ;i++)
{
Activity a = activityList.get(i);
String line=a.getactivityID() +";"+a.gettitle()+";" +a.getdurationHours() ;
if (a instanceof OneOnOneSession)
{
OneOnOneSession o= (OneOnOneSession)a;
line ="O;" +line +";"+ o.getSubject() +";"+o.getTutor().getTutorID()+ ";"+o.getRoomNumber( ) ;   
}
else if(a instanceof TutoringSession)
{
TutoringSession t=(TutoringSession)a;
line ="G;" +line + ";" +t.getSubject() + ";"+t.getTutor().getTutorID();
}
else if(a instanceof PracticeLab)
{
PracticeLab lab=(PracticeLab)a;
line= "L;" +line +";" +lab.getlabSoftware()+";"+lab.isPassed();
}
pw.println(line);
}
pw.close();
return true;
}
catch(FileNotFoundException e)
{
System.out.println("Error! the file " +fileName+" cannot be created ");
return false;
}


}

public int loadFromFile (String fileName,Tutor[] tutors,int nbT)throws FileNotFoundException
{
Scanner input=new Scanner(new File(fileName));
int loaded=0;
try
{
while(input.hasNextLine())
{
String[] p= input.nextLine().split(";") ;
int hours=Integer.parseInt(p[3]);
if(searchActivity(p[1]) == -1) {
Activity a=null ;
if(p[0].equals("L"))
{
a =addActivity(new PracticeLab(p[1],p[2],hours , p[4] , p[5].equals("true") ) );
}
else
{
Tutor tutor= Tutor.findTutor(tutors,nbT,p[5]) ;
if (tutor!= null&&tutor.getNbSessions()< tutor.getCapacity())
{
if(p[0].equals("O"))
{a = addActivity(new OneOnOneSession(p[1], p[2], hours, p[4], tutor, p[6]));}
else
{a = addActivity(new TutoringSession(p[1], p[2], hours, p[4], tutor));}
if(a !=null)
{tutor.addSession((TutoringSession) a);}


}

}
if(a!= null)
loaded++ ;
}

}
}
finally
{
input.close();
}
return loaded;
}




public String toString() {
String s="";
if (nbActivities == 0) {
s="No activities available.";
}
for (int i = 0; i < nbActivities; i++) {
s+="Activity " + (i + 1) + ":" +activityList.get(i) +"\n";}

return "Student: " + studentName + " , ID: " + studentId + "\nNumber of Activities: " + nbActivities + "\nTotal Fees: " + calculateStudentFees()+ "\nStudent activities:" + s;
}


}










