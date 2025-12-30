package assignment010;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Restaruent {
	public static void main(String[] args) {
		ExecutorService pool=Executors.newFixedThreadPool(3);
	
		for(int i=0;i<10;i++) {
			Thread newThread=new Thread(new Order(i));
			pool.submit(newThread);
		}

	}
}

class Order implements Runnable{
	int orderId;
	
	Order(int id){
		this.orderId=id;
	}
	
	public void run() {
		try {
			System.out.println("Chef "+Thread.currentThread().getName()+" is preparing Order "+orderId);
			Thread.sleep(100);
			System.out.println("ORDER "+orderId+" IS READY !!");
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}