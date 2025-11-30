package assignment004;

public class Word {
	String value;
	
	Word(String msg){
		this.value=msg;
	}
	
	public void  doBold(){
		this.value="<b>"+this.value+"</b>";
	}
	
	public void doItalic() {
		this.value="<i>"+this.value+"</i>";
	}
	
	public void doUnderline() {
		this.value="<u>"+this.value+"</u>";
	}
	
	public String toString() {
		return this.value;
	}
}
