import java.util.stream.*;
import java.util.*;
import java.util.regex.*;
import java.util.function.*;


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
		
			//Note how it backs to de_DE because it is the default locale and there is no japanese locale.
			b = ResourceBundle.getBundle("Texts", Locale.JAPAN);	
			System.out.println(b.getString("a"));
			
			//Note how now it does not back to de_DE or de because we set the default locale to japanese.
			//There is no japanese property file so it goes directly to the default property file
			Locale.setDefault(Locale.JAPAN);
			b = ResourceBundle.getBundle("Texts", Locale.JAPAN);
			System.out.println(b.getString("a"));
			
		}); 
		
	}

	
	public static void m(String descr, Runnable r) {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}

}