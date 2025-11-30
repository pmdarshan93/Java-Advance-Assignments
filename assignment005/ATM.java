

public class ATM {
	private Card card;

	void insertCard(Card card) {
		this.card = card;
	}

	class Card {
		String cardNumber;
		String holderName;
		int pin;

		Card(String cardNumber, String holderName, int pin) {
			this.cardNumber = cardNumber;
			this.holderName = holderName;
			this.pin = pin;
		}
		
		void printCardInfo() {
			System.out.println("Card Number : " + cardNumber + "\nHolder Name : " + holderName+pin);
		}
	}

	

	void validatePin(int pinInput) {
		class PinValidator {
			boolean check(int pinInput) {
				return card.pin == pinInput;
			}
		}
		if(new PinValidator().check(pinInput)) {
			System.out.println("PIN Verified. Access Granted.");
		}
		else {
			System.out.println("PIN Invalid. Access Denied.");
		}
	}

	public static void main(String[] args) {
		ATM sbi = new ATM();
		ATM.Card cus = sbi.new Card("1234-4321-2332", "Darshan", 2008);
		cus.printCardInfo();
		sbi.insertCard(cus);
		sbi.validatePin(2004);
		sbi.validatePin(2008);
		
	}
}
