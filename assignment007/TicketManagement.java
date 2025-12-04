package assignment007;

import java.util.Comparator;
import java.util.PriorityQueue;

public class TicketManagement {
	
	public static void main(String[] args) {
		Comparator<Ticket> description=(a,b)->Integer.compare(a.description.length(),b.description.length());
		Comparator<Ticket> id=(a,b)->Integer.compare(a.id,b.id);
		PriorityQueue<Ticket> ticketList=new PriorityQueue<>(description.thenComparing(id));
		ticketList.add(new Ticket(0,"Ramesh","He has a scratch on the right top of the display "));
		ticketList.add(new Ticket(1,"Suresh","Laptop Display blank"));
		ticketList.add(new Ticket(2,"Vijay","Laptop Display blank"));
		ticketList.add(new Ticket(3,"Darshan","laptop backside screw got separated"));
		
		while(ticketList.peek()!=null) {
			System.out.println(ticketList.poll());
		}
	}

}

