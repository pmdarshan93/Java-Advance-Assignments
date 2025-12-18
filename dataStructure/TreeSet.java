package dataStructure;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

@SuppressWarnings("unchecked")
public class TreeSet<T extends Comparable<T>> {
	Node<T> root;
	int size;
	int modCount;
	@SuppressWarnings("unchecked")
	static Node nil =new Node(null);

	TreeSet(){
		root=nil;
	}
	
	static {
		nil.right = nil;
		nil.left = nil;
		nil.parent = nil;
		nil.color=Color.BLACK;
	}

	void add(T value) {
		Node<T> n = new Node<>(value);
		n.left=nil;
		n.right=nil;
		n.parent=nil;
		modCount++;

		// if it is the root
		if (root == nil) {
			root = n;
			n.color = Color.BLACK;
			size++;
			return;
		}
		System.out.println(root+" "+root.right+" "+root.left);
		// if not the root
		Node<T> currentNode = root;
		Node<T> parentNode = null;

		// find the last null position for node

		while (currentNode != nil) {
			parentNode = currentNode;
			if (currentNode.value.compareTo(n.value) < 0) {
				currentNode = currentNode.right;
			} else if (currentNode.value.compareTo(n.value) > 0) {
				currentNode = currentNode.left;
			} else {
//				currentNode=node;
				return;
			}
		}

		// insert the node at the place

		if (parentNode.value.compareTo(n.value) < 0) {
			parentNode.right = n;
		} else {
			parentNode.left = n;
		}
		n.parent = parentNode;
		checkOrder(n);
		size++;
	}

	private void checkOrder(Node<T> node) {

		// process want to be repeated until it reaches the root or parent is black
		while (node != root && node.parent.color == Color.RED) {

			Node<T> parent = node.parent;
			Node<T> grandParent = parent.parent;
			Node<T> uncle = parent == grandParent.left ? grandParent.right : grandParent.left;

//			if(grandParent==null) {
//				parent.color=Color.BLACK;
//				break;
//			}

			if (parent == grandParent.left) {
				if (uncle != nil && uncle.color == Color.RED) {
					parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					grandParent.color = Color.RED;
					node = grandParent;
				} else if (parent.left == node) {
					rotateRight(grandParent);
					parent.color = Color.BLACK;
					grandParent.color = Color.RED;
				} else {
					rotateLeft(parent);
					rotateRight(grandParent);
					node.color = Color.BLACK;
					grandParent.color = Color.RED;
				}

			} else {
				if (uncle != nil && uncle.color == Color.RED) {
					parent.color = Color.BLACK;
					uncle.color = Color.BLACK;
					grandParent.color = Color.RED;
					node = grandParent;
				} else if (parent.right == node) {
					rotateLeft(grandParent);
					grandParent.color = Color.RED;
					parent.color = Color.BLACK;
				} else {
					rotateRight(parent);
					rotateLeft(grandParent);
					grandParent.color = Color.RED;
					node.color = Color.BLACK;
				}
			}
		}
		root.color = Color.BLACK;
	}

	private void rotateRight(Node<T> grandParent) {
		Node<T> parent = grandParent.left;
		grandParent.left = parent.right;
		if (grandParent.left != nil)
			grandParent.left.parent = grandParent;
		parent.parent = grandParent.parent;
		if (root == grandParent) {
			root = parent;
		} else if (grandParent.parent.left == grandParent) {
			grandParent.parent.left = parent;
		} else {
			grandParent.parent.right = parent;
		}
		parent.right = grandParent;
		grandParent.parent = parent;
	}

	private void rotateLeft(Node<T> grandParent) {
		Node<T> parent = grandParent.right;
		grandParent.right = parent.left;
		if (grandParent.right != nil)
			grandParent.right.parent = grandParent;
		parent.parent = grandParent.parent;
		if (root == grandParent) {
			root = parent;
		} else if (grandParent.parent.left == grandParent) {
			grandParent.parent.left = parent;
		} else {
			grandParent.parent.right = parent;
		}
		parent.left = grandParent;
		grandParent.parent = parent;
	}

	void clear() {
		root = null;
	}

	T first() {
		Node<T> current = root;
		while (current.left != nil) {
			current = current.left;
		}
		return current.value;
	}

	T last() {
		Node<T> current = root;
		while (current.right != nil) {
			current = current.right;
		}
		return current.value;
	}

	boolean isEmpty() {
		return root == null;
	}

	T pollFirst() {
		modCount++;
		if (isEmpty()) {
			return null;
		}
		Node<T> current = root;
		while (current.left != nil) {
			current = current.left;
		}
		remove(current.value);
		return current.value;
	}

	T pollLast() {
		modCount++;
		if (isEmpty()) {
			return null;
		}
		Node<T> current = root;
		while (current.right != nil) {
			current = current.right;
		}
		remove(current.value);
		return current.value;
	}

	T higher(T value) {
		if (isEmpty())
			return null;
		Node<T> current = root;
		Node<T> parent = root;
		while (current != nil) {

			if (current.value.compareTo(value) > 0) {
				parent = current;
				current = current.left;
			} else {
				current = current.right;
			}

		}
		return parent == nil ? null : parent.value;
	}

	T lower(T value) {
		if (isEmpty())
			return null;
		Node<T> current = root;
		Node<T> parent = root;
		while (current != nil) {
			if (current.value.compareTo(value) < 0) {
				parent = current;
				current = current.right;
			} else {
				current = current.left;
			}
		}
		return parent == nil ? null : parent.value;
	}

