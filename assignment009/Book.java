package assignment009;

public class Book implements Comparable<Book> {
String name;
int id;
boolean isBook;

Book(String name,int id){
	this.name=name;
	this.id=id;
}

@Override
public int compareTo(Book o) {
	return Integer.compare(this.id,o.id);
}
}
