import java.util.*;

public class Maps {

	public static int counter;

	public static void main(String[] args) {
	
	m("Map::merge()", () -> {
	
		Map<String, Integer> m = new HashMap<>();
		m.put("Profits", 0);
		
		m.merge("Profits", 15, (a, b) -> a + b);
		m.merge("Losses", 5, (a, b) -> a + b);
		
		m.merge("Losses", 30, (a, b) -> a + b);
		
		System.out.println(m);
	
	});

	m("Map null values()", () -> {
	
		//Null values are allowed in hash map but not in hash table.
		
		Map<String, Integer> m = new HashMap<>();
		m.put("1", null);
		
		try {
			Map<String, Integer> m2 = new Hashtable<>();
			m2.put("1", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(m);
	
	});	
		
	}
	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}

}