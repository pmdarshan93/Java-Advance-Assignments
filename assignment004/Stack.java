package assignment004;

import java.util.ArrayList;
import java.util.Scanner;

public class Stack {
    private ArrayList<Word> stacklist = new ArrayList<>();
    private ArrayList<Word> undoList=new ArrayList<>();
    private int capacity = 5;

    public boolean hasCapacity() {
        if (stacklist.size() < capacity) {
            return true;
        }
        return false;
    }

    public void push(Word word) throws StackListOverFlowException  {
        if (hasCapacity()) {
            stacklist.add(word);
        } else {
            throw new StackListOverFlowException("Stack List Overflowed");
        }
    }

    public void pop() throws StackListUnderFlowException {
        if (!stacklist.isEmpty()) {
        	undoList.add(stacklist.get(stacklist.size()-1));
            stacklist.remove(stacklist.size() - 1);
        } else {
            throw new StackListUnderFlowException("Stack List is Empty");
        }
    }
    
    public void redo() throws StackListUnderFlowException{
    	if(!undoList.isEmpty()) {
    		stacklist.add(undoList.get(undoList.size()-1));
            undoList.remove(undoList.size() - 1);
    	}
    	else {
    		throw new StackListUnderFlowException("Undo list is empty");
    	}
    }

    public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Stack stack = new Stack();
		System.out.print("Enter the  word : ");
		String word = sc.nextLine();
		Word msg = new Word(word);
		try {
			stack.push(msg);
			Outer:
		    while (true) {
			    System.out.println(stack.stacklist+"\nEnter your choice : (Bold / italic / underline / undo / redo / stop");
    			String choice = sc.nextLine().toLowerCase();
	    		switch (choice) {
	    			case "stop":{
	    				break Outer;
	    			}
		    	    case "bold": {
			    	    msg.doBold();
    				    stack.push(msg);
        				break;
	        		}
		        	case "italic":{
			        	msg.doItalic();
				        stack.push(msg);
				        break;
        			}
    	    		case "underline":{
		        		msg.doUnderline();
			        	stack.push(msg);
			        	break;
			        }
        			case "undo":{
	        			stack.pop();
	        			break;
		        	}
        			case "redo":{
        				stack.redo();
        				break;
        			}
        			default:{
        				System.out.println("In valid input");
        			}
			    }
		    }
		}catch(StackListOverFlowException e) {
			System.out.println(e.getMessage());
		}catch(StackListUnderFlowException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			sc.close();
			System.out.println("Thank you ");
		}
	}
}