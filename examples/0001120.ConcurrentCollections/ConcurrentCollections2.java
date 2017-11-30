import java.util.*;
import java.util.concurrent.*;

public class ConcurrentCollections2 {

	public static void main(String[] args) throws InterruptedException {
	
		BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(3);
		System.out.println("Adding");
		blockingQueue.add(1);
		System.out.println("Adding");
		blockingQueue.add(2);
		System.out.println("Adding");
		blockingQueue.add(3);
		System.out.println("Adding");
		try {
			//Add will throw an IllegalStateException because the queue is full.
			blockingQueue.add(4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Offer will return false and not add anything because the queue is full
		boolean added = blockingQueue.offer(4);
		System.out.println(added);
		
		//Peek takes the first element without removing it.
		System.out.println(blockingQueue.peek());
		System.out.println(blockingQueue.peek());
		System.out.println(blockingQueue.peek());
		System.out.println(blockingQueue.peek());
		
		//Poll returns and removes the first element
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		System.out.println(blockingQueue.poll());
		
		//Poll returns null if queue is empty
		System.out.println(blockingQueue.poll());
		
		blockingQueue.put(1);
		blockingQueue.put(2);
		blockingQueue.put(3);
		
		
		
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(1000);
				System.out.println("Removing");
				blockingQueue.take();
				System.out.println("Removed");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		
		t.start();
		
		//Put will be blocked until the other thread takes an element
		System.out.println("Putting");
		blockingQueue.put(4);
		System.out.println("Put");
		
	}

}