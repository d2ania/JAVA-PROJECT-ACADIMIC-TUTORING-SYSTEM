import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//phase 2:first GUI frame-the user enters the details of the activity to add to a student planner
public class InputFrame extends JFrame implements ActionListener{
private JTextField idField;
private JTextField nameField;
private JComboBox<String> typeBox;
private JComboBox<String> activityBox;
private JComboBox<String> tutorBox;
private JComboBox<String> roomBox;
private JButton addButton;
private JButton resultsButton;
public InputFrame(){
setTitle("Academic Tutoring System -add Activity");
setSize(620,400);
setLocation(60,80);
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
Container contentPane=getContentPane();
contentPane.setLayout(new BorderLayout());
JLabel title = new JLabel("Add an Activity to a Student Planner",JLabel.CENTER);
title.setFont(new Font("Arial",Font.BOLD,18));
title.setBorder(BorderFactory.createEmptyBorder(12,0,0,0));
contentPane.add(title,BorderLayout.NORTH);
// the form: a label and a component in every row
JPanel form=new JPanel (new GridLayout(6,2,10,12));
form.setBorder(BorderFactory.createEmptyBorder(15,20,10,20));
idField=new JTextField(10);
nameField= new JTextField(15);
typeBox= new JComboBox<String>();
typeBox.addItem("Group Tutoring Session");
typeBox.addItem("One-on-One Private Session");
typeBox.addItem("Practice Lab ");
activityBox=new JComboBox<String>();
tutorBox=new JComboBox<String>();
for(int i=0; i<PlannerTest.nbTutors ; i++){
tutorBox.addItem(PlannerTest.tutors[i].getName() + " (" + PlannerTest.tutors[i].getTutorID() + ") " + PlannerTest.money(PlannerTest.tutors[i].getHourlyRate()) + "/hr");
}
roomBox=new JComboBox<String>();
for(int i=0 ; i<PlannerTest.rooms.length ; i++){
roomBox.addItem(PlannerTest.rooms[i]);
}
form.add(new JLabel("Student ID (5 numbers):"));
form.add(idField);
form.add(new JLabel("Student Name(new students only):"));
form.add(nameField);
form.add(new JLabel("Activity Type:"));
form.add(typeBox);
form.add(new JLabel("Activity:"));
form.add(activityBox);
form.add(new JLabel("Tutor (sessions only):"));
form.add(tutorBox);
form.add(new JLabel("Room (one-on-one only):"));
form.add(roomBox);
contentPane.add(form, BorderLayout.CENTER);

//the buttons 
JPanel buttons= new JPanel(new FlowLayout());
addButton=new JButton("Add Activity");
resultsButton=new JButton("View Results");
buttons.add(addButton);
buttons.add(resultsButton);
buttons.setBorder(BorderFactory.createEmptyBorder(0,0,12,0));
contentPane.add(buttons,BorderLayout.SOUTH);


fillActivities();
typeBox.addActionListener(this);
addButton.addActionListener(this);
resultsButton.addActionListener(this);
setVisible(true);
}
// fills the activity list according to the selected type, and enables only the components that are needed
private void fillActivities(){
activityBox.removeAllItems();
int type=typeBox.getSelectedIndex();
String[] ids;
String[] titles;
int[] hours;
if(type==0){
ids = PlannerTest.groupIds;
titles = PlannerTest.groupTitles;
hours = PlannerTest.groupHours;
}
else if(type==1){
ids = PlannerTest.oneIds;
titles = PlannerTest.oneTitles;
hours = PlannerTest.oneHours;
}
else{
ids = PlannerTest.labIds;
titles = PlannerTest.labTitles;
hours = PlannerTest.labHours;
}
for(int i=0 ; i<ids.length ; i++){
activityBox.addItem("[" + ids[i] + "] " + titles[i] + " - " + hours[i] + " hrs");
}
tutorBox.setEnabled(type!=2);
roomBox.setEnabled(type==1);
}
public void actionPerformed(ActionEvent event){
Object source=event.getSource();
if(source==typeBox){
fillActivities();
}
else if(source==addButton){
addNewActivity();
}
else if (source==resultsButton){
new ResultFrame();
}
}

// same steps as booking in the console menu (cases 1, 2 and 3 of the student portal)
private void addNewActivity(){
String sid = idField.getText().trim();
try{
PlannerTest.checkId(sid); // throws our InvalidIDException if the ID is not 5 numbers
}
catch(InvalidIDException e){
JOptionPane.showMessageDialog(this, e.getMessage(), "Invalid ID", JOptionPane.ERROR_MESSAGE);
return;
}
StudentPlanner p=StudentPlanner.findStudent(PlannerTest.students,PlannerTest.nbStudents,sid);

if(p==null){// a new student is registered
String name=nameField.getText().trim();
if(name.equals("")){
JOptionPane.showMessageDialog(this,"This is a new student ID, please enter the student name.");
return;
}
if(PlannerTest.nbStudents==PlannerTest.students.length){
JOptionPane.showMessageDialog(this, "Error the System cannot save more students!");
return;
}
p=new StudentPlanner(name, sid, PlannerTest.PLANNER_CAPACITY);
PlannerTest.students[PlannerTest.nbStudents]=p;
PlannerTest.nbStudents++;
}
if(p.getNbActivities()==p.getCapacity()){
JOptionPane.showMessageDialog(this, "ERROR: Your planner is full. Please remove an activity first.");
return;
}

int type = typeBox.getSelectedIndex();
int c = activityBox.getSelectedIndex();
String actId;
if(type==0)
actId = PlannerTest.groupIds[c];
else if(type==1)
actId = PlannerTest.oneIds[c];
else
actId = PlannerTest.labIds[c];

if(p.searchActivity(actId)!=-1){
JOptionPane.showMessageDialog(this, "ERROR! You already booked this activity " + actId);
return;
}

if(type==2){
p.addActivity(new PracticeLab(actId, PlannerTest.labTitles[c], PlannerTest.labHours[c], PlannerTest.labSoftware[c], false));
}
else{
Tutor tutor = PlannerTest.tutors[tutorBox.getSelectedIndex()];
if(tutor.getNbSessions()==tutor.getCapacity()){
JOptionPane.showMessageDialog(this, "ERROR! " + tutor.getName() + " has no free time slots (session list is full)");
return;
}
TutoringSession session;
if(type==1)
session = new OneOnOneSession(actId, PlannerTest.oneTitles[c], PlannerTest.oneHours[c], PlannerTest.oneSubjects[c], tutor, PlannerTest.rooms[roomBox.getSelectedIndex()]);
else
session = new TutoringSession(actId, PlannerTest.groupTitles[c], PlannerTest.groupHours[c], PlannerTest.groupSubjects[c], tutor);
Activity a = p.addActivity(session);
tutor.addSession((TutoringSession) a);
}
JOptionPane.showMessageDialog(this, "SUCCESS: Activity " + actId + " was added to " + p.getStudentName() + "'s planner!");

// all the fields are cleared after the addition
idField.setText("");
nameField.setText("");
typeBox.setSelectedIndex(0);
tutorBox.setSelectedIndex(0);
roomBox.setSelectedIndex(0);
}
}

