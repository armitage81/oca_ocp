import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {

	static {
		System.out.println("Found the class");
	}

	static class Counter {
		private int count;
		
		public int getAndIncrement() {
			return count++;
		}
		
	}
	
	static class ThreadSafeCounter {
		private AtomicInteger count = new AtomicInteger(); //This needs an import and initialization
		
		public int getAndIncrement() {
			return count.getAndIncrement(); //Note: Even this call is not atomic, but it uses (like all the other "atomic" methods the compareAndSet loop, which probably relies on native CPU's CAS mechanism)
		}
		
	}
	
	static class ThreadIncrementer extends Thread {
	
		private Counter counter;
	
		public ThreadIncrementer(Counter c) {
			this.counter = c;
		}
	
		public void run() {
			for (int i = 0; i < 100000; i++) {
				counter.getAndIncrement();
			}
		}
	}
	
	static class ThreadIncrementerWithThreadSafeCounter extends Thread {
	
		private ThreadSafeCounter counter;
	
		public ThreadIncrementerWithThreadSafeCounter(ThreadSafeCounter c) {
			this.counter = c;
		}
	
		public void run() {
			for (int i = 0; i < 100000; i++) {
				counter.getAndIncrement();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		Counter counter = new Counter();
		
		ThreadIncrementer t1 = new ThreadIncrementer(counter);
		ThreadIncrementer t2 = new ThreadIncrementer(counter);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println(counter.getAndIncrement()); //Note how this will not produce the output 200000 (most probably). The statement count++ is not atomic.
		
		//We don't want to use heavy sync blocking, instead we use an AtomicInteger. Check the ThreadSafeCounter class
		ThreadSafeCounter threadSafeCounter = new ThreadSafeCounter();
		ThreadIncrementerWithThreadSafeCounter t3 = new ThreadIncrementerWithThreadSafeCounter(threadSafeCounter);
		ThreadIncrementerWithThreadSafeCounter t4 = new ThreadIncrementerWithThreadSafeCounter(threadSafeCounter);
		
		t3.start();
		t4.start();
		t3.join();
		t4.join();
		
		//Now, the printed value will be 200000
		System.out.println(threadSafeCounter.getAndIncrement());
		
		
	}

}