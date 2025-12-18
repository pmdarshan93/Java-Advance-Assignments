package assignment009;

public class Booking implements Runnable{
	Library lib;
	Student student;

	Booking(Library lib, Student cus) {
		this.lib = lib;
		this.student = cus;
	}

	public void run(){
		synchronized(lib){
			lib.bookCount-=student.bookCount;
			System.out.println("Student "+student.name+" booked "+student.bookCount+" books remaining "+lib.bookCount);
		}
	}

	private void syncronized(Library lib2) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) throws InterruptedException {
		Library myLibrary = new Library(20);

//		Book b1 = new Book("PS", 1);
//		Book b2 = new Book("One piece", 2);
//		Book b3 = new Book("Naruto", 3);
//		Book b4 = new Book("ZS", 4);
//		Book b5 = new Book("Comedy", 5);
//		Book b6 = new Book("Eat that frong", 6);
//		Book b7 = new Book("Who moved my cheese", 7);
//		Book b8 = new Book("Dopamine detox", 8);
//		Book b9 = new Book("Subtle art of not giving", 9);
//		Book b10 = new Book("Ichi kai", 10);

//		myLibrary.addBooks(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10);

		Student c1 = new Student("Darshan",2);
		Student c2 = new Student("Sree", 3);
		Student c3 = new Student("Abi", 4);
		Student c4 = new Student("Siva", 1);
		Student c5 = new Student("Ajmeer", 3);
		Student c6 = new Student("Vijay", 5);
		Student c7 = new Student("Aathesh",1 );

		Thread bk1 = new Thread(new Booking(myLibrary, c1));
		Thread bk2 = new Thread(new Booking(myLibrary, c2));
		Thread bk3 = new Thread(new Booking(myLibrary, c3));
		Thread bk4 = new Thread(new Booking(myLibrary, c4));
		Thread bk5 = new Thread(new Booking(myLibrary, c5));
		Thread bk6 = new Thread(new Booking(myLibrary, c6));
		Thread bk7 = new Thread(new Booking(myLibrary, c7));

		bk1.start();
		bk2.start();
		bk3.start();
		bk4.start();
		bk5.start();
		bk6.start();
		bk7.start();

		bk1.join();
		bk2.join();
		bk3.join();
		bk4.join();
		bk5.join();
		bk6.join();
		bk7.join();
		System.out.println("\n ----- Booking Compeleted -----");
	}

}
