package afanasjew.experiments;

public class PrivateMethods {

	private void a() {
		System.out.println("PrivateMethods::a");
	}
	
	public static void main(String[] args) {
		
		
		PrivateMethods o1 = new PrivateMethods();
		PrivateMethodsSubclass o2 = new PrivateMethodsSubclass();
		PrivateMethods o3 = new PrivateMethodsSubclass();
		
		
		
		//The output of the three depends on the reference type. The method version of the reference type is used.
		o1.a();
		o2.a();
		o3.a();
		
		new PrivateMethods().a();
		new PrivateMethodsSubclass().a();
		
		//Now the a() method is called in the sub class (via b()) and the subclass version of a() is used.
		o2.b();
	}
	
}

class PrivateMethodsSubclass extends PrivateMethods {
	
	protected void a() {
		System.out.println("PrivateMethodsSubclass::a");
	}
	
	void b() {
		a();
	}
	
}
