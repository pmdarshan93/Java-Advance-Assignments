package dataStructure;

public class Node<T> {
	T value;
	Node<T> right;
	Node<T> left;
	Node<T> parent;
	Color color;
	
	Node(T value){
		this.value=value;
		this.color=Color.RED;
	}
	
	Node(T value,Node<T> right,Node<T> left,Node<T> parent,Color color){
		this.value=value;
		this.right=right;
		this.left=left;
		this.parent=parent;
		this.color=color;
	}
	
	public String toString() {
		return value+" "+color;
	}
}


enum Color{RED,BLACK};