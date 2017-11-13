package afanasjew.experiments;

public class StaticFinalInitializations {

	String instance1 = "1";
	String instance2;
	String instance3;
	String instance4;
	String instance5;
	String instance6;
	
	final String finalInstance1 = "1";
	//ofinal String finalInstance2;
	final String finalInstance3;
	final String finalInstance4;
	//final String finalInstance5;
	//final String finalInstance6;
	
	static String static1 = "1";
	static String static2;
	static String static3;
	static String static4;
	static String static5;
	static String static6;
	
	static final String finalStatic1 = "1";
	static final String finalStatic2;
	//static final String finalStatic3;
	//static final String finalStatic4;
	//static final String finalStatic5;
	//static final String finalStatic6;

	static {
		static2 = "2";
		finalStatic2 = "2";
	}
	
	{
		static3 = "3";
		//finalStatic3 = "3"; //static final variable can be inited only in declaration or static block
		instance3 = "3";
		finalInstance3 = "3";
	}
	
	public StaticFinalInitializations() {
		instance4 = "4";
		finalInstance4 = "4";
		static4 = "4";
		//finalStatic4 = "4"; //static final variable can be inited only in declaration or static block
	}

	static void staticMethod() {
		//instance5 = "5"; //Cannot init instance variable in the static method.
		//finalInstance5 = "5" //Cannot init instance variable in the static method.
		static5 = "5";
		//finalStatic5 = "5"; //static final variable can be inited only in declaration or static block
	}
	
	void instanceMethod() {
		instance6 = "6";
		//finalInstance6 = "6"; //Instance final variable can be inited only in declaration, init block or constructor
		static6 = "6";
		//finalStatic6 = "6"; //static final variable can be inited only in declaration or static block
	}
	
	
}
