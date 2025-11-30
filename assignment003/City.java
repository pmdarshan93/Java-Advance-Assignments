package assignment003;
import java.util.Arrays;
import java.util.Comparator;

public class City implements Comparator{
	String name;
	long population;
	Area area;
	int noOfAirports;
	
	City(String name,long population,double length,double breadth,int noOfAirports){
		this.name=name;
		this.population=population;
		this.area=new Area(length,breadth);
		this.noOfAirports=noOfAirports;
	}
	
//	public int compare(Object c1,Object c2) {
//		return Long.compare(c1.population,c2.population);
//	}
//	
	public String toString() {
		return ("Name : "+name+"\t Area : "+area+"\tPopulation : "+population+"\t Airports : "+noOfAirports+"\n");
	}
	
	public static void main(String[] arg) {
		City c1=new City("Madurai",3000000l,2000.01,680.25,1);
		City c2=new City("Chennai",200000l,2200.01,240.25,3);
		City c3=new City("Tenkasi",345000l,2053.01,320.25,0);
		City c4=new City("Tirunelveli",70000l,1240.01,240.25,1);
		City c5=new City("Kumbakonam",30000l,422.01,123.25,2); 
		
		City[] arr= {c1,c2,c3,c4,c5};

		System.out.println(Arrays.toString(arr));
		Arrays.sort(arr,new PopulationComperator());
		System.out.println("---------Sorted using population---------\n"+Arrays.toString(arr));
		Arrays.sort(arr,new AreaComperator().thenComparing(new NameComperator()));
		System.out.println("---------Sorted using Area and Name---------\n"+Arrays.toString(arr));
		Arrays.sort(arr,new AirportComperator().reversed().thenComparing(new PopulationComperator().reversed()));
		System.out.println("---------Sorted using No Of Airports and population---------\n"+Arrays.toString(arr));
		Arrays.sort(arr,new NameComperator());
		System.out.println("---------Sorted using Name---------\n"+Arrays.toString(arr));
	}




}

class Area{
	double length;
	double breadth;
	
	Area(double len,double bred){
		length=len;
		breadth=bred;
	}
	
	public String toString() {
		return (this.calculateArea()+".Kmsq");
	}
	
	double calculateArea() {
		return length*breadth;
	}
}
