import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
public class ResultFrame extends JFrame implements ActionListener{
private JTextField idField;
private JButton findB;
private JButton saveB;
private JButton loadB;
private JTextArea details;
private JTextArea all;

public ResultFrame()
{
setTitle("Acadim Tutoring System - results");
setSize(800 , 480);
setLocation(700, 80);
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
Container content=getContentPane()   ;
content.setLayout( new BorderLayout( ));
JPanel top=new JPanel(new FlowLayout());
idField=new JTextField(8);
findB=new JButton("Find");
saveB=new JButton("Save planner");
loadB=new JButton("load planner");
top.add(new JLabel("student id:"));
top.add(idField);
top.add(findB);
top.add(saveB);
top.add(loadB);
content.add(top,BorderLayout.NORTH);
details=new JTextArea();
details.setEditable(false);
details.setLineWrap(true);
details.setWrapStyleWord(true);
all=new JTextArea();
all.setEditable(false);

JPanel left=new JPanel(new BorderLayout());
left.add(new JLabel("student details and fees:",JLabel.CENTER), BorderLayout.NORTH);
left.add(new JScrollPane (details),BorderLayout.CENTER);
JPanel right=new JPanel (new  BorderLayout( )) ;
right.add(new JLabel("all students (use find for more info)",JLabel.CENTER),BorderLayout.NORTH);
right.add(new JScrollPane ( all), BorderLayout.CENTER);
JPanel cen = new JPanel(  new GridLayout(1 , 2,10,  0));
cen.setBorder(BorderFactory.createEmptyBorder(5,10,10,10)) ;
cen.add(left) ;
cen.add( right);
content.add(cen , BorderLayout.CENTER );
details.setText("enter a student ID and click find");
showAllStudents();
findB.addActionListener(this ) ;
 saveB.addActionListener( this);
loadB.addActionListener(this)  ;
setVisible(true);

}


public void actionPerformed( ActionEvent event )
{
Object source = event.getSource();

if(idField.getText().trim().equals("") )
{ 
details.setText("enter a student ID and click find");
showAllStudents( );
return ;

}
StudentPlanner p= getStudent() ;
if( p ==null)
return;

if(source == findB){
showDetails(p ) ;
}
else if(source== saveB )
{
if(p.getNbActivities()==0)
{
JOptionPane.showMessageDialog (this, "nothing to save , this planner is empty");
return ;
 
}
if(p.saveToFile( p.getStudentId( ) +".txt"))
JOptionPane.showMessageDialog (this , "The planner of "+p.getStudentName() +" was saved in the file "+ p.getStudentId()+".txt") ;
else
JOptionPane.showMessageDialog (this,"ERROR: the planner could not be saved.","Save error",JOptionPane.ERROR_MESSAGE);
}
else if( source ==loadB)
{
try{
int n=p.loadFromFile( p.getStudentId()+ ".txt" , PlannerTest.tutors ,PlannerTest.nbTutors) ;
JOptionPane.showMessageDialog( this , n +"activities were loaded from the file " +p.getStudentId()+ ".txt") ;
showDetails (p);
}
catch( FileNotFoundException  e)
{
JOptionPane.showMessageDialog(this , "No saved planner file was found for this student. Save the planner first.", "file not found" ,  JOptionPane.ERROR_MESSAGE);
}
catch(NumberFormatException e)
{
JOptionPane.showMessageDialog( this ,"The saved file is not valid (a number was expected)." ,"file error",JOptionPane.ERROR_MESSAGE);}
catch(ArrayIndexOutOfBoundsException e)
{
JOptionPane.showMessageDialog(this,"The saved file is not valid (a line is incomplete).", "file error",JOptionPane.ERROR_MESSAGE) ;
}

}showAllStudents();
}

private StudentPlanner getStudent()
{
String sid =idField. getText( ).trim() ;
try
{
PlannerTest.checkId(sid);//InvalidIDException is handled here
}
catch(InvalidIDException e)
{
JOptionPane.showMessageDialog( this, e.getMessage(),"invalid ID",JOptionPane.ERROR_MESSAGE ) ;
return null;}
StudentPlanner p=StudentPlanner.findStudent(PlannerTest.students, PlannerTest.nbStudents,sid)  ;
if(p==null)
{
JOptionPane.showMessageDialog(this, "ERROR! No student is registered with ID " + sid);
}
return p;}

private void showDetails(StudentPlanner p
){
String s = p.toString()+"\n";
if(p.getNbActivities()>0)
{
s+="\nfee summary:\n" ;
for(int i=0; i<p.getNbActivities(); i++)
{
Activity a=p.getActivityList().get(i);
s += "["+(i+1) +"] "   +a.getactivityID()+ ": "+ PlannerTest.money(a.calculateFee())+"\n";}}
details.setText (s) ;
}

private void showAllStudents()
{
String s="";
for(int i=0; i<PlannerTest.nbStudents; i++)
{
StudentPlanner sp =  PlannerTest.students[i] ;
s+="ID: "+sp.getStudentId()+ "\nName: "+sp.getStudentName() +"\nActivities: "+ sp.getNbActivities() +"\n\n" ; }
all.setText(s);}

}
















