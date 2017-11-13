package afanasjew.experiments;

public class Constructors {

	static class A {
		A() {
			System.out.println("A()");
		}
		
		A(int i) {
			System.out.println("A(int)");
		}
		
		A(boolean b) {
			System.out.println("A(boolean)");
		}
		
		A(String s) {
			System.out.println("A(String)");
		}
	}
	
	static class B extends A {
		B() {
			System.out.println("B()");
		}
		
		B(int i) {
			System.out.println("B(int)");
		}
		
		B(double d) {
			this((int)d);
			System.out.println("B(double)");
		}
		
		B(boolean b) {
			super(b);
			System.out.println("B(boolean)");
		}
		
	}
	
	static class C extends B {
		C(String s) {
			System.out.println("C(String)");
		}
	}
	
	//This would not compile, because C does not have a default constructor, even if B has it
	/*
	static class D extends C {
		
	}
	
	static class D extends C {
		
		D(String s) {
		
		}
		
		D() {
		
		}
	}
	
	static class D extends C {
		D() {
			super("");
			System.out.println("D");
		}
		
		D(int i) {
			
		}
	}
	
	 */
	
	static class D extends C {
		D() {
			super("");
			System.out.println("D(String)");
		}
		
		D(int i) {
			this();
			System.out.println("D(int)");
		}
	}
	
	public static void main(String[] args) {
		System.out.println("---------");
		new B();
		System.out.println("---------");
		new B(1);
		System.out.println("---------");
		new B(1.0);
		System.out.println("---------");
		//new B(""); //This does not compile, constructors are not inherited.
		System.out.println("---------");
		new B(true);
		System.out.println("---------");
		new C("");
		System.out.println("---------");
		new D(1);
	}
	
}
