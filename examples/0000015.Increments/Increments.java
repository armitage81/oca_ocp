package afanasjew.experiments;

public class Increments {

	public static void main(String[] args) {
		int i=0;
		//This will run forever because i is increased only after the assignment of itself to the var i
		for (int j = 0; j < 10; j++) {
			i = i++;
			System.out.println(i);
		}
	}
	
}