	void print() {
		System.out.print("[ ");
		print(root);
		System.out.print("]\n");
	}

	void print(Node<T> n) {
		if (n == nil) {
			return;
		}
		print(n.left);
		System.out.print(n.value + " ");
		print(n.right);
	}

	int size() {
		return size;
	}

	Iterator<T> iterator() {
		return new itr();
	}

	private class itr implements Iterator<T> {

		Node<T> next;
		int itrModCount = modCount;
		private boolean isFirstNext = true;

		itr() {
			next = root;
			while (next.left != nil) {
				next = next.left;
			}
		}

		@Override
		public boolean hasNext() {
			if (modCount != itrModCount) {
				throw new ConcurrentModificationException();
			}
			return next != nil;
		}

		@Override
		public T next() {
			if (modCount != itrModCount) {
				throw new ConcurrentModificationException();
			}
			if (isFirstNext) {
				isFirstNext = false;
				return next.value;

			}
			if (next.right != nil) {
				next = next.right;
				while (next.left != nil) {
					next = next.left;
				}
			} else {
				Node<T> child = next;
				next = next.parent;
				while (next != nil && next.left != child) {
					next = next.parent;
					child = child.parent;
				}
			}
			return next == nil ? null : next.value;
		}

	}

	Iterator<T> descendingIterator() {
		return new DescendItr();
	}

	private class DescendItr implements Iterator<T> {
		Node<T> next;
		int itrModCount = modCount;
		private boolean isFirstNext = true;

		DescendItr() {
			next = root;
			while (next.right != nil) {
				next = next.right;
			}
		}

		@Override
		public boolean hasNext() {
			return next != nil;
		}

		@Override
		public T next() {
			if (isFirstNext) {
				isFirstNext = false;
				return next.value;// TODO Auto-generated method stub
			}

			if (next.left != nil) {
				next = next.left;
				while (next.right != nil) {
					next = next.right;
				}
			} else {
				Node<T> child = next;
				next = next.parent;
				while (next != nil && next.right != child) {
					child = next;
					next = next.parent;
				}

			}
			return next == nil ? null : next.value;
		}

	}

	boolean remove(T n) {
		if (n == null) {
			return false;
		}
		Node<T> delete = root;
		while (delete != nil && delete.value.compareTo(n) != 0) {
			if (delete.value.compareTo(n) < 0) {
				delete = delete.right;
			} else {
				delete = delete.left;
			}
		}

		if (delete == nil) {
			return false;
		}

		Node<T> successor = null;

		if (delete.right != nil && delete.left != nil) {
			successor = findSuccessor(delete);
			delete.value = successor.value;
			delete = successor;
		}
		Node child=null;
		if (delete.left == nil || delete.right == nil) {
			child = delete.right == nil ? delete.left : delete.right;
			if (delete == root) {
				root = child;
				child.parent = nil;
			} else if (delete.parent.left == delete) {
				delete.parent.left = child;
			} else {
				delete.parent.right = child;
			}
		}

		if (delete.color == Color.BLACK)
			fixDeletion(child);
		size--;
		return true;
	}

	private Node<T> findSuccessor(Node<T> n) {
		if (n.right != nil) {
			n = n.right;
		} else {
			return n;
		}
		while (n.left != nil) {
			n = n.left;
		}
		modCount--;
		return n;
	}

	private void fixDeletion(Node<T> n) {
		while (n != root && n.color == Color.BLACK) {
			if (n.parent.left == n) {
				Node<T> uncle = n.parent.right;

				if (uncle.color == Color.RED) {
					uncle.color = Color.BLACK;
					n.parent.color = Color.RED;
					rotateLeft(n.parent);
					uncle = n.parent.right;
				}
				if (uncle.right.color == Color.BLACK &&  uncle.left.color == Color.BLACK) {
					uncle.color = Color.RED;
					n = n.parent;
				}
				else if ( uncle.left.color == Color.RED) {
					uncle.left.color = Color.BLACK;
					uncle.color = Color.RED;
					rotateRight(uncle);
					uncle = n.parent.right;
				} 
				if ((uncle.right.color == Color.RED)) {
					uncle.color = n.parent.color;
					n.parent.color = Color.BLACK;
					uncle.right.color = Color.BLACK;
					rotateLeft(n.parent);
					n = root;
				}

			} else {
				Node<T> uncle = n.parent.left;

					if (uncle.color == Color.RED) {
						uncle.color = Color.BLACK;
						n.parent.color = Color.RED;
						rotateRight(n.parent);
						uncle = n.parent.left;
					}
					if (uncle.right.color == Color.BLACK &&  uncle.left.color == Color.BLACK) {
						uncle.color = Color.RED;
						n = n.parent;
					}
					else if ( uncle.right.color == Color.RED) {
						uncle.right.color = Color.BLACK;
						uncle.color = Color.RED;
						rotateLeft(uncle);
						uncle = n.parent.left;
					} 
					if ((uncle.left.color == Color.RED)) {
						uncle.color = n.parent.color;
						n.parent.color = Color.BLACK;
						uncle.left.color = Color.BLACK;
						rotateRight(n.parent);
						n = root;
					}
			}
		}
		n.color = Color.BLACK;
	}

	T search(T value) {
		if(isEmpty()) {
			return null;
		}
		Node<T> parent=root;
		while(parent!=nil) {
			if(parent.value.compareTo(value)>=0) {
				if(parent.value.compareTo(value)==0) {
					return parent.value;
				}
				else {
					parent=parent.left;
				}
			}
			else {
				parent=parent.right;
			}
		}
		return null;
	}
}
