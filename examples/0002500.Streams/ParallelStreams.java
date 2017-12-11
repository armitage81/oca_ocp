import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class ParallelStreams {
	
	public static int counter = 0;
	public static boolean flag = false;
	
	public static void main(String[] args) {
				
		m("parallel vs sequential" , () -> {
			
			Supplier<Stream<Integer>> s = () -> Stream.of(1,2,3, 4, 5, 6, 7, 8, 9);
			
			System.out.println("Parallel: " + s.get().isParallel());
			System.out.println("Parallel: " + s.get().parallel().isParallel());
			System.out.println("Parallel: " + s.get().parallel().parallel().isParallel());
			System.out.println("Parallel: " + s.get().parallel().sequential().isParallel());
			System.out.println("Parallel: " + s.get().parallel().peek((e)->{}).isParallel());
		});
		
		
		m("What is executed in parallel?" , () -> {
			Supplier<Stream<Integer>> s = () -> Stream.iterate(0, i -> i + 1).limit(10);			
			s.get().peek(e -> consume("A", e)).parallel().peek(e -> consume("B", e)).sequential().peek(e -> consume("C", e)).count(); //Note: all will be executed sequentially
			System.out.println("---------------------------");
			s.get().peek(e -> consume("A", e)).parallel().peek(e -> consume("B", e)).count(); //Note: All will be executed in parallel
			System.out.println("---------------------------");
			s.get().parallel().peek(e -> consume("A", e)).collect(Collectors.toList()).stream().peek(e -> consume("B", e)).count(); //Parallel and sequential steps by using two streams
			
		});
		
		Comparator<Integer> c = (a,b) -> a - b;
		
		m("Which terminal operations order a parallel stream?" , () -> {
			Supplier<Stream<Integer>> s = () -> Stream.iterate(0, i -> i + 1).limit(10).parallel();
			System.out.println(s.get().findAny().get()); //Random
			System.out.println(s.get().findFirst().get()); //0
			System.out.println(s.get().min(c::compare).get()); //0
			System.out.println(s.get().max(c::compare).get()); //9
			
		});
		
		m("Streams change their parallelism during concatenation depending on the method" , () -> {
			Supplier<Stream<Integer>> s1 = () -> Stream.of(1,2,3).parallel();
			Supplier<Stream<Integer>> s2 = () -> Stream.of(4,5,6).parallel();
			
			System.out.println(s1.get().isParallel()); //true
			System.out.println(s2.get().isParallel()); //true
			System.out.println(Stream.concat(s1.get(), s2.get()).isParallel()); //true
			System.out.println(Stream.of(s1.get(), s2.get()).flatMap(s -> s).isParallel()); //false
			
		});
		
		m("sorted() after parallel()" , () -> {
			
			System.out.println("-------------------------------");
			
			Supplier<Stream<Integer>> s = () -> Stream.iterate(0, i -> i + 1).limit(10).parallel();
			
			s.get().forEach(System.out::print); //Random
			System.out.println();
			s.get().sequential().forEach(System.out::print); //Sorted
			System.out.println();
			s.get().forEachOrdered(System.out::print); //Sorted
			System.out.println();
			s.get().sorted().forEach(System.out::print); //Random
			
			System.out.println();
			
			s.get().map(a -> a + 1).forEach(System.out::println);
			
		});
		
		
		m("position of unordered()" , () -> {
			
			
			
			System.out.println("-------------------------------");
			
			Supplier<Stream<Character>> s = () -> Stream.iterate('A', i -> (char)(i + 1)).limit(26);
			
			s.get().forEach(System.out::print); //Result is ordered
			
			System.out.println();
			System.out.println("-------------------------------");
			
			s.get().unordered().forEach(System.out::print); //Result is still ordered (only because stream is sequential, it is not ordered in its nature)
			
			System.out.println();
			System.out.println("-------------------------------");
			
			s.get().parallel().forEach(System.out::print); //Result is not ordered (forEach does not order the stream output and because of the parallelity of the stream, it is printing without order)
			
			System.out.println();
			System.out.println("-------------------------------");
			
			s.get().parallel().forEachOrdered(System.out::print); //Result is ordered again (forEachOrdered orders the stream output even if the stream is parallel)
			
			System.out.println();
			System.out.println("-------------------------------");
			
			s.get().parallel().sorted((a,b) -> b - a).forEach(System.out::print); //NOTE! This sorts the input in reverse but as forEach is unordered, the output is random
			
			System.out.println();
			System.out.println("-------------------------------");
			
			s.get().parallel().sorted((a,b) -> b - a).forEachOrdered(System.out::print); //NOTE! This sorts the input in reverse and, as forEachOrdered is ordered, the output is ordered in reverse
			
			System.out.println();
			System.out.println("-------------------------------");
			
		});
		
		
		m("ordered and unordered streams" , () -> {
			
			
			
			System.out.println("-------------------------------");
			
			Supplier<Stream<Integer>> s = () -> Stream.iterate(0, i -> {
				i += flag ? 1 : 0;
				flag = !flag;
				return i;
			}).limit(1000000).parallel();
			
			long l1 = System.currentTimeMillis();
			s.get().parallel().distinct().count(); //Takes ca. 5700 ms
			long l2 = System.currentTimeMillis();
			s.get().parallel().unordered().distinct().count(); //Takes ca. 300 ms. Note: Unordered processing is much faster for some operations
			long l3 = System.currentTimeMillis();
			s.get().unordered().distinct().parallel().count(); //Is still fast
			long l4 = System.currentTimeMillis();
			s.get().parallel().distinct().unordered().count(); 
			long l5 = System.currentTimeMillis();
			
			System.out.println("Ordered execution time: " + (l2 - l1));
			System.out.println("Unordered execution time: " + (l3 - l2));
			System.out.println("Another unordered execution time: " + (l4 - l3));
			System.out.println("Yet another unordered execution time: " + (l5 - l4));
			
		});
		
		
		
		
		
		
		
	}
	
	public static Random r = new Random();
	
	public static void consume(String prefix, Integer i) {
		System.out.println(prefix + i);
		try {
			Thread.sleep(r.nextInt(50));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}
	
}
