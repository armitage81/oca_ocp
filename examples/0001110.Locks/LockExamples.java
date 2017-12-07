import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.stream.*;
import java.time.temporal.*;

class LockExamples {

	public static int counter = 0;
	
	public static void main(String[] args) throws Exception {
		
		final ReentrantLock lock = new ReentrantLock();
		final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		
		m("ReentrantLock", () -> {
	
			//This example uses the reentrant lock to verify that numbers [10 - 20[ are printed in accending order 3 times in a row.
			//Note how 3 threads are printing the numbers [0-10[ in a randomly nested order (e.g. 000111232323...) because this part is not synchronized.
			//Numbers [10-20[ will be always printed in accending order. They will be not nested with each other but they can be nested with the printing of numbers [0-10[ and [20-30[
	
			try {
			
				Runnable r = () -> { 
					
					
					IntStream.range(0, 10).forEach((i) -> {System.out.println(i);try { Thread.sleep(new Random().nextInt(1000));} catch (Exception e) {throw new RuntimeException(e);}}); 
					System.out.println(String.format("Thread %s before Lock: (hold count = %s) (locked = %s)", Thread.currentThread().getName(), lock.getHoldCount(), lock.isLocked()));
					lock.lock();
					try {
						System.out.println(String.format("After lock: (hold count = %s) (locked = %s)", Thread.currentThread().getName(), lock.getHoldCount(), lock.isLocked()));
						IntStream.range(10, 20).forEach((i) -> {System.out.println(i);try { Thread.sleep(new Random().nextInt(1000));} catch (Exception e) {throw new RuntimeException(e);}}); 
						System.out.println(String.format("Before unlock: (hold count = %s) (locked = %s)", Thread.currentThread().getName(), lock.getHoldCount(), lock.isLocked()));
					} finally {
						lock.unlock();
					}
					System.out.println(String.format("After unlock: (hold count = %s) (locked = %s)", Thread.currentThread().getName(), lock.getHoldCount(), lock.isLocked()));
					IntStream.range(20, 30).forEach((i) -> {System.out.println(i);try { Thread.sleep(new Random().nextInt(1000));} catch (Exception e) {throw new RuntimeException(e);}}); 
					
					
				};
				
				ExecutorService exec = Executors.newFixedThreadPool(3);
				Stream.of(r, r, r).forEach(exec::execute);
				exec.shutdown();
				exec.awaitTermination(10, TimeUnit.MINUTES);
			
			} catch (Exception e ) {
				throw new RuntimeException(e);
			}
			
		});
		
		
		m("ReentrantReadWriteLock", () -> {
		
			/*
			
			ReentrantReadWriteLock has two locks: readLock and writeLock.
			Writing operations on a shared resource should be wrapped by locking and unlocking the writeLock, reading operations by readLock.
			
			Following thread state set is the invariant of each program with a read/write lock
			
			- No Locks acquired
			- Write Lock acquired
			- One or many read locks are acquired
			
			On the opposite, the following thread states are not possible
			
			- Multiple write locks are acquired
			- Write lock and one or many read locks are acquired
			
			The only additional advantage of the ReadWriteLock compared to Lock is that many readers can read a resource while being sure that no write happens on the same resource.
			
			*/
		
			try {
				int[] a = new int[] {0, 0, 0, 0, 0};
				
				Runnable reader = () -> { 
					IntStream.range(0, 10).forEach((i) -> {
						readWriteLock.readLock().lock();
						try {
							IntStream.range(0, 5).forEach((j) -> {System.out.print("|" + a[j] + "|");}); 
							System.out.println();
						} finally {
							readWriteLock.readLock().unlock();
						}
					});
				};
				
				Runnable writer = () -> { 
					IntStream.range(0, 100).forEach((i) -> {
						readWriteLock.writeLock().lock();
						try {
							IntStream.range(0, 5).forEach(j -> a[j]++); 
						} finally {
							readWriteLock.writeLock().unlock();
						}
					});
				} ;
				
				ExecutorService exec = Executors.newFixedThreadPool(4);
				Stream.of(reader, writer, writer, writer).forEach(exec::execute);  //First run has only one reader for better readability of the output.
				exec.shutdown();
				exec.awaitTermination(10, TimeUnit.MINUTES);
				
				exec = Executors.newFixedThreadPool(13);
				System.out.println("----------------------------------------");
				Stream.of(reader, reader, reader, reader, reader, reader, reader, reader, reader, reader, writer, writer, writer).forEach(exec::execute); //Multiple readers are also safe.
				exec.shutdown();
				exec.awaitTermination(10, TimeUnit.MINUTES);
			} catch (Exception e ) {
				throw new RuntimeException(e);
			}
			
		});
		
		
		
		
		
	}
	
	public static void m(String descr, Runnable r) throws Exception {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
