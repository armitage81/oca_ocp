import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Streams {
	
	static class Person {
		int age;
		String name;
		
		public Person(String name, int age) {
			this.age = age;
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		
		
	}
	
	public static void main(String[] args) {
		
		Supplier<Stream<Person>> streamSupplier = () -> Stream.<Person>of(new Person("Anton", 36), new Person("Julia", 30)).filter(p -> (p.age % 4) == 0);
				
		streamSupplier.get().forEach(System.out::println);
		streamSupplier.get().forEach(System.out::println);
		
		
		double result = Stream.of(10, 15, 20, 34, 1, 124).collect(Collectors.averagingInt(i -> i));
		System.out.println(result);
		
		
		
		
		
	}
	
}
