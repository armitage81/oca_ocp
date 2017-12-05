import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.lang.management.*;

public class ConcurrentCollections3 {
	
	private static int counter = 0;
	
	public static void main(String[] args) {
		
		
		
		//This demonstrates how changes in the ConcurrentSkipListSet are reflected in the iterator
		m("ConcurrentSkipListSet", () -> {
			
			try {
			
				ConcurrentSkipListSet<Integer> s = new ConcurrentSkipListSet<Integer>();	
				s.add(1);
				s.add(2);
				s.add(3);
				s.add(4);
				s.add(5);
				//s.add(null); Would throw a null pointer exception.
				
				//Functional initialization. Uncommented here because later lambda requires an effectively final variable
				//s = new ConcurrentSkipListSet<Integer>(Stream.of(1,2,3,4,5).collect(Collectors.toList()));
				
				Iterator<Integer> it = s.iterator();
				
				ExecutorService es = Executors.newFixedThreadPool(1);
				
				//Note: other thread will remove a number each 50 millis and add a 100*number instead.
				for (int i = 0; i < 20; i++) {
					final int ii = i;
					es.submit(() -> {try {Thread.sleep(50);} catch(InterruptedException e) {e.printStackTrace();} s.remove(ii); s.add(100 * ii);});
				}
				
				es.shutdown();
				
				//Give the other thread time to replace ca. 2 numbers.
				Thread.sleep(100);
				
				while(it.hasNext()) {
					
					int n = it.next();
					
					Thread.sleep(70);
					
					System.out.println(n);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			ConcurrentSkipListSet<Integer> s2 = new ConcurrentSkipListSet<Integer>();
			s2.add(4);
			s2.add(6);
			s2.add(8);
			s2.add(10);
			Iterator<Integer> it2 = s2.iterator();
			while (it2.hasNext()) {
				System.out.println(it2.next());
				s2.add(0);
				s2.add(2);
				s2.add(12);
			}
			
			
			
		});
		
		
		m("ConcurrentSkipListSet - iterators and concurrent modification", () -> {
			
			//Note: ConcurrentSkipListSet is ordered (it implements NavigableSet and SortedSet)
			//We are attempting to add 0, 2 and 12 in each iteration. Because it is a set, addition happens only once.
			//Since the iterator is already at element 4 in the first iteration, it never goes through added 0 and 2, but it goes through 12
			
			try {
			
				ConcurrentSkipListSet<Integer> s = new ConcurrentSkipListSet<Integer>();
				s.add(4);
				s.add(6);
				s.add(8);
				s.add(10);
				Iterator<Integer> it = s.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
					s.add(0);
					s.add(2);
					s.add(12);
				}	
				
				//This time, the set is sorted in descending order, so 12 is never reflected in the iterator, (it was added after the first iteration) but 0 and 2 are.
				
				System.out.println("----------------------------------------");
				
				s = new ConcurrentSkipListSet<Integer>((a, b) -> b - a);
				
				s.add(4);
				s.add(6);
				s.add(8);
				s.add(10);
				it = s.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
					s.add(0);
					s.add(2);
					s.add(12);
				}	
				
			
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		});
		
		m("CopyOnWriteArrayList - iterator", () -> {
						
			//Note: Changes on CopyOnWriteArrayList are not reflected in the iterator.
			try {
			
				int i = 3;
				CopyOnWriteArrayList<Integer> l = new CopyOnWriteArrayList<Integer>();	
				l.add(1);
				l.add(2);
				Iterator<Integer> it = l.iterator();
				while (it.hasNext()) {
					System.out.println(it.next());
					l.add(i++);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		});
		
		
		m("CopyOnWriteArrayList - memory", () -> {
						
			//Note: Changes on CopyOnWriteArrayList are not reflected in the iterator.
			try {
			
				CopyOnWriteArrayList<Integer> l = new CopyOnWriteArrayList<Integer>();	
				Iterator<Integer> it;
				
				while (true) {
					l.add(1000);
					l.remove(0);
				}
			
			} catch (Exception e) {
				e.printStackTrace();
			}
						
		});
		
		
		
		
	}
	
	@SuppressWarnings({"HardCodedStringLiteral"})
    public static String getThreadDumps()
    {
        ThreadInfo[]  threadInfos = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
        StringBuilder dump = new StringBuilder();
        dump.append(String.format("%n"));
        for (ThreadInfo threadInfo : threadInfos) {
            dump.append(threadInfo);
        }
        return "++++++++++++++++++++++\n" + dump.toString() + "\n-------------------------";
    }
	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		System.out.println("-----------------------------");
		r.run();
		System.out.println();
	}
	
}
