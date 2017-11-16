import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface Combiner<T> {
	T combine(T a, T b);
}

public class Reduce {

	
	public static <T> T reduce(List<T> l, Combiner<T> f, T d) {
		if (l.size() == 0) {
			return d;
		} else {
			return reduce(l.subList(1,  l.size()), f, f.combine(d, l.get(0)));
		}
	}
	
	
	
	public static void main(String[] args) {
		List<Integer> l = new ArrayList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		l.add(4);
		l.add(5);
		System.out.println(reduce(l, (a, b) -> a + b, 0));
		
		System.out.println(reduce(l, (a, b) -> a * b, 1));
	}
	
	
	
}
