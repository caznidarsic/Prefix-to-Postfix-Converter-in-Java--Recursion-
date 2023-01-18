/* Christian Znidarsic
 * Lab 2
 * EN.605.202.86.SP22 Data Structures
 * 
 * The Queue class. 
 * 
 * 	This class defines a Queue and it's methods enqueue(),
 * dequeue(), and isEmpty().
 */
public class Queue {
	
	QueueNode front, rear;
	
	
	//the constructor for the Queue class. It defines this.front and this.rear to be null.
	public Queue() {
		
		this.front = null;
		this.rear = null;
		
	}
	
	
	//enqueue() adds the input String to the back of the queue
	void enqueue(String key) {
		
		QueueNode temp = new QueueNode(key);
		
		/* 
		 * if the queue is empty, then the new node is both the front and
		 * the rear of the queue.
		 */
		if (this.rear == null) {
			this.front = this.rear = temp;
			return;
		}
		
		//set the next pointer
		this.rear.next = temp;
		this.rear = temp;
	}
	
	
	//dequeue() removes the head of the queue if not empty, and does nothing if queue is empty
	void dequeue() {
		
		if (this.front == null) {
			return;
		}
		
		this.front = this.front.next;
		
		if (this.front == null) {
			this.rear = null;
		}
		
	}
	
	//isEmpty() returns true if the queue is empty, false if not empty
	boolean isEmpty() {
		
		if (this.front == null) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	
}
