import java.util.*;
import java.util.stream.*;
import java.util.concurrent.*;

public  class ForkJoinPools {

	public static void main(String[] args) {
		List<String> l = IntStream.range(0,32).mapToObj(n -> "anton" + n).collect(Collectors.toList());
		
		
		
		class MyRecursiveAction extends RecursiveAction { //RecursiveAction is in package java.util.concurrent
		
			private List<String> list;
			private int start;
			private int end;
			
			public MyRecursiveAction(List<String> list) {
				this(list, 0, list.size());
			}
			
			public MyRecursiveAction(List<String> list, int start, int end) {
				this.list = list;
				this.start = start;
				this.end = end;
			}
		
			public void compute() { //RecursiveAction defines the abstract method compute.
				if (end - start <= 3) { //Handle the specific case that will break the recursion.
				
					System.out.println(String.format("(%s) %d-%d: Entering the special case.", Thread.currentThread(), start, end));
				
					elementaryOperation(start, end);
				} else {
					
					System.out.println(String.format("(%s) %d-%d: Entering the general case.", Thread.currentThread(), start, end));
					
					int middle = start + (end - start) / 2; //Divide the work load in two.
					
					MyRecursiveAction otherTask = new MyRecursiveAction(list, start, middle); //Create new RecursiveAction for the first half of the work load.
					MyRecursiveAction myTask = new MyRecursiveAction(list, middle, end); //Create new RecursiveAction for the second half of the work load.
					
					System.out.println(String.format("(%s) %d-%d: Forking %d-%d.", Thread.currentThread(), start, end, start, middle));
					
					otherTask.fork(); //Fork the first half of the work load
					
					System.out.println(String.format("(%s) %d-%d: Computing %d-%d.", Thread.currentThread(), start, end, middle, end));
					
					myTask.compute(); //Compute the second half of the work load by calling compute() recursively.
					
					System.out.println(String.format("(%s) %d-%d: Waiting for join.", Thread.currentThread(), start, end));
					
					otherTask.join(); //Wait for the other task to finish. Help with remaining tasks if there are any submitted.
					
					//Alternatively use:
					//invokeAll(otherTask, myTask);
					
				
				}
				
			}
			
			private void elementaryOperation(int start, int end) {
				for (int i = start; i < end; i++) {
					String s = list.get(i);
					list.set(i, s.toUpperCase());
				}
			}
						
			
		}
		
					
		System.out.println(l);
		
		ForkJoinPool pool = new ForkJoinPool(); //Create the fork join pool
		pool.invoke(new MyRecursiveAction(l)); //Invoke it with the recursive action
		System.out.println(l);
		
	}

}