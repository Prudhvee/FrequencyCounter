	/*
	 * The class 'Node' defines the node structure of the FibonacciHeap.
	 * degree - The degree of the corresponding Node.
	 * name - The keyword that the key in the Node corresponds to.
	 * key - The frequency of the keyword that the Node corresponds to.
	 * parent - The parent of the corresponding Node.
	 * child - The child of the corresponding Node.
	 * left - The left sibling of the corresponding Node.
	 * right - The right sibling of the corresponding Node.
	 * mark - The child cut value of the corresponding Node
	 */

	public class Node {

		private Integer degree;
		private String name;
		private Integer key;
		private Node parent;
		private Node child;
		private Node left;
		private Node right;
		private boolean mark;

	/*
	 *Node constructor 
	 */
		public Node(Integer key, String name)
		{
			createNode(new Integer(0),key,name, null, null, null, null, false);
		}
		public Node createNode(Integer degree, Integer key, String name, Node parent, Node child, Node left, Node right, boolean mark) {
			this.degree = degree;
			this.key = key;
			this.setName(name);
			this.parent = parent;
			this.child = child;
			this.left = left;
			this.right = right;
			this.mark = mark;
			
			return this;
		}

	/*
	 *Setters and Getters for the class variables
	 */
		public Integer getDegree() {
			return degree;
		}

		public void setDegree(Integer degree) {
			this.degree = degree;
		}

		public Integer getKey() {
			return key;
		}

		public void setKey(Integer key) {
			this.key = key;
		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public Node getChild() {
			return child;
		}

		public void setChild(Node child) {
			this.child = child;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public boolean isMark() {
			return mark;
		}

		public void setMark(boolean mark) {
			this.mark = mark;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
