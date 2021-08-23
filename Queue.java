// Queue ADT implementation
public class Queue {
	private Node head;
	private Node tail;
	private int numItems;
	
	private class Node{
		Object item;
		Node next;
		
		Node(Object item) {
			this.item = item;
			this.next = null;
		}
	}
	
	Queue() {
		head = null;
		tail = head;
		numItems = 0;
	}
	
	boolean isEmpty() {
		return head == null;	
	}
	
	int length() {
		return numItems;		
	}
	
	public void enqueue(Object newItem) {
		Node newN = new Node(newItem);
		if(numItems == 0) {
			head = newN;
			tail = newN;
			numItems++;
		}
		else if(numItems == 1) {
			head.next = newN;
			tail = head.next;
			numItems++;
		}
		else { 
			tail.next = newN;
			tail = tail.next;
			numItems++;
		}
	}
	
	public Object dequeue() throws QueueEmptyException {
		Object temp;
		if(numItems == 1) {
			temp = head.item;
			head = null;
			tail = head;
			numItems = 0;
					
		}
		else if(numItems > 1) {
			temp = head.item;
			head = head.next;
			numItems--;
		}
		
		else {
			throw new QueueEmptyException("Queue is empty.");
		}	
		return temp;
	}
	
	public Object peek() throws QueueEmptyException {
		if(head != null) {
			Object temp = head.item;
			return temp;
		}
		else {
			throw new QueueEmptyException("Queue is empty.");
		}
	}
	
	public void dequeueAll() throws QueueEmptyException {
		if(!isEmpty()) {
			head = null;
			tail = head;
			numItems = 0;
		}
		else {
			throw new QueueEmptyException("Queue is Empty.");
		}
	}

	public String toString() {
		Node temp = head;
		String queue = "";
		if(numItems != 0) {
			while(temp != null) {
				Job job = (Job)temp.item;
				queue += "("+job.getArrival() +", " + job.getDuration() +", "
				      +(job.getFinish() == -1?"*":String.valueOf(job.getFinish()))+") ";
				temp = temp.next;
			}
		}
		return queue;
	}
}
