package dataStructure;

public class Contact implements Comparable<Contact>{
	String country,name,number;
	
	Contact(String number,String country,String name){
		this.number=number;
		this.country=country;
		this.name=name;
	}
	
	
	public String toString() {
		return "Name : "+name+"\tNumber : ("+country+") "+number;
	}
	
	public int compareTo(Contact c) {
		return this.number.compareTo(c.number);
	}
	
	public static boolean validPhone(String number) {
		String phoneRegex = "^\\d{10}$"; 
		return number.length()==10 && number.charAt(0)!='0' && number.matches(phoneRegex);
	}
	
	public static boolean validCountryCode(String code) {
		return code.length()>1 && code.length()<4;
	}


}
