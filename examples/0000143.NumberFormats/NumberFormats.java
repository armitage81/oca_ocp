import java.util.*; //For locales
import java.text.*; //For formats

public class NumberFormats {

	public static void main(String[] args) {
		
		Locale l;
		NumberFormat nf;
		double d = 12345.123456789;
		l = new Locale("en", "US");
		
		
		//Initialization of number format
		nf = NumberFormat.getInstance();
		System.out.println(nf.format(d));
		
		nf = NumberFormat.getInstance(l);
		System.out.println(nf.format(d));
		
		nf = NumberFormat.getNumberInstance(l);
		System.out.println(nf.format(d));
		
		nf = NumberFormat.getCurrencyInstance();
		System.out.println(nf.format(d));
		
		nf = NumberFormat.getCurrencyInstance(l); //Will print number with the currency symbol.
		System.out.println(nf.format(d));
		
		//Maximum fraction digits.
		nf = NumberFormat.getInstance(l);
		nf.setMaximumFractionDigits(2); //Rest will be not cut but rounded.
		System.out.println(nf.format(d));
		nf.setMaximumFractionDigits(5); //Demonstrates rounding up.
		System.out.println(nf.format(d));
		
		
	}

}