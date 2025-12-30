package assignment010;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ExamEvalutorMain{
	public static void main(String[] args) throws InterruptedException, ExecutionException {

	ExecutorService pool=Executors.newFixedThreadPool(2);
	
	Future<String> s1=pool.submit(new ExamEvalutor("Darshan"));
	System.out.println(s1.get());
	Future<String> s2=pool.submit(new ExamEvalutor("Sree"));
	System.out.println(s2.get());
	Future<String> s3=pool.submit(new ExamEvalutor("Abi"));
	System.out.println(s3.get());
	Future<String> s4=pool.submit(new ExamEvalutor("Aathesh"));
	System.out.println(s4.get());
	Future<String> s5=pool.submit(new ExamEvalutor("Siva"));
	System.out.println(s5.get());

	pool.shutdown();
	}
}

class ExamEvalutor implements Callable<String>{

	String student;
	
	ExamEvalutor(String name){
		this.student=name;
	}
	@Override
	public String call() throws Exception {
		System.out.println("Teacher "+Thread.currentThread().getName()+" is grading "+student+"\n");
		return student+" is graded by "+Thread.currentThread().getName()+" got "+Math.floor((Math.random()*100))+" marks\n";
	}
	
}

