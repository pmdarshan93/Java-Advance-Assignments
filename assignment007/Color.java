package assignment007;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Scanner;

public class Color {
	String color;
	String code;
	
	Color(String color,String code){
		this.color=color;
		this.code=code;
	}
	
	public String toString() {
		return color+" : "+code;
	}
	
	public boolean equals(Object o) {
		Color c=(Color) o;
		return this.color.equals(c.color) && this.code.equals(c.code);
	}
	
	public int hashCode() {
		return Objects.hash(this.color,this.code);
	}
public static void main(String []args) {
	Scanner sc=new Scanner(System.in);
	HashSet<Color> arr=new HashSet<>();
	arr.add(new Color("WHITE","#FFFFFF"));
	int option=0;
	while(option!=3) {
		System.out.print("1.Add color \t2.View all colors \t 3.Exit\n Enter the option : ");
		option=sc.nextInt();
		sc.nextLine();
		if(option==1) {
			System.out.print("Enter the color name : ");
			String name=sc.nextLine().toUpperCase();
			System.out.print("Enter the color Code : ");
			String code=sc.nextLine();
			arr.add(new Color(name,code));
		}
		else if(option==2) {
			Iterator itr=arr.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		}
		else if(option!=3) {
			System.out.println("Invalid option");
		}
	}
	System.out.println("THank you ");
}


}
