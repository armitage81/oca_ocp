import java.util.concurrent.*;

public class VariousExecutors {
	
	public static void main(String[] args) {
		
		//Using executor service
		
		//This is the single thread executor
		Executor executor = new Executor() {
			@Override
			public void execute(Runnable command) {
				command.run();
			}
		};
		
		//This is the thread per command executor
		Executor executor2 = new Executor() {
			@Override
			public void execute(Runnable command) {
				Thread t = new Thread(command);
				t.start();
			}
		};
		
		//Let's try the executors.
		
		Runnable task = new Runnable() {
			public void run() {
				System.out.println("A");
				System.out.println("B");
				System.out.println("C");
				System.out.println("D");
				System.out.println("E");
				System.out.println("F");
				System.out.println("G");
				System.out.println("H");
				System.out.println("I");
				System.out.println("J");
			}
		};
		
		executor.execute(task);
		executor2.execute(task);
		executor2.execute(task);
		executor2.execute(task);
		executor2.execute(task);





		//Java's Executor implementations (Note, they implement ExecutorService which is a sub interface of Executor)

		//Executors is a factory that creates ThreadPoolExecutor objects (that implements ExecutorService that extends Executor). Cached threads are removed from the pool after 60 seconds of idleness.
		ExecutorService ex1 = Executors.newCachedThreadPool(); 
		
		//A fixed thread pool limited to 4 threads. Tasks that are not able to be picked up immediately, will be put on an unbounded blocked queue.
		ExecutorService ex2 = Executors.newFixedThreadPool(4); 
		
		//One thread, that executor cannot be modified with different thread size later as opposed to the fixed thread pool
		ExecutorService ex3 = Executors.newSingleThreadExecutor(); 
		
		//ScheduledExecutorService ftses = Executors.newScheduledThreadPool(4); // multi-threaded version
		//ftses.schedule(task, 5, TimeUnit.SECONDS); // run once after a delay
		//ftses.scheduleAtFixedRate(task, 2, 5, TimeUnit.SECONDS); //begin after a 2sec delay and begin again every 5 seconds
		//ftses.scheduleWithFixedDelay(task, 2, 5, TimeUnit.SECONDS); // begin after 2sec delay and begin again 5 seconds *after* completing the last execution

		//An executor service can use Callable instead of Runnable
		
		
		System.out.println("----------Example of Callable execution---------------");
		
		
		Callable<Long> c = new Callable() {
			public Long call() throws Exception {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					throw new Exception(e);
				}
				return ThreadLocalRandom.current().nextLong(1, 11); // A better way to use randomizing in a thread rathar than Random or Math.random()
			}
		};

		
		ExecutorService ex = Executors.newFixedThreadPool(4);
		Future<Long> f1 = ex.submit(c);
		Future<Long> f2 = ex.submit(c);
		Future<Long> f3 = ex.submit(c);
		Future<Long> f4 = ex.submit(c);
		ex.shutdown();
		
		try {
			System.out.println(f1.get());
			System.out.println(f2.get());
			System.out.println(f3.get());
			System.out.println(f4.get());
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}
		
		

		
	}
	
}