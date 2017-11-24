import java.util.stream.*;
import java.util.*;
import java.util.regex.*;
import java.util.function.*;


class Texts extends ListResourceBundle {
	public Object[][] getContents() {
	
		return new Object[][] 
		{
		
			{"a", "A from Texts.java"},
			{"b", "B from Texts.java"},
			{"c", "C from Texts.java"}
		
		};
	
	}
}

class Texts_de extends ListResourceBundle {
	public Object[][] getContents() {
	
		return new Object[][] 
		{
		
			{"a", "A from Texts_de.java"},
			{"b", "B from Texts_de.java"}
		
		};
	
	}
}

class Texts_de_DE extends ListResourceBundle {
	public Object[][] getContents() {
	
		return new Object[][] 
		{
		
			{"a", "A from Texts_de_DE.java"}
		
		};
	
	}
}


public class ResourceBundles {

	public static int counter = 0;

	
	public static void main(String[] args) {
	
		m("Resource bundles: Loading texts from different bundles", () -> {
			
			//Note how a,b and c are from different files because of the hierarchical structure.
			ResourceBundle b = ResourceBundle.getBundle("Texts", Locale.GERMANY);
			System.out.println(b.getString("a"));
			System.out.println(b.getString("b"));
			System.out.println(b.getString("c"));
			
			//Note how the de_DE property file was not considered because the local is language only
			b = ResourceBundle.getBundle("Texts", Locale.GERMAN);
			System.out.println(b.getString("a"));
			System.out.println(b.getString("b"));
			System.out.println(b.getString("c"));
		
		}); 
		
	}

	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}

}