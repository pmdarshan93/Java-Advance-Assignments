package assignment010;

public class Transaction extends Thread {
	Person customer;
	int withdraw;

	Transaction(Person p, int amount) {
		this.customer = p;
		this.withdraw = amount;
	}

	public void run() {
		synchronized (customer) {
			if (customer.canWithdraw(withdraw)) {
				customer.acc.balance -= withdraw;
				System.out.println(customer.name+" Transaction succesful balance : " + customer.acc.balance);
			} else {
				System.out.println("Insufficient balance");
			}

		}
	}

	public static void main(String[] args) {
		Account sharedAcc = new Account(5000);

		Person husband = new Person("Husband",sharedAcc);
		Person wife = new Person("Wife",sharedAcc);

		Transaction t1 = new Transaction(husband, 4000);
		Transaction t2 = new Transaction(wife, 3000);
		
		t1.start();
		t2.start();
	}

}

class Account {
	int balance;

	Account(int amount) {
		this.balance = amount;
	}
}

class Person {
	String name;
	Account acc;

	Person(String name,Account acc) {
		this.name=name;
		this.acc = acc;
	}

	boolean canWithdraw(int amount) {
		return acc.balance >= amount && amount > 0;
	}
}