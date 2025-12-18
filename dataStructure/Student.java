package dataStructure;

public class Student implements Comparable<Student>{
	String name;
	static int count;
	int id;
	Score score;
	
	Student(String name,int tam,int eng,int mat,int sci,int soc){
		this.name=name;
		this.id=count++;
		this.score=new Score(tam,eng,mat,sci,soc);
	}
	
	public int compareTo(Student s) {
		return Integer.compare(this.score.total,s.score.total);
	}
	
	public String toString() {
		return "Name : "+name+",ID : "+id+",Score : "+score.total;
	}
	
}
