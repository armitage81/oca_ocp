import java.util.stream.*;
import java.util.*;
import java.util.regex.*;
import java.util.function.*;

public class StreamsAdvanced {

	public static int counter = 0;

	public static void main(String[] args) {
	
		m("Creating a stream from list and printing via method reference", () -> {		
			List<Integer> l = new ArrayList<>();
			l.add(1);
			l.add(2);
			l.add(3);
			l.add(4);
			l.add(5);
			l.stream().forEach(System.out::print);
		});
		
		m("Empty stream count returns 0", () -> {
			long i = Stream.empty().count();
			System.out.println(i);
		});
		
		m("Stream creation from array", () -> {
			Stream.of(1, 2, 3).forEach(System.out::print);
			Integer[] arr = new Integer[]{4,5,6};
			Arrays.stream(arr).forEach(System.out::print);
		});
		
		m("Stream builder. Note: it needs a generic to be assigned to the variable", () -> {
			
			Stream<String> stream = Stream.<String>builder().add("A").add("B").build();
			stream.forEach(System.out::print);
		
		});
		
		m("Stream::generate with a limit", () -> {
			
			Stream.<Integer>generate(() -> 5).limit(10).forEach(System.out::print);
		
		});
		
		m("Stream::iterate with a limit. Note: it needs a seed.", () -> {
			
			Stream.<Integer>iterate(1, n -> n * 2).limit(10).forEach(System.out::print);
		
		});
		
		
		m("There are three primitive streams for int, long and double", () -> {
			
			IntStream.of(1,2,3).forEach(System.out::print);
			LongStream.of(1,2,3).forEach(System.out::print);
			DoubleStream.of(1.5,2.5,3.5).forEach(System.out::print);
		
		});
		
		
		m("Ranges: closed and open. Only for IntStream and LongStream", () -> {
			
			IntStream.range(0, 4).forEach(System.out::print);
			IntStream.rangeClosed(0, 4).forEach(System.out::print);
		
			LongStream.range(0, 4).forEach(System.out::print);
			LongStream.rangeClosed(0, 4).forEach(System.out::print);
		
			//This will not compile
			//DoubleStream.range(0, 4).forEach(System.out::println);
			//DoubleStream.rangeClosed(0, 4).forEach(System.out::println);
			//Stream.range(0, 4).forEach(System.out::println);
			//Stream.rangeClosed(0, 4).forEach(System.out::println);
		
		
		});
		
		m("Stream from string", () -> {
			
			//chars() returns an IntStream
			"anton".chars().forEach(System.out::println);
			"anton".chars().peek((int i) -> System.out.println((char)i)).forEach(System.out::print);
		
		
		});
		
		m("Split stream", () -> {
			
			Pattern.compile(",").splitAsStream("a,b,c").forEach(System.out::print);
		
		});
		
		m("Stream::findAny returns an optional", () -> {
			
			Optional<String> opt = Stream.of("1", "2", "3").findAny();
			
			opt.ifPresent(System.out::print);
		
		});
		
		m("Stream collect to list", () -> {
			
			List<String> l = Stream.of("1", "2", "3").collect(Collectors.toList());
			
			System.out.println(l);
		
		});
		
		m("Stream skip skips the first n elements from the stream", () -> {
			
			Stream.of("1", "2", "3", "4", "5").skip(3).forEach(System.out::print);	
		
		});
		
		m("Stream map takes a function and can be used to transform elements", () -> {
			
			Stream.of("1", "2", "3", "4", "5").map(Integer::parseInt).map(n -> n * n).map(String::valueOf).map(s -> s + ",").forEach(System.out::print);	
		
		});
		
		m("Stream sorted", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("000", "CCC", "ccc", "1", "A", "a", "2", "B", "b"); 
			s.get().sorted().forEach(System.out::println);
			System.out.println("-------");
			s.get().sorted((s1,s2)->s1.length() - s2.length()).forEach(System.out::println);
		
		});
		
		m("Stream filter", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("000", "CCC", "ccc", "1", "A", "a", "2", "B", "b"); 
			s.get().filter(e -> e.length() == 1).forEach(System.out::println);
			System.out.println("-------");
			s.get().filter(e -> e.toLowerCase().equals("a")).forEach(System.out::println);
		
		});
		
		m("Stream reduce", () -> {
			Supplier<IntStream> s = () -> IntStream.rangeClosed(1, 6); 
			System.out.println(s.get().reduce(1, (a, b) -> a * b));
		
		});
		
		m("Stream collector joining", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("A", "B", "C"); 
			System.out.println(s.get().collect(Collectors.joining(",")));
			System.out.println(s.get().collect(Collectors.joining(",", "[", "]")));
		
		});
		
		m("Stream avg and sum int gets a ToIntFunction to map each el", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("100", "200", "100", "0"); 
			System.out.println(s.get().collect(Collectors.averagingInt(Integer::parseInt)));
			System.out.println(s.get().collect(Collectors.summingInt(Integer::parseInt)));
		
		});
		
		m("Stream summary statistics", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("100", "200", "100", "0"); 
			IntSummaryStatistics is = s.get().collect(Collectors.summarizingInt(Integer::parseInt));
			System.out.println(is);
		});
		
		m("Stream groupingBy", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("Anton", "Alex", "Vala", "Sergej", "Ella", "Julia"); 
			Map<Integer, List<String>> m = s.get().collect(Collectors.groupingBy(String::length));
			System.out.println(m);
		});
		
		m("Stream partitioningBy", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("Anton", "Alex", "Vala", "Sergej", "Ella", "Julia"); 
			Map<Boolean, List<String>> m = s.get().collect(Collectors.partitioningBy(str -> str.length() >= 5));
			System.out.println(m);
		});
		
		m("Stream collectingAndThen", () -> {
			Supplier<Stream<String>> s = () -> Stream.of("Anton", "Alex", "Vala", "Sergej", "Ella", "Julia"); 
			String x = s.get().collect(Collectors.collectingAndThen(Collectors.partitioningBy(str -> str.length() >= 5), m -> m.toString()));
			System.out.println(x);
		});
		
		m("Streams: custom collector", () -> {
		
			Collector<Integer, ?, Integer> c = Collector.of(
				() -> (int)Math.random(),
				(a, b) -> System.out.println(a + " " + b),
				(a, b) -> a + b
			);
		
			Supplier<Stream<Integer>> s = () -> Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10); 
			int n = s.get().collect(c);
			System.out.println(n);
			
		});
		
	
	}

	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}

}