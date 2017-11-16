import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface Transformer<T, R> {
	R transform(T t);
}

public class Map {

	public static <T, R> List<R> map(List<? extends T> l1, Transformer<T, R> t) {
		List<R> l2 = new ArrayList<>();
		for (T e : l1) {
			l2.add(t.transform(e));
		}
		return l2;
	}
	
	public static void main(String[] args) {
		
		List<Character> l = new ArrayList<>();
		l.add('A');
		l.add('B');
		l.add('C');
		
		System.out.println(Map.map(l, (Character c) -> {int i = c; return i;} ));
		
		System.out.println(Map.map(l, (Character c) -> {String s = c + "" + c; return s;} ));
	}
	
}
