/* Christian Znidarsic
 * Lab 2
 * EN.605.202.86.SP22 Data Structures
 * 
 * The QueueNode class 
 * 
 * 	This class defines a node for a queue data structure.
 * It has fields "key" and "next" which are the two properties of
 * a node. The constructor QueueNode() takes a String as an input
 * and sets it as the .key property. It defines the .next property
 * as null.
 */
public class QueueNode {
	String key;
	QueueNode next;
	
	public QueueNode(String key) {
		this.key = key;
		this.next = null;
	}
	
}
