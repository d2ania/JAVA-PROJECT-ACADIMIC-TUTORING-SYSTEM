public class Node<T>{

private T data;
private Node<T> next;

public Node(T obj){
data=obj;
next=null;
}

public void setData(T obj){
data=obj;
}

public T getData(){
 return data;
 }

public void setNext(Node<T> link){
next=link;

}

public Node<T> getNext(){
return next;
}




}