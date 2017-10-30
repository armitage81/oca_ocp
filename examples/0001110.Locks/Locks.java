import java.util.concurrent.locks.Lock; //General lock interface
import java.util.concurrent.locks.ReentrantLock; //Main lock implementation
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.TimeUnit;
import java.util.*;

public class Locks {


	public static void main(String[] args) throws Exception {	

		Object obj = new Object();
		
		//This is normal locking with a synchronized keyword.
		synchronized(obj) { 
			System.out.println("Sync block");
		}
		
		
		//This is the equivalent locking with a reentrant lock.
		Lock lock = new ReentrantLock();
		lock.lock();
		
		try { //Not necessary, but recommended to always be able to unlock the lock.
			System.out.println("Sync block");
		} finally {
			lock.unlock();
		}
		
		
		//You can react to a locked resource (as opposed to the synchronized block where you just would wait)
		
		lock = new ReentrantLock();
		boolean locked = lock.tryLock();
		if (locked) { //This is the case if you managed to lock an open lock.
			try {
				System.out.println("Locked");
			} finally {
				lock.unlock();
			}
		} else { //This happens if the lock was already locked by another thread. Instead of waiting in a blocked state, this thread will react and go on.
			System.out.println("Not locked");
		}
	
		//You can decide to wait a definite time before reacting to a locked lock. 
		lock = new ReentrantLock();
		try {
			locked = lock.tryLock(3, TimeUnit.SECONDS); //This can throw an InterruptedException like all blocked methods.
			if (locked) {
				try {
					System.out.println("Locked");
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException ex) {
			
		}
	
	
		//If locking with multiple synchronized blocks they must be always locked in the same order to avoid deadlocks. 
		//With Lock, this is not necessary.
	
		Lock l1 = new ReentrantLock();
		Lock l2 = new ReentrantLock();
	
		Thread t = new Thread(new Runnable() {
				public void run() {			
					l1.lock();
					l2.lock();
					try {
						System.out.println("Sleeping.");
						Thread.sleep(5000);
						System.out.println("Awake.");
					} catch (InterruptedException e) {
						
					} finally {
						l1.unlock();
						l2.unlock();
					}
				}
			}
		);
		
		t.start();
	
		Thread.sleep(5000); //To let the other thread lock the l1 and l2 locks first.
	
		boolean aq1 = l1.tryLock();  //This prevents deadlocks, but you need to handle the case when the lock is not obtainable, e.g. a loop. But this approach is CPU intensive, there is a better way (below).
		boolean aq2 = l2.tryLock();
		try {
			if (aq1 && aq2) {
				System.out.println("Locked by this thread.");
			} else {
				System.out.println("Was already locked by another thread.");
			}
		} finally {
			if (aq2) {
				l2.unlock();  //Important! It is not allowed to unlock a lock that is not locked by the same thread. The condition aq2 checks that this thread has locked the lock and unlocks it only then.
			}
			if (aq1) {
				l1.unlock();
			}
		}
	
	
	
		//ReentrantReadWriteLock objects can be used to restrict only the writers, while many readers can access the code at the same time.
		
		class MaxValueCollection {
			private List<Integer> integers = new ArrayList<>();
			
			private Integer count = new Integer(0);
			
			private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
			
			public void add(Integer i) {
				
				System.out.println("before writing " + count);
			
				rwl.writeLock().lock(); // one at a time
				try {				
					integers.add(i);
				} finally {
					
					System.out.println("after writing " + count);
				
					synchronized(count) {
						count++;
					}
				
					rwl.writeLock().unlock();
				}		
				
				
			}
			
			public int findMax() {
			
				System.out.println("before reading " + count);
			
				rwl.readLock().lock(); // many at once
				try {
					return Collections.max(integers);
				} finally {
					System.out.println("after reading " + count);
					synchronized(count) {
						count++;
					}
					rwl.readLock().unlock();
				}
				
				
			}
		}
	
		class ReaderThread extends Thread {
		
			private MaxValueCollection mvc;
			private int counter = 0;
			
			public ReaderThread(MaxValueCollection mvc) {
				this.mvc = mvc;
			}
			
			public void run() {
				for (int i = 0; i < 100; i++) {
					mvc.findMax();
				}
			}
		}
		
		class WriterThread extends Thread {
		
			private MaxValueCollection mvc;
			
			public WriterThread(MaxValueCollection mvc) {
				this.mvc = mvc;
			}
			
			public void run() {
				for (int i = 0; i < 100; i++) {
					mvc.add(1);
				}
				
			}
		}
	
		MaxValueCollection mvc = new MaxValueCollection();
		
		WriterThread wt = new WriterThread(mvc);
		ReaderThread rt1 = new ReaderThread(mvc);
		ReaderThread rt2 = new ReaderThread(mvc);
		ReaderThread rt3 = new ReaderThread(mvc);
		ReaderThread rt4 = new ReaderThread(mvc);
		ReaderThread rt5 = new ReaderThread(mvc);
		
		wt.start();
		rt1.start();
		rt2.start();
		rt3.start();
		rt4.start();
		rt5.start();
		
		
		wt.join();
		rt1.join();
		rt2.join();
		rt3.join();
		rt4.join();
		rt5.join();
	
	
	}

}