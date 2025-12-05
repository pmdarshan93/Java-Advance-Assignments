package assignment008;

import java.util.ArrayList;
import java.util.Objects;

public class Employee {
	String name;
	int id;


	Employee(String name,int id){
		this.name=name;
		this.id=id;
	}

	
	public int hashCode() {
		return Objects.hash(name,id);
	}
	
	public boolean equals(Object o) {
		Employee e=(Employee) o;
		return this.id==e.id;
	}
	
	public String toString() {
		return "Name : "+name+"\t Id : "+id;

	}
	
}
