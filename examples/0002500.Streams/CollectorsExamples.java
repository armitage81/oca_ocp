import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import java.util.stream.*;
import java.lang.management.*;

public class CollectorsExamples {
	
	private static int counter = 0;
	
	public static void main(String[] args) {
		
		
		
		//This demonstrates the flat map. Note: flatMap uses mapper to make a stream of each element of the original stream. Resulting streams are chained together.
		m("flatMap", () -> {
			Stream<Integer> s1 = Stream.of(1,2,3);
			Stream<Integer> s2 = Stream.of(4,5,6);
			
			Stream.of(s1, s2).flatMap(s -> s).forEach(e -> {System.out.println(">" + e + "<");});
			
			//Note: The stream objects s1 and s2 are referring to cannot be used any more because the streams have been used in a flatmap already and a terminal operation was executed.
			s1 = Stream.of(1,2,3); 
			s2 = Stream.of(4,5,6);
			
			//Note: The mapper in the flatMap operates on single elements in this stream but returns streams.
			Stream.of(s1, s2).flatMap(e -> e.limit(2)).forEach(System.out::println);
			System.out.println("---------");
			//Here we create a stream out of each integer element of the original IntStream and chain them together.
			IntStream.rangeClosed(1,5).flatMap(n -> IntStream.rangeClosed(1, n)).forEach(System.out::println);
			
		});
		
		m("grouping and partitioning", () -> {
			System.out.println(Stream.of(1,2,3,4,5,6,7,8).collect(Collectors.groupingBy(e -> e % 2 == 0)));
			System.out.println(Stream.of(1,2,3,4,5,6,7,8).collect(Collectors.partitioningBy(e -> e % 2 == 0)));
			
			Map<Boolean, List<Integer>> m1 = Stream.<Integer>empty().collect(Collectors.groupingBy(e -> e % 2 == 0));
			Map<Boolean, List<Integer>> m2 = Stream.<Integer>empty().collect(Collectors.partitioningBy(e -> e % 2 == 0));
			
			System.out.println(m1);
			System.out.println(m2);

			
		});
		
		
		m("parallel with flatMap", () -> {
			
			Stream<String> s1 = Stream.generate(() -> "1").limit(100).parallel();
			Stream<String> s2 = Stream.of("4", "5", "6").parallel();
			
			Stream<String> s3 = Stream.of(s1, s2).flatMap(s -> s).peek(System.out::println); //flatMap returns a sequential stream even if the underlying streams are parallel.
			System.out.println(s3.count());

		});
		
		
		m("parallel streams", () -> {
			
			Stream.iterate(0, (i) -> i + 5).limit(10).parallel().sorted().forEach(System.out::println); //Note: this will not sort properly.
			System.out.println("-----------------------------");
			Stream.iterate(0, (i) -> i + 5).limit(10).parallel().sequential().sorted().forEach(System.out::println); //This will sort properly

		});
		
		
		
		m("Collecting and reducing parallel streams", () -> {
			//Reducing
			int n = Stream.iterate(0, (i) -> i + 5).limit(10).parallel().reduce(0, (a, b) -> a + b, (a, b) -> a + b);
			System.out.println(n);
			
			//And now reducing with anonymous classes
			n = Stream.iterate(0, (i) -> i + 5).limit(10).parallel().reduce(
			0, 
			new BinaryOperator<Integer>() {
				public Integer apply(Integer i1, Integer i2) {
					return i1 + i2;
				}
			}, 
			new BinaryOperator<Integer>() {
				public Integer apply(Integer i1, Integer i2) {
					return i1 + i2;
				}
			});
			System.out.println(n);
			
			
				
			//Collecting
			System.out.println("-----------------------------");
			StringBuilder strB = Stream.iterate(0, (i) -> i + 5).limit(10).parallel().map(i -> i * 10).collect(StringBuilder::new, (sb, i) -> {sb.append(i);}, (sb1,sb2) -> {sb1.append(sb2);});
			System.out.println(strB);
			
			//And now collecting with anonymous classes
			strB = Stream.iterate(0, (i) -> i + 5).limit(10).parallel().map(i -> i * 10).collect(
				new Supplier<StringBuilder>() {
					public StringBuilder get() {
						return new StringBuilder();
					}
				}, 
				new BiConsumer<StringBuilder, Integer>() {
					public void accept(StringBuilder sb, Integer i) {
						sb.append(i);
					}
				},
				new BiConsumer<StringBuilder, StringBuilder>() {
					public void accept(StringBuilder sb, StringBuilder sb2) {
						sb.append(sb2);
					}
				}
			);
			System.out.println(strB);

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
