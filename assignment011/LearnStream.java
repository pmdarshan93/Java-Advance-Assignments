package assignment011;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LearnStream {
	public static void main(String[] args) {
		// Consumer
		System.out.println("------------- Consumer --------------");
		List<String> names=new ArrayList<String>(Arrays.asList("John", "Jane", "Jack"));
		names.forEach((name)->{System.out.println(name);});
		
		//Map Looper
		HashMap<Integer,String> fruits=new HashMap<>();
		System.out.println("------------- BI Consumer --------------");
		fruits.put(10,"Apple");
		fruits.put(14, "Mango");
		fruits.put(2, "Strawberry");
		fruits.forEach((count,name)->{System.out.println("We have "+count+" "+name);});
		System.out.println("------------- Predicate --------------");
		ArrayList<Integer> arr=new ArrayList();
		arr.add(2);
		arr.add(25);
		arr.add(32);
		arr.add(19);
		arr.add(55);
		List<Integer> ansarr=arr.stream().filter((ele)-> ele>20).collect(Collectors.toList());
		System.out.println("Original Array : "+arr);
		System.out.println("Answer array : "+ansarr);
		System.out.println("------------ Supplier ---------------");
		List<Double> arr2=Stream.generate(()->Math.floor(Math.random()*100)).limit(5).collect(Collectors.toList());
		System.out.println(arr2);
		arr2.add(22.0);
		System.out.println(arr2);
		System.out.println("------------ Function ---------------");
		List<String> arr3=new ArrayList<>();
		arr3.add("sree");
		arr3.add("abi");
		arr3.add("siva");
		List<String> ansarr3=arr3.stream().map((ele)->ele.toUpperCase()).collect(Collectors.toList());
		System.out.println("Original Array : "+arr3);
		System.out.println("Answer array : "+ansarr3);
		System.out.println("------------ Bi function ---------------");
		HashMap<String,Integer> emp=new HashMap<>();
		emp.put("Ajmeer",20000);
		emp.put("Siva",30000);
		emp.put("Naveen",40000);
		System.out.println("Orginal map : "+emp);
		emp.replaceAll((name,salary)->salary+=(salary/100)*10);
		System.out.println("Updated Map : "+emp);
	}
}
