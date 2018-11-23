		/*
		 * This class contains the methods required to implement the max priority queue
		 * data structure - 'Fibonacci Heap'.

		 * **Class level variables:
		 * myMax - Points to the Node that stores the maximum key value in the Heap.
		 * myNumberOfNodes - the total number of Nodes in the FibonacciHeap
		 */

		public class FibonacciHeap {
			Node myMax;
			Integer myNumberOfNodes;

			public FibonacciHeap(Node max, Integer numberOfNodes) {
				myMax = max;
				myNumberOfNodes = numberOfNodes;
			}

			public static FibonacciHeap makeFibHeap() {
				return new FibonacciHeap(null, 0);
			}

		/*
		 * FibHeapInsert(Node x)
		 * Insert a new node x into the FibonacciHeap H. 
		 * If myMax is null, then make the new Node as myMax.
		 * Else, add the new node to the right of the myMax in the root node list of the H.
		 */
		public void FibHeapInsert(Node x) {
			x.setDegree(0);
			x.setParent(null);
			x.setChild(null);
			x.setMark(false);
			if (this.myMax == null) {
				x.setLeft(x);
				x.setRight(x);
				this.myMax = x;
			} else {
				x.setLeft(this.myMax);
				x.setRight(this.myMax.getRight());
				this.myMax.getRight().setLeft(x);
				this.myMax.setRight(x);
				if (x.getKey() > this.myMax.getKey())
					this.myMax = x;
			}

			this.myNumberOfNodes++;
		}

		/*
		 * Extract the Node with the maximum key from the Fib Heap H.
		 * If there is no myMax, return null
		 * Else, Add all the children of myMax to the root list of H.
		 *       Remove myMax from the rootList and make the right sibling 
		 * 	 	 of myMax as new myMax of the Fib Heap H.
		 *		 If there is only one Node in the Fib Heap H, then make myMax null
		 * 		 and decrement the number of nodes in H by 1.
		 * 		 Consolidate the remaining Fib Heap H to combine the nodes of equal degree 
		 * 		 in H, pairwise(This is done in the method consolidate)
		 *
		 */
		public Node FibHeapExtractMax() {
			Node z = this.myMax;
			if (z != null) {
				Integer numberOfChildren = z.getDegree();
				Node temp = z.getChild();
				while (numberOfChildren > 0) {
					Node sibling = temp.getRight();
					//FibHeapInsert(temp);
					temp.setRight(this.myMax.getRight());
					temp.setLeft(this.myMax);
					this.myMax.getRight().setLeft(temp);
					this.myMax.setRight(temp);
					
					temp.setParent(null);
					temp = sibling;
					numberOfChildren--;
				}
				// Remove z from the rootList
				z.getLeft().setRight(z.getRight());
				z.getRight().setLeft(z.getLeft());
				if (z == z.getRight())
					this.myMax = null;
				else {
					this.myMax = z.getRight();
					consolidate();
				}
				this.myNumberOfNodes--;
			}

			return z;
		}

		/*
		 * Consolidate a Fibonacci Heap.
		 * After extracting max from the Fibonacci Heap H, we combine the nodes with same degree, pairwise.
		 * Node[] A is used to keep track of the nodes with a specific degree.
		 *        A[i] - contains a node with degree i.
		 *
		 * For each node in the root list, if there is any other node with the same degree, make the node with 
		 * smaller key the child of the node with the larger key. This is done in the method FibHeapLink
		 * 
		 */
		public void consolidate() {
			Node[] A = new Node[this.myNumberOfNodes];
			for (int i = 0; i < this.myNumberOfNodes; i++)
				A[i] = null;
			Node temp = this.myMax;
			/* Adding z for the while loop condition check. This is because, the value of x is 
			 * changed in the loop and cannot be used to check the loop condition.
			 */
			Node x = null; Node z=null;
			Node last = temp.getLeft();
			do {
				x = temp;
				z= x;
				temp = temp.getRight();
				Integer d = x.getDegree();
				while (A[d] != null) {
					Node y = A[d];
					if (x.getKey() < y.getKey()) {
						Node t = x;
						x = y;
						y = t;
					}
					fibHeapLink(y, x);
					A[d] = null;
					d = d + 1;
				}
				A[d] = x;
			}while (z != last);
			this.myMax = null;
			for (int i = 0; i < A.length; i++) {
				if (A[i] != null) {
					if (this.myMax == null) {
						this.myMax = A[i];
					} else {
						if (A[i].getKey() > this.myMax.getKey())
							this.myMax = A[i];
					}
				}
			}
		}

		/*
		 * fibHeapLink(Node y, Node x)
		 * Node y is to be made the child of the Node x.
		 * 
		 * Node x
		 *   |
		 * Node y
		 * Remove the Node y from the root list
		 * Make node x the parent of node y.
		 * If Node x has no children, then make y the child of x and point the left and right siblings to itself.
		 * Else, insert the the node y in the circular sibling linked list of the children of node x
		 * 
		 * Increase the degree of the node x by 1 and mard the child cut value of the node y as false.
		 */
		public void fibHeapLink(Node y, Node x) {
			y.getLeft().setRight(y.getRight());
			y.getRight().setLeft(y.getLeft());

			Node xChild = x.getChild();
			y.setParent(x);
			if (xChild != null) {
				y.setRight(xChild);
				y.setLeft(xChild.getLeft());
				xChild.getLeft().setRight(y);
				xChild.setLeft(y);
			}
			else
			{
				y.setLeft(y);
				y.setRight(y);
				x.setChild(y);
			}
			x.setDegree(x.getDegree() + 1);

			y.setMark(false);
		}
		
		
		/*
		 * FibHeapIncreaseKey(Node x, Integer k)
		 * Increase the key value of the Node 'x' to a new key value 'k'.
		 * If the child key value is larger than the parent key value, cut 
		 * the child from the child list of the parent and add it to the root list.
		 * Initiate cascading cut on the parent node.
		 */
		public void FibHeapIncreaseKey(Node x, Integer k)
		{
			x.setKey(k);
			Node y = x.getParent();
			//If x key is greater than its parent, make x a new tree in the root list
			if(y != null && x.getKey() > y.getKey())
			{
				cut(x,y);
				cascadingCut(y);
			}
			if(x.getKey() > this.myMax.getKey())
				this.myMax = x;
		}

		/*
		 * cascadingCut(Node y)
		 * If the child cut value of the node y is false, mark it as true.
		 * Else, if parent of the node y is not null, cut y from the child list of the parent,
		 * add it to the root list, and initiate cascading cut on the parent of the node y.
		 */
		private void cascadingCut(Node y) {
			Node z = y.getParent();
			if(z != null)
			{
				if(!y.isMark())
					y.setMark(true);
				else
				{
					cut(y,z);
					cascadingCut(z);
				}
			}
		}

		/*
		 * cut(Node x, Node y) 
		 * Cut the node x from the children list of the node y and add node x to the root list.
		 * Decrease the degree of the node y by 1.
		 * 
		 * If there is only 1 child in the child list of the node y, then make the child of y null.
		 * Else, if the node y points to the node x as a child, change the pointer to the right 
		 * sibling of the node x. Then, remove the node x from its sibling list and add it to the
		 * root list of the Fib Heap H.
		 */
		private void cut(Node x, Node y) {
			if(y.getDegree() == 1)
				y.setChild(null);
			else
			{
				if(y.getChild() == x)
				{
					y.setChild(x.getRight());
				}
			}
			x.getLeft().setRight(x.getRight());
			x.getRight().setLeft(x.getLeft());
			y.setDegree(y.getDegree() - 1);
			//Adding x to the root list
			x.setParent(null);
			x.setMark(false);
			x.setRight(this.myMax.getRight());
			x.setLeft(this.myMax);
			this.myMax.getRight().setLeft(x);
			this.myMax.setRight(x);
			
		}

	}