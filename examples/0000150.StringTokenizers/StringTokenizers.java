import java.util.StringTokenizer; //This is the import for string tokenizers.

public class StringTokenizers {
	public static void main(String[] args) {
		
		
		//StringTokenizer st = new StringTokenizer(); //This won't compile. You need to pass a string in the constructor.
		
		StringTokenizer st = new StringTokenizer("World War Two took place in the time between 1939 and 1945");
		
		System.out.println(st.countTokens()); //Returns number of tokens (12)
		
		while (st.hasMoreTokens()) { //Uses Enumerator, not Iterator like Scanner.
			String s = st.nextToken();
			System.out.println(s);
		}
		
		System.out.println(st.countTokens()); //Now it returns 0, because the enumeration already happened.
		
		st = new StringTokenizer("Column1|||Column2|||Column3", "|||"); //Here we are setting the delimiter to the ||| string
		
		System.out.println(st.countTokens());
		
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			System.out.println(s);
		}
		
		System.out.println(st.countTokens());
		
		st = new StringTokenizer("Attention stop the enemy is attacking stop prepare defences full stop", "\\s+(full\\s+)?stop\\s+"); //This will compile but not work as expected. StringTokenizer does not support regexes
		
		System.out.println(st.countTokens());
		
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			System.out.println(s);
		}
		
		System.out.println(st.countTokens());
		
		
	}
}