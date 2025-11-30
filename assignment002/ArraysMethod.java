import java.util.Arrays;

public class ArraysMethod {
 public static void main(String[] args) {
	 int[] arr= {25,452,24,241,13,29};
	 // array sorting
	 Arrays.sort(arr);
	 // printing array
	 System.out.println(Arrays.toString(arr));
     System.out.println("Largest value : "+arr[5]+"\tSmallest value : "+arr[0]);
	 // Binary Search in array ( if the element if not present returns where it fits )
	 System.out.println(Arrays.binarySearch(arr, 26));
	 // copy the given arr
	 int[] arr2=Arrays.copyOf(arr,10);
	 System.out.println(Arrays.toString(arr2));
	 // copy the given array with given indices
	 int[] arr3=Arrays.copyOfRange(arr, 2, 5);
	 System.out.println(Arrays.toString(arr3));
	 String[] arr4=new String[6];
	 // fill the array fully or with given indices with given value
	 Arrays.fill(arr4,0,2,"abc");
     Arrays.fill(arr4,3,5,"def");
	 System.out.println(Arrays.toString(arr4));
	 // checks two array equal or not
	System.out.println( Arrays.equals(arr, arr2));
	int[][] arr5= {{1,2,3},{3,4,5}};
	// to String prints the references where deep to string print sub arrays to
	System.out.println(Arrays.deepToString(arr5));
    // Comparison using hash code
    int[] a={1,2,4};
    int[] b={1,2,4};
    System.out.println(Arrays.hashCode(a)==Arrays.hashCode(b));
    ////// cloning
    try{
        Students s1=new Students("Gowtham",21);
Students s2=(Students)s1.clone();
System.out.println(s1+"\n"+s2);
    }catch(CloneNotSupportedException e){
        System.out.println(e.getMessage());
    }

 }
}



 class Students implements Cloneable{
int id;
String name;

Students(String name,int id){
this.name=name;
this.id=id;
}
public String toString() {
	return "Name : "+this.name+" Id : "+this.id;
}


protected Object clone() throws CloneNotSupportedException{
	return super.clone();

}
}
 