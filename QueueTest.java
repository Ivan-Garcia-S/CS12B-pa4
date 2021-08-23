//Test client for Queue ADT
public class QueueTest {
	public static void main(String args[]) {
	
	Queue q = new Queue();
	Job j = new Job(4,5);
	//	Node n = new Node(j);
	
	System.out.println(q.isEmpty());
	q.enqueue(j);
	//	System.out.println(q.peek());
	q.enqueue(new Job(6,2));
	q.enqueue(new Job(10,1));
	System.out.println(q.length());
	System.out.println(q.toString());
	System.out.println(q.isEmpty());
	
	q.dequeueAll();
	//	q.dequeue();
	//	q.dequeue();
	//	System.out.println(q.toString());
	System.out.println(q.isEmpty());
	
	
	
	
	
	}
}
