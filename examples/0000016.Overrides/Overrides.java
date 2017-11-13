package afanasjew.experiments;

public class Overrides {

	public static void a(Number n) {
		
	};
	
	public void b(Number n) {
		System.out.println("Overrides::b");
	}
	
	public Number c() {
		System.out.println("Overrides::c");
		return null;
	}
	
	public Number d() {
		System.out.println("Overrides::d");
		return null;
	}
	
	public static Number e() {
		System.out.println("Overrides::e");
		return null;
	}
	
	public Number f(Number n) {
		return null;
	}
	
	public static void main(String[] args) {
		Overrides obj = new B();
		obj.b(3);
		obj.b((Number)3);
		obj.c();
		
		Overrides obj2 = new Overrides();
		obj2.c();
	}
	
}

class B extends Overrides {
	
	
	//This will not compile because the super class method is static. 
	//It is neither overloading nor overriding, but a compiler error.
	/*
	public void a(Number n) {
		
	}
	*/
	
	public void a(Integer i) { //Covariant params works even if it is static.
		
	}
	
	public void a(Object o) { //Super class params work even if the other is static.
		
	}
	
	public void b(Integer i) { //Covariant params don't count for override. It is no overriding then, but overloading
		System.out.println("B::b");
	}
	
	public Integer c() { //This is a valid override because the return value is covariant to the super method.
		System.out.println("B::c");
		return null;
	}
	
	
	//This will not compile because the return type is not covariant with the super method.
	/*
	public Object d() { 
		System.out.println("B::c");
		return null;
	}
	*/
	
	//Even this will not work because the return type is not covariant. And it's not even an override.
	/*
	public static Object e() {
		
	}
	*/
	
	public Integer f(Integer n) { //This works even if the return type is not covariant, because Integer != Number as param type.
		return null;
	}

}
