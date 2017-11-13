package afanasjew.experiments;

public class SwitchCases {

	public static void main(String[] args) {

		byte foo = 120;

		@SuppressWarnings("unused")
		final byte foo2;
		
		switch (foo) {
		default:
			System.out.println("default");
			break;
		case 2:
			System.out.println("2");
			break;
		case 120:
			System.out.println("120");
		case 121:
			System.out.println("121");
		case 127:
			System.out.println("127");
			break;
		}

		switch(2) {
		
			case 1:
				System.out.println("1");
				
			default:
				System.out.println("default");
				
			case 2:
				System.out.println("2");
	
		}
		
	}
	
	
	
	

}
