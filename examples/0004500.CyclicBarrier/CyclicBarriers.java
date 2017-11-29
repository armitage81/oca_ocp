import java.util.concurrent.*;

public class CyclicBarriers {

	private static CyclicBarrier barrier;

	public static void main(String[] args) {	
	
		class Worker implements Runnable {
			
			private long count;
			private int n;
			
			public Worker(int n) {
				this.n = n;
			}
			
			public void run() {
				try {
					while(true) {
						
						count++;
						System.out.println("..............................".substring(0, n) + " " + count + ":[" + n + "]");
						Thread.sleep(1000 * n);
						if (count % 10 == 0) {
							barrier.await();
							System.out.println(Thread.currentThread() + " SYNC " + count);							
						}
					}
				
				} catch (Exception e) {
					throw new RuntimeException();
				}
				
			}
		
		
		}
		
		barrier = new CyclicBarrier(10, ()-> {System.out.println("BARRIER");});
		
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Worker(i));
			t.start();
		}
	
	}

}