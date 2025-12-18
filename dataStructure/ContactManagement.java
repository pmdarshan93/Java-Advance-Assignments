package dataStructure;

import java.util.Iterator;
import java.util.Scanner;

public class ContactManagement {
	TreeSet<Contact> contacts = new TreeSet<>();

	boolean numberValidator(String number) {
		return Contact.validPhone(number);
	}

	boolean codeValidator(String code) {
		return Contact.validCountryCode(code);
	}

	boolean addContact(String name, String number, String code) {
		Contact newContact = new Contact(number, code, name);
		if (contacts.search(newContact) == null) {
			contacts.add(newContact);
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		ContactManagement manager = new ContactManagement();
		TreeSet<Contact> contacts = manager.contacts;
//		TreeSet<Integer> set=new TreeSet<>();
//        int[] numbers = { 42,17, 89, 17, 56,23, 56, 91, 10, 5,5, 73, 29, 84, 33, 91, 67, 12, 39, 2, 99, 2, 45, 62, 88};
//        
//        for(int num:numbers) {
//        	set.add(num);
//        }
//        
//        set.print();
//        System.out.println("SIze : "+set.size());
//        System.out.println("Poll First : "+set.pollFirst());
//        System.out.println("Poll Last  : "+set.pollLast());
//        System.out.println("First : "+set.first());
//        System.out.println("Last : "+set.last());
//        System.out.println("Higher : "+set.higher(33));
//        System.out.println("Lower : "+set.lower(89));
//        System.out.println(" IS empty "+set.isEmpty());
//        System.out.println("SIze : "+set.size());
//        Iterator<Integer> itr=set.iterator();
//        while(itr.hasNext()) {
//        	System.out.print(itr.next()+" ");
//        }
//        System.out.println();
//        Iterator<Integer> desitr=set.descendingIterator();
//        while(desitr.hasNext()) {
//        	System.out.print(desitr.next()+" ");
//        }
//        System.out.println(set.search(2414));

		System.out.println("------------- Contact Manager USING RED BLACK TREE SET ----------------");
		int option = 0;
		Scanner sc = new Scanner(System.in);
		Contact dummy = new Contact("0", "+91", "Admin");
		while (option != 7) {
			System.out.print(
					"\n----------- MAIN MENU -----------\n\n1.Search Contact\n2.Add Contact\n3.Remove COntact\n4.View All COntacts\n5.No of Contacts\n6.Update Contact\n7.Exit\nEnter the Option : ");
			option = sc.nextInt();
			sc.nextLine();
			System.out.println("");
			if (option == 1) {
				System.out.print("Enter the number : ");
				String number = sc.nextLine();
				dummy.number = number;
				Contact searched = contacts.search(dummy);
				if (searched == null) {
					System.out.print("\n--- No Contact Found -----\nWant to add it y/n ?  ");
					char ans = sc.nextLine().toLowerCase().charAt(0);
					if (ans == 'y') {
						option = 2;
					}
				} else {
					System.out.println("---------- Contact Found -----------\n" + searched
							+ "\n-----------------------------------");
				}
			}
			if (option == 2) {
				System.out.print("\nEnter the contact Name : ");
				String name = sc.nextLine();
				System.out.print("Enter the phone Number : ");
				String number = sc.nextLine();
				System.out.print("Enter the Country Code ( +91 or IND ) : ");
				String countryCode = sc.nextLine();
				if (manager.codeValidator(countryCode)) {
					if (manager.numberValidator(number)) {
						if (manager.addContact(name, number, countryCode)) {
							System.out.println("\nAdded Contact Successfully\n");
						} else {
							System.out.println("\nContact Already Exists\n");
						}
					} else {
						System.out.println("\n -- Invalid Number --");
					}
				} else {
					System.out.println("- Invalid Country Code - \n");
				}
			} else if (option == 3) {
				System.out.print("Enter the number want to remove : ");
				dummy.number = sc.nextLine();
				Contact remove = contacts.search(dummy);
				if (remove == null) {
					System.out.println("---------- No contact Found -----------\n");
				} else {
					if (contacts.remove(remove)) {
						System.out.println("----------- Contact Removed Successfully ------------\n");
					} else {
						System.out.println("----------- No contact found 2 ----------------");
					}
				}
			} else if (option == 4) {
				if (contacts.isEmpty()) {
					System.out.println("--------- Contact list is empty--------\n");
				} else {
					Iterator<Contact> itr = contacts.iterator();
					while (itr.hasNext()) {
						Contact c = (Contact) itr.next();
						if (c != null)
							System.out.println(c);
					}
					System.out.println();
				}
			} else if (option == 5) {
				System.out.println("==================================\n====== Total Contacts : " + contacts.size()
						+ " ======\n==================================");
			} else if (option == 6) {
				System.out.print("Enter the number you want to update : ");
				String number = sc.nextLine();
				dummy.number = number;
				Contact searched = contacts.search(dummy);
				if (searched != null) {
					int subOption = 0;
					while (subOption != 4) {
						System.out.print(
								"--------------------- Update Menu ----------------------- \n1.Number \n2.Name\n3.Country Code\n4.Exit\nEnter the option : ");
						subOption = sc.nextInt();
						sc.nextLine();
						if (subOption == 1) {
							System.out.println("Enter the New Number");
							String newNumber = sc.nextLine();
							if (Contact.validPhone(newNumber)) {
								manager.addContact(searched.name, newNumber, searched.country);
								manager.contacts.remove(searched);
							} else {
								System.out.println("\n--------- Invalid Number Update Failed ---------");
							}
						} else if (subOption == 2) {
							System.out.print("Enter the new name : ");
							String name = sc.nextLine();
							if (name != "") {
								searched.name = name;
								System.out.println("---- Updated Name Successfully ----");
							} else {
								System.out.println("---- Invalid Name ----");
							}
						} else if (subOption == 3) {
							System.out.println("Enter the new Country Code");
							String code = sc.nextLine();
							if (Contact.validCountryCode(code)) {
								searched.country = code;
								System.out.println("------ Country Code Updated Successfully -----");
							} else {
								System.out.println("--------- Invalid Country code -----------");
							}
						} else if (subOption != 4) {
							System.out.println("------- Invalid Option ---------");
						}
					}
				} else {
					System.out.println("------ Contact Not Found ------\n");
				}
			} else if (option != 7 && option != 1) {
				System.out.println("-------- Invalid Option ---------");
			}
		}
		System.out.println("\n---------- Thank you ------------");
		sc.close();
	}

}