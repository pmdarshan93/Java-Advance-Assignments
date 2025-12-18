package dataStructure;

public class Score {
	int tamil;
	int english;
	int maths;
	int science;
	int social;
	int total;
	
	Score(int tam,int eng,int mat,int sci,int soc){
		this.tamil=tam;
		this.english=eng;
		this.maths=mat;
		this.science=sci;
		this.social=soc;
		this.total=tam+eng+mat+sci+soc;
	}

}
