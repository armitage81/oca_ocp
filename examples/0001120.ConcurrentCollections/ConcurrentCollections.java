import java.util.concurrent.*;
import java.util.*;

public class ConcurrentCollections {
	
	public static void main(String[] args) {
		
		//Example of 'copy on write' collection. It creates a copy of an internal array with each modification.
		//Its iterator always points to the elements that existed when the iterator was created. The list might be modified while iterating without throwing the concurrent modification exception
		//But the iterator itself must not call remove, set or add
		CopyOnWriteArrayList l = new CopyOnWriteArrayList();
		l.add("A");
		l.add("B");
		System.out.println(l.size());
	
		Iterator it = l.iterator();
		
		while (it.hasNext()) {
			Object o = it.next();
			System.out.println(o); 
			l.add(o.toString() + "new"); //List modified during iteration. No effect on the iteration, though.
			//it.remove(); //This would cause an UnsupportedOperationException
		}
		
		System.out.println(l.size());


		//There is also a CopyOnWriteArraySet
		
		CopyOnWriteArraySet s = new CopyOnWriteArraySet();
		s.add(1);
		s.add(2);
		s.add(3);
		
		for (Object o : s) {
			System.out.println(o);
			s.add(10 * (Integer)o);
		}
		
		
		
		//Concurrent collections' iterators are weakly consistent, that is, they will refer to the elements of iterator's creation time or later.
		System.out.println("------Concurrent Map--------");
		ConcurrentHashMap m = new ConcurrentHashMap();
		m.put("1", 1);
		m.put("2", 2);
		
		for (Object o : m.keySet()) {
			System.out.println(m.get(o));
			if ("1".equals(o)) {
				m.put("1.5", 1.5); //<- Iterator will also print 1.5 because it was added while it was iterating. Note: It wouldn't happen if this would be called in the last iteration.
			}
		}
		
		//Other concurrent collections
		//Pros: Threads can read and write concurrently without copying the internal arrays
		//Cons: Inconsistent during iteration
		ConcurrentLinkedDeque cc2 = new ConcurrentLinkedDeque();
		ConcurrentLinkedQueue cc3 = new ConcurrentLinkedQueue();
		ConcurrentSkipListMap cc4 = new ConcurrentSkipListMap();
		ConcurrentSkipListSet cc5 = new ConcurrentSkipListSet();
		
		//ConcurrentMap and ConcurrentSkipListMap implement the ConcurrentMap interface. Demo below.
		System.out.println("------Concurrent Map putIfAbsent demo--------");
		ConcurrentMap cm1 = new ConcurrentHashMap();
		ConcurrentMap cm2 = new ConcurrentSkipListMap();
		cm1.putIfAbsent(1, 1); //This is thread safe
		cm1.putIfAbsent(2, 2);
		cm1.putIfAbsent(1, 3);
		System.out.println(cm1.keySet().size()); //This will print '2'
		
		//ConcurrentSkipListMap and ConcurrentSkipListSet are sorted and require their elements to be Comparable
		
		class NotComparable {
		
		}
		
		class MyComparable implements Comparable {
			public int compareTo(Object o) {
				return 0;
			}
		}
		
		cc4.putIfAbsent(new MyComparable(), new MyComparable());
		//cc4.putIfAbsent(new NotComparable(), new NotComparable()); //This would throw a ClassCastException (or compiler would complain if it would be generic)
		
		
		
		//BlockingQueues
		
		System.out.println("------Blocking Queues--------");
		
		ArrayBlockingQueue b1 = new ArrayBlockingQueue(20);
		LinkedBlockingDeque b2 = new LinkedBlockingDeque();
		LinkedBlockingQueue b3 = new LinkedBlockingQueue();
		PriorityBlockingQueue b4 = new PriorityBlockingQueue();
		DelayQueue b5 = new DelayQueue();
		LinkedTransferQueue b6 = new LinkedTransferQueue();
		SynchronousQueue b7 = new SynchronousQueue();
		
		
		//Common methods of blocking queues
		
		b1.add(1); //true if added, false if duplicates not allowed, IllegalStateException if full
		b1.offer(2); //true if added, false if full
		try {
			b1.put(3); //void. Will block when necessary to wait for space in the queue. As always in such cases, can throw an InterruptedException.
			b1.offer(4, 500, TimeUnit.MILLISECONDS); //true if added, false if full even after timeout. Blocks until timeout expires or element added. Throws InterruptedException
		} catch (InterruptedException e) {
		
		}
		b1.remove(5); //true if found and removed, false otherwise.
		
		try {
			b1.poll(500, TimeUnit.MILLISECONDS); //Removes and returns first element from the head or null if there is none even after timeout. InterruptedException. Blocking.
			b1.take(); //Removes and returns first element. Blocks until it is there. InterruptedException.
		} catch (InterruptedException e) {
		
		}
		b1.poll(); //Removes and returns first element or null if there is none.
		b1.element(); //Returns the head without removing it. Throws NoSuchElementException if there is none.
		b1.peek(); //Returns the head without removing it or null if there is none.
		
		
		//LinkedTransferQueue is the most powerfull implementation of a blocking queue. Here is the showcase of what it can do.
		
		TransferQueue<Integer> ltq = new LinkedTransferQueue<>(); 
		boolean retVal;
		
		retVal = ltq.add(1);
		
		try {
			//Take care! Only bounded queues will declare an InterruptedException in put because other queues will not block during put. If you change the declaration of ltq variable to the LinkedTransferQueue type, no try catch is needed (or allowed) here.
			ltq.put(2); 
		} catch (InterruptedException e) {
		
		}
		
		retVal = ltq.offer(3);
		
		try {
			ltq.offer(4, 500, TimeUnit.MILLISECONDS);
			//ltq.transfer(5); //Would block until this element is consumed
			retVal = ltq.tryTransfer(7, 500, TimeUnit.MILLISECONDS); //false if not consumed in a time interval
		} catch (InterruptedException e) {
		
		}
		
		retVal = ltq.tryTransfer(6); //true if consumed, false otherwise
		
		Integer value;
		
		value = ltq.element();
		value = ltq.peek();
		value = ltq.poll();
		try {
			value = ltq.poll(500, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		
		}
		
		value = ltq.remove();
		
		try {
			value = ltq.take();
		} catch (Exception e) {
		
		}
		
		
		
	}
	
}