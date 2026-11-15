import java.util.*;
public class PlannerTest
{
/* Project Description: An Academic Tutoring System where the User : 
1- Chooses a role(Student or Tutor)
2- Logs in with registered ID
3- Choose required task from Main Mneu 
Done By:
 Rajaa Alrefaai (446207403) - Dania Ahmad (446208019) - Rahaf Mohammed (446207768)
Section ID:
25474
Instructor's Name:
Malak Mohammad - Almojali
*/

static Scanner input =new Scanner(System.in);
static Tutor[]tutors= new Tutor[20]; // registered Tutors of the System
static int nbTutors=0; 

static StudentPlanner[]students=new StudentPlanner[20]; // registered Students of the System
static int nbStudents=0;

static final int PLANNER_CAPACITY=20; // Maximum number of activities one Student PLanner can hold

static final int ID_LENGTH=5; // Both ID's for Tutor and Student must have 5 digits

static String [] failedLabs= new String[100]; // will be used when tutor marks the student as "not passed" and student can see the message 
static int nbFailedLabs=0;

// activity and group tutoring sessions choices 
static String[] groupIds={"10101","10102","10103","10104"};
static String[] groupTitles={"Discrete Mathematics Workshop","Java Programming Review","Calculus Study Group","Physics Problem Solving"};
static int [] groupHours= {2,2,3,2};
static String[] groupSubjects={"Math 151","CSC 113","MATH 106","PHYS 103"};

// 1-1 private sessions
static String[] oneIds={"20101","20102","20103","20104"};
static String[] oneTitles={"Java Data Structures Review","Calculus Private Tutoring","Physics Private Help","Exam Preparation Session"};
static int[] oneHours ={3,2,1,2};
static String[] oneSubjects={"CSC 113","MATH 106","PHYS 103","MATH 151"};

// practice labs 
static String[] labIds={"30101","30102","30103","30104"};
static String[] labTitles={"Logic Practice Lab","Programming Practice Lab","Graphing Practice Lab","Physics Simulation Lab"};
static int[] labHours ={2,2,3,2};
static String[] labSoftware={"Logisim","jGrasp","GeoGebra","PhET"};

// rooms that can be reserved for 1-1
static String[] rooms={"Building 31, Room 204","Building 31,Room 210","Building 4,Room 105","Building 4,Room 112"};

//Start of Main

public static void main(String[] args )
{ 

//Registered Tutors
tutors[0]= new Tutor("90001", "Dr.Sara", 45.0);
tutors[1]= new Tutor("90002", "Prof.Khalid", 50.0);
tutors[2]= new Tutor("90003", "Eng.Nora", 40.0);
tutors[3]= new Tutor("90004", "Dr.Faisal", 35.0);
tutors[4]= new Tutor("90005", "Ms.Reem", 30.0);
nbTutors=5;

//Registered Students
students[0]= new StudentPlanner("Dania", "44001", PLANNER_CAPACITY);
students[1]= new StudentPlanner("Rajaa", "44002", PLANNER_CAPACITY);
students[2]= new StudentPlanner("Rahaf", "44003", PLANNER_CAPACITY);
nbStudents=3;
int choice;
 
 // Start of Main Menu
do{
System.out.println();
System.out.println("============================================================");
System.out.println("        ACADEMIC TUTORING AND MANAGEMENT SYSTEM ");
System.out.println("============================================================");
System.out.println("1. Student Portal");
System.out.println("2. Tutor Portal");
System.out.println("3. Open GUI Version");
System.out.println("4. Exit Program");
System.out.println("-------------------------------------------------------------");
choice=maxmin("Enter your role choice: ",1,4);
switch(choice){

//Student Portal
case 1:{
System.out.println();
System.out.println("============================================================");
System.out.println("                      STUDENT LOGIN ");
System.out.println("============================================================");
System.out.println("Registered students: ");
for (int i=0; i<nbStudents; i++){
System.out.println("- "+students[i].getStudentName()+ " (" + students[i].getStudentId()+") ");
} 
System.out.println();

//read Student ID (must be same as ID_LENGTH)
String sid;
boolean ok;
do {
System.out.println("Note: the ID must be exactly "+ ID_LENGTH+" numbers(For Example 44001)");
System.out.println("Enter Student ID: ");
sid=input.nextLine();
ok=sid.length()==ID_LENGTH;
for(int i=0 ; i< sid.length();i++)
{if(sid.charAt(i) < '0' || sid.charAt(i)>'9'){
ok=false;
break;}
}
if (!ok){
System.out.println("Invalid ID! Must be Exactly "+ ID_LENGTH+" numbers (NO LETTERS, NO SPACES, NOT MORE OR LESS OF GIVEN DIGIT NUMBER)");
   }
} while (!ok);

StudentPlanner p= StudentPlanner.findStudent(students, nbStudents, sid);
if(p==null){ // this will help us save a new student
if(nbStudents==students.length){
System.out.println("Error the System CANNOT save more students!");
break;
}
String name="";
while(name.equals("")) {
System.out.println("Enter Student Name: ");
name=input.nextLine();
if(name.equals("")){
System.out.println("This feild CANNOT be empty! ");
   }
}
students[nbStudents]= new StudentPlanner(name, sid, PLANNER_CAPACITY);
nbStudents++;
p= students[nbStudents-1];
}
System.out.println("Welcome, "+p.getStudentName()+"!");

int sChoice;
do{   // we start Student Menu
System.out.println("-------------------------------------------------------------");
System.out.println("          STUDENT PORTAL: "+ p.getStudentName().toUpperCase()+" ("+p.getStudentId()+")");
System.out.println("-------------------------------------------------------------");
System.out.println("1. Book/Add a Tutoring Session (Group) ");
System.out.println("2. Book/Add a One-on-One Private Session ");
System.out.println("3. Register for a Practice Lab ");
System.out.println("4. View My Schedule and Fee Summary (Display All) ");
System.out.println("5. Search for an Activity");
System.out.println("6. Cancel/Remove an Activity ");
System.out.println("7. View My Completed Labs");
System.out.println("8. Log out to Main Menu ");
sChoice=maxmin("Choice: ",1,8);
switch(sChoice){

// booking a group session (1) or a 1-1 (2) same steps
case 1:
case 2:{
boolean oneOnOne=(sChoice==2);
System.out.println();
String [] ids;
String [] titles;
int [] hoursList;
String [] subjects;
if (oneOnOne){ 
System.out.println("---Booking One-On-One Private Session---");
ids=oneIds;
titles=oneTitles;
hoursList=oneHours;
subjects=oneSubjects;
} else{
System.out.println("---Booking Group Tutoring Session---");
ids=groupIds;
titles=groupTitles;
hoursList=groupHours;
subjects=groupSubjects;
}
if (p.getNbActivities()==p.getCapacity()){
System.out.println("ERROR: Your planner id FULL. Please Remove an Activity first.");
break;
}

// stduent choosing activity from list
int c= chooseFromCatalog(ids, titles, hoursList, subjects);
if(c==0){
break;
}
String actId=ids[c-1];

if (p.searchActivity(actId)!=-1){
System.out.println("ERROR! You already booked this activity "+actId);
break;
}

//student sees the tutors and writes Tutor ID
System.out.println();
System.out.println("Available tutors: ");
for(int i=0; i<nbTutors; i++){
System.out.println("- "+tutors[i].getName()+" (" + tutors[i].getTutorID()+") Rate: "+money(tutors[i].getHourlyRate())+"/hr");
}
System.out.println();
String tutorId;
boolean validId;
do{
System.out.println("Note: the ID must be exactly "+ ID_LENGTH+" numbers(For Example 44001)");
System.out.println("Enter Tutor ID: ");
tutorId=input.nextLine();
validId=tutorId.length()==ID_LENGTH ;
for(int i=0 ; i< tutorId.length();i++)
{if(tutorId.charAt(i) < '0' || tutorId.charAt(i)>'9'){
validId=false;
break;}
}

if (!validId){
System.out.println("Invalid ID! Must be Exactly "+ ID_LENGTH+" numbers (NO LETTERS, NO SPACES, NOT MORE OR LESS OF GIVEN DIGIT NUMBER)");
   }
} while (!validId);

Tutor tutor= Tutor.findTutor(tutors, nbTutors, tutorId);
if(tutor==null){
System.out.println("ERROR! No Tutor is registered with ID "+tutorId);
break;
}
if (tutor.getNbSessions()==tutor.getCapacity()){
System.out.println("ERROR!" +tutor.getName()+ " has no free time slots(session list is full)");
break;
}

TutoringSession session; // student chooses a room from list
if(oneOnOne){
System.out.println();
System.out.println("Available Rooms: ");
for(int i=0; i<rooms.length; i++){
System.out.println((i+1)+"."+rooms[i]);
}

int r=maxmin("Choose a room (1-" +rooms.length+ "): ",1,rooms.length);
session =new OneOnOneSession(actId, titles[c-1],hoursList[c-1], subjects[c-1],tutor,rooms[r-1]);
} else{
session= new TutoringSession(actId, titles[c-1],hoursList[c-1], subjects[c-1],tutor);
}
Activity a=p.addActivity(session);
if(a==null){
System.out.println("ERROR:Cannot add activity");
break;
}
tutor.addSession(( TutoringSession)a);
if (oneOnOne){
System.out.println("SUCCESS: One-On-One Session "+ actId+ "booked succesfully!");
} else {
System.out.println("SUCCESS: Group Tutoring Session "+ actId+ "booked succesfully!");
}
break;
}


// register for a practice lab
case 3: {
System.out.println();
  System.out.println("--- Registering Practice lab ---");
  if(p.getNbActivities()==p.getCapacity()){
  System.out.println("your planner is full .remove an activity first.");
  break;
  }
  int c=chooseFromCatalog(labIds,labTitles,labHours,labSoftware);
  if(c==0){
  break;
  }
  String labId=labIds[c-1];
  if(p.searchActivity(labId) != -1){
  System.out.println("error; you already registered activity"+labId+".");
  break;
  }
  //every new lab start as not passed only a tutor can mark it as passed
  
 p.addActivity(new PracticeLab(labId,labTitles[c-1],labHours[c-1],labSoftware[c-1],false));
  // a new registration starts clean (no old "Not Passed" result)
  int f=findFailed(p.getStudentId()+labId);
  if (f != -1){
  failedLabs[f]="";
  }
  System.out.println("practice lab"+labId+"registered successfully!");
  System.out.println("your tutor will mark the lab as passed when you complete it");
  break;
  }
  case 4: {
  System.out.println();
  if (p.getNbActivities() == 0) {
System.out.println("Your schedule is empty. Book an activity first.");
break;
}
  System.out.println("-----------------------------------------------------------");
  System.out.println("Student planner activities for:"+ p.getStudentName().toUpperCase());
  System.out.println("-----------------------------------------------------------");
  System.out.println(p);
  System.out.println("-----------------------------------------------------------");
  System.out.println("Fee summary");
  List<Activity>list=p.getActivityList();
  for(int i=0 ; i < p.getNbActivities();i++)
  {
  System.out.println("["+(i+1)+"]"+list.get(i).getactivityID()+":"+money(list.get(i).calculateFee()));
  }
  System.out.println("-----------------------------------------------------------");
  break;
  }
  // the student chooses one of his activities and sees its details
  case 5: {
  System.out.println();
  System.out.println("--- Search Activity ---");
  int c = chooseBooked(p);
  if(c==0){
  break;
  }
   int index = p.searchActivity(p.getActivityList().get(c - 1).getactivityID());
  if (index == -1) {
System.out.println("Activity was not found.");
break;
  }
  Activity a=p.getActivityList().get(index);
  System.out.println("FOUND ACTIVITY DETAILS:");
  System.out.println(a);
  System.out.println("total fee:" + money(a.calculateFee()));
  break;
  }
  // the student chooses one of his activities and removes it
  case 6: {
  System.out.println();
  System.out.println("--- Cancel/Remove Activity ---");
 int c = chooseBooked(p);
 if(c==0){
  break;
  }
  String actId=p.getActivityList().get(c-1).getactivityID();
  int index=p.searchActivity(actId);
  if( index == -1){
  System.out.println("Activity"+actId+"was not found");
  break;
  }
  Activity a=p.getActivityList().get(index);
  // a tutoring session must also be removed from its tutor's list of sessions
  if (a instanceof TutoringSession) {
TutoringSession session = (TutoringSession) a;
    session.getTutor().removeSession(session);
    }
    // a removed lab must also lose its "Not Passed" result
  if (a instanceof PracticeLab) {
int f = findFailed(p.getStudentId() + actId);
if (f != -1) {
 failedLabs[f] = "";
}
  }
   p.removeActivity(actId);
  System.out.println("SUCCESS: Activity " + actId + " removed successfully. ");
  break;
 }
// shows how many labs the student passed 
 // and the result of every lab, with a message for the labs marked as Not Passed
  case 7:{
  System.out.println();
  System.out.println("--- My Completed Labs ---");
  System.out.println("Number of passed labs: " + p.countCompletedRecursive(0));
  
 List<Activity> list = p.getActivityList();
 int nbLabs=0;
 for(int i=0; i < p.getNbActivities(); i++)
 {
 if(list.get(i) instanceof PracticeLab){
 PracticeLab lab=(PracticeLab)list.get(i);
 nbLabs++;
 if(lab.isPassed()){
  System.out.println(nbLabs + "." + lab+",Result:- Passed");
  }else if (findFailed(p.getStudentId()+lab.getactivityID()) != -1){
  System.out.println(nbLabs + "."+ lab +" ,Result:- Not Passed");
  
     System.out.println("   MESSAGE: Your tutor reviewed this lab and you did not pass.");
     System.out.println("   Please review the material and ask your tutor for another try.");
 } else {
  System.out.println(nbLabs + "."+ lab +",Result:- Not evaluated yet");
  }
  }
  }
  if(nbLabs == 0){
  System.out.println("you have not registered for any practice lab yet.");
  }
  break;
  }
  case 8:
  System.out.println("Returning to Main Menu...");
  break;
  }
  }while (sChoice != 8);
  break;
  }
  // ======================= tutor portal =======================
  case 2: {
   System.out.println();
   System.out.println("===========================================================");
  System.out.println("                        TUTOR LOGIN");
  System.out.println("===========================================================");
  System.out.println("Registered tutors:");
  for(int i=0 ; i<nbTutors ; i++){
  System.out.println("- "+tutors[i].getName()+ " ("+tutors[i].getTutorID()+ ")");
  }
  System.out.println();
  
  // read the tutor ID:it must be exactly ID_LENGTH numbers
   
  String tid;
  boolean ok;
  do {
  System.out.println("Note: the ID must be exactly " + ID_LENGTH + " numbers(for example 90001).");
System.out.print("Enter Tutor ID: ");
tid = input.nextLine();
ok = tid.length() == ID_LENGTH;
for(int i=0 ; i< tid.length();i++)
{if(tid.charAt(i) <'0' || tid.charAt(i)>'9'){
ok=false;
break;}
}
if (!ok) {
 System.out.println("Invalid ID. It must be exactly " + ID_LENGTH + " numbers (no letters, no spaces, not more, not less).");
}
  } while (!ok);

  Tutor t = Tutor.findTutor(tutors, nbTutors, tid);
  if (t == null) {
System.out.println("ERROR: No tutor is registered with ID " + tid + ".");
break;
  }
  System.out.println("Login successful. Welcome, " + t.getName() + "!");

  int tOption;
  do {
  //tutor menu
  System.out.println();
  System.out.println("-----------------------------------------------------------");
   System.out.println("     WELCOME, " + t.getName().toUpperCase() + " (" + t.getTutorID() + ")");
  System.out.println("-----------------------------------------------------------");
  System.out.println("1. View Details of My Assigned Sessions");
  System.out.println("2. Calculate My Total Teaching Hours & Earnings");
  System.out.println("3. View Performance Rating / Evaluation Score");
  System.out.println("4. Count Completed / Passed Lab Requirements ");
  System.out.println("5. Mark a Student's Lab as Passed / Not Passed");
  System.out.println("6. Display Full Master Schedule");
  System.out.println("7. Log Out to Main Menu");
  tOption=maxmin("choice: ",1,7);
  switch (tOption){
  // the tutor chooses one of his sessions and sees its details
  case 1:{
  System.out.println();
  System.out.println("--- Tutor Session Lookup ---");
  if (t.getNbSessions()==0){
  System.out.println("You have no assigned sessions yet.");
  break;
  }
  List<TutoringSession>list=t.getSessions();
  
   // owners[i] = the name of the student who booked session i (found by checking every planner)
   String[]owners=new String[t.getNbSessions()];
   System.out.println("your sessions:");
   for(int i=0 ; i<t.getNbSessions() ; i++){
   owners[i]="unknown";
   for(int s=0 ; s < nbStudents; s++){
   for(int k=0;  k < students[s].getNbActivities(); k++){
   if (students[s].getActivityList().get(k)==list.get(i)){
   owners[i]=students[s].getStudentName();
   }
   }
   }
   System.out.println((i+1)+". [" + list.get(i).getactivityID() + "] " + list.get(i).gettitle() + " (Student: " + owners[i] + ")");
    }
    System.out.println("0. Cancel");
    int c=maxmin("Choose a session (0-" + t.getNbSessions() + "): ", 0, t.getNbSessions());
    if(c == 0){
    break;
    }
    System.out.println("SESSION FOUND:");
  System.out.println(list.get(c - 1));
  System.out.println("Student: " + owners[c - 1]);
  System.out.println("Session Rate: " + money(list.get(c - 1).calculateFee()));
  break;
 }

case 2://Calculate My Total Teaching Hours & Earnings(Tutor menu)
{
System.out.println("------ Teaching hours and earning caculation ------");
int tH=0; //total teaching hours
double bE=0; //tutor base earning
double rF=0; //room fees
List<TutoringSession> list=t.getSessions();
for(int i=0 ; i< t.getNbSessions() ; i++){
tH+=list.get(i).getdurationHours();
bE+=list.get(i).getdurationHours() * t.getHourlyRate();
rF+=list.get(i).calculateFee() - list.get(i).getdurationHours() * t.getHourlyRate();
}
System.out.println();
System.out.println(t);
System.out.println("Rate: " + money(t.getHourlyRate()) + "/hr");
System.out.println("Total allocated hours: "+tH+" hrs");
System.out.println("Base session earnings: "+money(bE)) ;
System.out.println("Room fees: "+money(rF));
System.out.println("Total Session Fees: "+money(bE + rF));
break;
}


case 3:{//View Performance Rating / Evaluation Scor(Tutor menu)
System.out.println("\n------ Quality Evaluation Rating ------\n");
Evaluable e= t; //(up casting)- from Tutor to Evaluable (poly)
String ti;
String stat;
if(e.evaluate()>= 4.5)
ti="Premium tutor";
else
ti="Standerd tutor";
System.out.println("Tutor rating (" + t.getName() +") :" + e.evaluate() + "/5.0 (" + ti + ")");
for(int j=0 ; j<nbStudents ; j++){
StudentPlanner sp=students[j];
List<Activity> act=sp.getActivityList();

for(int i=0;i< sp.getNbActivities() ; i++){
Activity a=act.get(i); //polymorphism
if( a instanceof Evaluable){
Evaluable ev= (Evaluable) a;//(down casting)- from Activity to Evaluable (poly)
if(ev.evaluate()==100)
stat="Passed";
else
stat="Not passed";
System.out.println("Practice lab performance score(" + a.getactivityID() + "," + sp.getStudentName() + "):" +ev.evaluate() + "%(" + stat+ ")");
}
}
}
break;
}

case 4://Count Completed / Passed Lab Requirements(Tutor menu)
{
System.out.println("------Task completion scan------");
int totalAct=0;
for(int j=0 ; j<nbStudents ; j++){
totalAct+=students[j].getNbActivities();}
if(totalAct==0)
{System.out.println("No activities in the system yet , A student must book activities first!");
break;}
int total=0;
int count=0;
for(int i =0 ; i<nbStudents ; i++){
count = students[i].countCompletedRecursive(0);
if(students[i].getNbActivities() >0){
System.out.println(students[i].getStudentName() + ":" + count);}
total+=count;
}
System.out.println("Total completed tasks / Passed labs in System :" + total);
break;}


case 5:{//Mark a Student's Lab as Passed / Not Passed(Tutor menu)
System.out.println("------Mark a student's lab as Passed / Not Passed------");
String sID;
boolean valid;
do{
System.out.println("Note: ID must be exactly " + ID_LENGTH + " numbers(for example 44001)");
System.out.println("Enter student ID");
sID=input.nextLine();
valid=sID.length() == ID_LENGTH;
for(int i=0; i<sID.length() ; i++)
{
if (sID.charAt(i)<'0'||sID.charAt(i) >'9')
valid=false;
}
if(!valid)
{System.out.println("Invalid ID , It must be exactly " + ID_LENGTH  + " number(no letters , no spaces , not more ,not less");}
}while(!valid);

StudentPlanner sp= StudentPlanner.findStudent(students , nbStudents , sID);
if(sp == null)
{System.out.println("ERROR! No student is registered with ID " + sID);
break;}

List<Activity> list = sp.getActivityList();
int[] labP=new int[sp.getNbActivities()]; //lab position
int nbLabs=0;
String stat="";
System.out.println("Labs of " + sp.getStudentName() + ":");
for(int i=0 ;i <sp.getNbActivities() ; i++)
{if(list.get(i) instanceof PracticeLab)
{PracticeLab lab=(PracticeLab) list.get(i);
labP[nbLabs]=i;
nbLabs++;
if(lab.isPassed()){
stat="Passed";}
else if(findFailed(sp.getStudentId() + lab.getactivityID()) != -1)
{stat="Not Passed";}
else
{stat="Not evaluated yet";}

System.out.println(nbLabs +"." + lab + "_" + stat);
}
}

if(nbLabs==0){
System.out.println("This student han no practice labs.");
break;}

System.out.println("0. cancel");
int c=maxmin("Choose a lab(0-" + nbLabs + "): " , 0 , nbLabs);
if(c == 0)
{break;}

PracticeLab chosen=(PracticeLab) list.get(labP[c-1]);
String key= sp.getStudentId() + chosen.getactivityID();

System.out.println("Course: " +chosen.gettitle() + "("+chosen.getactivityID() + ")");
System.out.println("Student: " +sp.getStudentName());
System.out.println("1.Passed");
System.out.println("2.Not Passed");
int result = maxmin("Result (1-2):", 1, 2);
int f=findFailed(key);
if(result ==1)
{chosen.setPassed(true);
if(f!=-1){ failedLabs[f]="";}
System.out.println("SUCCESS: " + chosen.gettitle()+ "is now marked as Passed for: " + sp.getStudentName() ) ;
}
else{
chosen.setPassed(false);
if(f==-1 && nbFailedLabs< failedLabs.length){
failedLabs[nbFailedLabs]=key;
nbFailedLabs++;
}
System.out.println("SUCCESS: " + chosen.gettitle()+ "is now marked as NOT Passed for: " + sp.getStudentName() ) ;
System.out.println("A message message will be shown to the student in \"View my completed labs\"" );
} 
break;
}


case 6 ://(Tutor menu)Display Full Master Schedule
System.out.println(t);
break;

case 7://(Tutor menu)Log Out to Main Menu
System.out.println("Returning to main menu....");
break;
}
}while(tOption!=7);
break;
}
case 3:// Open GUI Version(main menu)
System.out.println("Opening the GUI...");
new InputFrame();
break;

case 4://Exit Program(main menu)
System.out.println("Exiting system,Thank you for using Academic Planner!");
System.exit(0);
break;
}
}while(choice!=4);
}

//--------------------------methods------------------------------

// check that an ID is exactly ID_LENGTH numbers otherwise throws our user defined exception
//the exception is not handeled here it is propagated to the caller method(the GUI)
public static void checkId(String id)throws InvalidIDException{
boolean ok=id.length()==ID_LENGTH;
for(int i=0 ;i< id.length(); i++){
if(id.charAt(i)<'0'  || id.charAt(i)>'9'){
ok=false;
break;
}
}
if(!ok)
throw new InvalidIDException("Invalid ID! it must be exactly "+ID_LENGTH+" numbers(no letters,no spaces).");
}


public static int findFailed(String key){ //search for (not passed) labs by the student and lab ID.
for(int i=0 ; i<nbFailedLabs ; i++)
if(failedLabs[i].equals(key)){
return i;
}
return -1;
}


public static int chooseFromCatalog(String[] id , String[] title , int[] hours , String[] extra){//shows the activity choices(and return the number they pick)
System.out.println("Choose an activity:");
for(int i=0 ;i<id.length ; i++){
System.out.printf("%d .[%s] %s - %d hrs - %s\n" ,(i + 1) , id[i] , title[i] , hours[i] , extra[i]);
}
System.out.println("0.Cancel");
return maxmin("Your choice (0-"+id.length +"): ",0,id.length);
}

public static int chooseBooked(StudentPlanner p){//shows the activities the student already booked
if(p.getNbActivities()==0){
System.out.println("You have no activities yet");
return 0;
}
System.out.println("Your activities:");
for(int i=0 ;i< p.getNbActivities() ;i++)
{System.out.println(i + 1 +". [" + p.getActivityList().get(i).getactivityID()+"] " +p.getActivityList().get(i).gettitle());}
System.out.println("0.Cancel");
return maxmin("Your choice (0-"+ p.getNbActivities() +"): ",0,p.getNbActivities());
}


public static String money(double amount)//format for the money, ex $10.00
{
int cent=(int)Math.round(amount *100);
String m="" + cent %100;
if(cent %100 <10)
{m+="0" ;}
return "$" + cent/100+ "."+m;}



public static int maxmin(String message , int min , int max){//reads a number between min and max (safety net)
while(true){
System.out.println(message);

String answer=input.nextLine();
boolean valid=answer.length()>0;
int n=0;

for(int i=0 ; i<answer.length() ; i++)
{
char ch=answer.charAt(i);
if(ch<'0' || ch>'9')
{valid=false;
break;}
if (n <= max) {
n = n * 10 + (ch - '0');
}}
if(!valid)
{System.out.println("Invalid input, please enter a number");}
else
{if(n>=min && n<=max)
{return n;}
System.out.println("Invalid choice, Please enter a number between " + min +" and "+max+".");
}
}
}

}







