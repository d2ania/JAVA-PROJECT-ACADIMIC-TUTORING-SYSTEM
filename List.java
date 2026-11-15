public class List<T>{

private Node<T> head;
private Node<T> tail;
private String name;

public List(){

head=tail=null;
name="No Name";

}

public List(String name){

head=tail=null;
this.name=name;
}

public boolean isEmpty(){
 return head==null;

}


public void insertAtBack(T obj){   // adds new node to the end of list

Node<T> newnode=new Node<T>(obj);
if(isEmpty())
head=tail=newnode;
else{
tail.setNext(newnode);
tail=newnode;
   }
}

public T removeFromFront(){   // removes first node and returns its data

if(isEmpty())
return null;
Node<T> first=head;
if(head==tail)
head=tail=null;
else
head=head.getNext();
return first.getData();
}


public T removeFromBack(){ // removes last  node and returns its data


if(isEmpty())
return null;
Node<T> current=head;
if(head==tail){
head=tail=null;
return current.getData();
}

Node<T> previous=null;
while(current.getNext()!= null){
previous=current;
current=current.getNext();
}
previous.setNext(null);
tail=previous;
return current.getData();

}


public int size(){ // nb of nodes in the list
int count=0;

Node<T> current=head;
while( current!=null){
count++;
current=current.getNext();
}
return count;
}

public T get( int index){   // returns data at the given position (0= first node), or null if the position doesnt not exist
if(index<0 || index>=size())
return null;
Node<T> current=head;
for(int i=0; i<index; i++)
current=current.getNext();
return current.getData();
}

public T remove (int index){      // removes the node at given position without shifting only links change and return its data
if(index<0 || index>=size())
return null;
if(index==0)
return removeFromFront();
if(index==size()-1)
return removeFromBack();
Node<T> previous=head;
for(int i=0; i<index-1;i++)
previous=previous.getNext();
Node<T> current=previous.getNext();
previous.setNext(current.getNext());
return current.getData();
}

}