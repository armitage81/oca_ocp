import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambdas {

	//Take a list of persons and print their names in upper case for all
	//who start with A

	static class Person {
		public String name;
		
		public static Person of(String name) {
			Person p = new Person();
			p.name = name;
			return p;
		}
	}
	
	
	public static void main(String[] args) {
		

		
		List<Person> l = new ArrayList<>();
		l.add(Person.of("Anton"));
		l.add(Person.of("Alexander"));
		l.add(Person.of("Bernd"));
		l.add(Person.of("Johann"));
		l.add(Person.of("Angela"));
		
		l.forEach((Person element) -> execute(
				() -> element,
				(Person p) -> p.name.startsWith("A"),
				(Person p) -> p.name,
				s -> System.out.println(s)		
		));
		
	}
	
	
	public static void execute(
			Supplier<Person> supplier,
			Predicate<Person> filter,
			Function<Person, String> transformer,
			Consumer<String> consumer) {
	
		Person p = supplier.get();
		if (filter.test(p)) {
			String s = transformer.apply(p);
			consumer.accept(s);
		}
		
	}
	
}
