package assignment008;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class EmployeeManagement {

	HashMap<Employee,ArrayList<Project>> map=new HashMap<>();
	
	void addEmployee(Employee e) {
		map.put(e,new ArrayList<Project>());
	}

	void addProject(Employee e,Project p) {
		map.get(e).add(p);
	}
	
	void addProject(Employee e,ArrayList<Project> arr) {
		map.get(e).addAll(arr);
	}
	
	void displayAll() {
		for(Map.Entry<Employee,ArrayList<Project>> pair:map.entrySet()){
			System.out.print(pair.getKey()+"\nProjects : "+pair.getValue()+"\n");
		}
	}
	
	public static void main(String []args){
		Employee e1=new Employee("Sree",0);
		Employee e2=new Employee("Ajmeer",1);
		ArrayList<Project> arr1=new ArrayList<>();
		ArrayList<Project> arr2=new ArrayList<>();
		Project p1=new Project("Java Assignment");
		Project p2=new Project("Advance Java concept");
		Project p3=new Project("Revise Node js ");
		Project p4=new Project("Revise Map concept");
		arr1.add(p1);
		arr1.add(p2);
		arr2.add(p3);
		arr2.add(p4);

		// Creating a Employee Management system
		EmployeeManagement zsManager=new EmployeeManagement();
		zsManager.addEmployee(e1);
		zsManager.addEmployee(e2);

		
		zsManager.addProject(e1, arr1);
		zsManager.addProject(e2, arr2);
		
		
		System.out.println("-------- Initial Hashmap of Employees -----------\n");
		zsManager.displayAll();
		
		
		System.out.println("\n\n-------- Remove project Java Assignment from Sree -----------\n");
		zsManager.map.get(e1).remove(p1);
		zsManager.displayAll();
		
		System.out.println("\n\n ------------- Assigning a additional Project To ajmeer -----------\n");
		zsManager.addProject(e2,new Project("Data base assignemnt"));
		zsManager.displayAll();
		
	}
	
}
