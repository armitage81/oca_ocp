import java.util.Date; //Date is in java.util package.
import java.util.Calendar; //Calendar is in java.util package.
import java.util.Locale; //Locale is in java.util package.
import java.text.DateFormat; //DateFormat is in java.text package.

public class DatesAndCalendars {

	public static void main(String[] args) {
		
		//Common methods
		
		Date d = new Date(); //Create with current date
		String s = d.toString(); //Print representable string.
		System.out.println(s);
		
		Calendar c = Calendar.getInstance(); //Easier than initializing the GregorianCalendar.
		System.out.println(c); //The output is not a representative date.
		
		//Setting a date via date object.
		c.setTime(new Date());
		System.out.println(c.getTime());
		
		//Setting a concrete date via calendar flags.
		c.set(Calendar.DAY_OF_MONTH, 14);
		c.set(Calendar.MONTH, 9); //Note, month is 0-based
		c.set(Calendar.YEAR, 2017);
		System.out.println(c.getTime());

		//Locale creation
		Locale l = new Locale("en"); //Initialize with language.
		System.out.println(l);
		l = new Locale("en", "US"); //Initialize with language and country.
		System.out.println(l);
		l = new Locale("testtest"); //This will compile and run through, but it won't make sense.
		System.out.println(l);
		
		c = Calendar.getInstance(l); //Now, calendar was created with the locale which is testtest. Probably, a timezone of the default local was taken as this one doesn't exist.
		System.out.println(c.getTime());
		
		l = new Locale("en", "US");
		c = Calendar.getInstance(l);
		System.out.println(c);
		System.out.println(c.getTime());
		
		
		
		
		//add and roll on the calendar.
		c.set(Calendar.DAY_OF_MONTH, 14);
		c.set(Calendar.MONTH, 9); 
		c.set(Calendar.YEAR, 2017);
		System.out.println("Original calendar: " + c.getTime());
		c.add(Calendar.DAY_OF_MONTH, 30); //Adding that many days, will increase month
		System.out.println("After add: " + c.getTime());
		c.add(Calendar.DAY_OF_MONTH, -30); //Now reverting and rolling this time. Month will not increase.
		c.roll(Calendar.DAY_OF_MONTH, 30);
		System.out.println("After roll: " + c.getTime());
		
		
		//Now come the different variations of the date format initialization.
		
		d = new Date();
		DateFormat dateFormat;
		
		dateFormat = DateFormat.getInstance(); //Returns a normal instance for the default locale
		System.out.println(dateFormat.format(d));
		
		dateFormat = DateFormat.getDateInstance(); //Returns a normal instance for the default locale and date only (no time)
		System.out.println(dateFormat.format(d));
		
		dateFormat = DateFormat.getDateInstance(DateFormat.LONG); //Now using formatting styles
		System.out.println(dateFormat.format(d));
		//dateFormat = DateFormat.getInstance(DateFormat.LONG); //Won't compile, only dateInstance works with styles
		dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
		System.out.println(dateFormat.format(d));
		dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		System.out.println(dateFormat.format(d));
		dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		System.out.println(dateFormat.format(d));
		
		//dateFormat = DateFormat.getDateInstance(new Locale("en", "US")); //Won't compile. You can use style or style AND locale, but not locale alone.
		
		dateFormat = DateFormat.getDateInstance(DateFormat.FULL, new Locale("en", "US")); //Style and locale.
		System.out.println(dateFormat.format(d));
		
		
		//dateFormat.format(d, new Locale("en", "US")); //This won't compile. Setting locale is possible only during the initialization of the dateformat.
		
		
		
		//Some additional important methods in locale.
		
		l = new Locale("de", "DE");
		Locale l2 = new Locale("en", "US");
		
		//Country and language will be printed for german locale IN DEFAULT LOCALE (that is "deutsch" for german, "german" for english, etc)
		System.out.println(l.getDisplayCountry());
		System.out.println(l.getDisplayLanguage());
		
		//German country and language in English
		System.out.println(l.getDisplayCountry(l2));
		System.out.println(l.getDisplayLanguage(l2));
		
		//English country and language in German
		System.out.println(l2.getDisplayCountry(l));
		System.out.println(l2.getDisplayLanguage(l));
		
		
		
	}

}