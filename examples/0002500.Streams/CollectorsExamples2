import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class CollectorsExamples2 {
	
	public static int counter = 0;

	
	public static void main(String[] args) {
				
		m("Various collectors" , () -> {
			
			Supplier<Stream<Integer>> s = () -> Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
			Supplier<Stream<String>> s2 = () -> Stream.of("1", "2", "3", "4", "5", "6", "7", "8", "9");
			
			System.out.println(s.get().collect(Collectors.averagingDouble(x -> x)));
			System.out.println(s2.get().collect(Collectors.averagingDouble(Double::valueOf)));
			
			System.out.println(s.get().collect(Collectors.averagingInt(x -> x)));
			System.out.println(s2.get().collect(Collectors.averagingInt(Integer::valueOf)));
			
			System.out.println(s.get().collect(Collectors.averagingLong(x -> x)));
			System.out.println(s2.get().collect(Collectors.averagingLong(Long::valueOf)));
			
			System.out.println(s.get().collect(Collectors.collectingAndThen(Collectors.<Integer>averagingInt(x -> x), (Double y) -> y * 2)));
			
			System.out.println(s.get().collect(Collectors.counting()));
			
			System.out.println(s.get().collect(Collectors.groupingBy(x -> x % 3)));
			
			System.out.println(s2.get().collect(Collectors.joining()));
			
			System.out.println(s2.get().collect(Collectors.toList()));
			
		});
		
	}

	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}
	
}
