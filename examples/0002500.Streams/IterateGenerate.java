import java.util.stream.*;
import java.util.*;
import java.util.regex.*;
import java.util.function.*;

public class IterateGenerate {

	public static int counter = 0;

	public static void main(String[] args) {
	
		//Various IntStream iterations. iterate starts with the given value and applies the function to it before storing the new result internally.
		
		//It uses IntUnaryOperator =>  int applyAsInt(int operand)
	
		m("Iterate", () -> {		
			@SuppressWarnings("unchecked")
			Supplier<IntStream>[] sArr = (Supplier<IntStream>[])new Supplier[] {
				() -> IntStream.iterate(2, i -> 2 * i),
				() -> IntStream.iterate(10, i -> i - 1),
				() -> IntStream.iterate(10, i -> i),
				() -> IntStream.iterate(10, i -> 2),
				() -> IntStream.iterate(42, IntUnaryOperator.identity()),
				() -> IntStream.iterate(42, IntUnaryOperator.identity().andThen(IntUnaryOperator.identity())),
			};
			Stream.of(sArr).forEach(supplier -> supplier.get().limit(10).forEach(System.out::println));
		});
		
		
		//Various IntStream generations. generate produces elements without input => it uses an IntSupplier.
		
		System.out.println("---------------------------------------------");
		
		m("Generate", () -> {		
			@SuppressWarnings("unchecked")
			Supplier<IntStream>[] sArr = (Supplier<IntStream>[])new Supplier[] {
				() -> IntStream.generate(() -> new Random().nextInt()),
				() -> IntStream.generate(() -> new Random().nextInt()).filter(n -> n >= 0),
				() -> IntStream.generate(() -> 10),
				() -> IntStream.generate(() -> new Random().nextInt() % 20).filter(n -> n >= 0)
			};
			Stream.of(sArr).forEach(supplier -> {supplier.get().limit(10).forEach(System.out::println); System.out.println("--------------------------------");});
		});
		
		
		//Iterate and generate for DoubleStream, LongStream and Stream<T>
		
		
		m("DoubleStream iterate and generate", () -> {		
			DoubleStream.iterate(1, d -> d + d / 2).limit(20).forEach(System.out::println); //Uses DoubleUnaryOperator
			System.out.println("-------------------------");
			DoubleStream.generate(() -> new Random().nextDouble()).limit(5).forEach(System.out::println); //Uses DoubleSupplier
		});
		
		m("LongStream iterate and generate", () -> {		
			LongStream.iterate(2, n -> n * n).limit(4).forEach(System.out::println); //Uses LongUnaryOperator
			System.out.println("-------------------------");
			DoubleStream.generate(() -> new Random().nextLong()).limit(5).forEach(System.out::println); //Uses LongSupplier
		});
		
		m("Stream iterate and generate", () -> {		
			Stream.<List<Object>>iterate(new ArrayList<Object>(), l -> {l.add(1); return l;}).limit(4).forEach(System.out::println); //Uses UnaryOperator
			System.out.println("-------------------------");
			Stream.<List<Object>>generate(() -> new ArrayList<Object>()).limit(4).forEach(System.out::println); //Uses Supplier
		});
		
		m("noneMatch", () -> {		
			Stream<String> stream = Stream.<String>iterate("-", s -> s + s);
			Predicate<String> predicate = s -> s.length() > 3;
			System.out.println(stream.noneMatch(predicate)); //Note: this is not infinite. None match will fail as soon as one element is found
		});
		
		m("allMatch", () -> {		
			Stream<String> stream = Stream.<String>iterate("-", s -> s + s);
			Predicate<String> predicate = s -> s.length() > 0;
			Predicate<String> predicate2 = s -> s.length() > 3;
			//System.out.println(stream.allMatch(predicate)); //Note: This is infinite and will fail with OOM error
			System.out.println(stream.allMatch(predicate2)); //Note how this will return as the first element does not match so false can be returned
		});
		
	}

	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}

}