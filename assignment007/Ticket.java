package assignment007;


class Ticket implements Comparable<Ticket>{
	int id;
	String employee;
	String description;
	
	Ticket(int id,String employee,String description){
		this.id=id;
		this.employee=employee;
		this.description=description;
	}
	
	public String toString() {
		return "ID : "+id+"\t Name : "+employee+"\tDescription : "+description;
	}
	
	public int compareTo(Ticket t) {
		int ans=Integer.compare(this.description.length(),t.description.length());
		if(ans==0)
			return Integer.compare(this.id,t.id);
		return ans;
	}

}
