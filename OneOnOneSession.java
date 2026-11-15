public class OneOnOneSession extends TutoringSession{
private String roomNumber;

public OneOnOneSession(String id,String title, int hours ,String subject , Tutor tutor , String roomNumber)
{
super(id , title , hours , subject , tutor);
this.roomNumber=roomNumber;
}

public OneOnOneSession(OneOnOneSession other){
super( other) ;
 this.roomNumber=other.roomNumber;
}

public double calculateFee()
{return super.calculateFee() + 15;} 

public void setRoomNumber(String roomNumber)
{this.roomNumber=roomNumber;}

public String getRoomNumber()
{return roomNumber;}


public String toString()
{
return super.toString() + "\nRoom number:" + roomNumber;
}
}





