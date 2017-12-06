import java.time.*;
import java.util.stream.*;
import java.time.temporal.*;
import static java.time.format.DateTimeFormatter.*;

public class DatesAndTimes {

	public static int counter = 0;
	
	public static void main(String[] args) throws Exception {
	
		m("LocalDate", () -> {
			
			LocalDateTime t1;
			LocalDate d1;
			
			d1 = LocalDate.of(1981,Month.OCTOBER, 14);
			System.out.println(d1);
			
			d1 = LocalDate.of(1981,10, 14);
			System.out.println(d1);
			
			d1 = LocalDate.now();
			System.out.println(d1);
			
			d1 = LocalDate.now().withYear(1981).withMonth(10).withDayOfMonth(14);
			System.out.println(d1);
			
			d1 = LocalDate.now().withDayOfYear(100);
			System.out.println(d1);
			
			d1 = LocalDate.of(1970, Month.JANUARY, 1);
			System.out.println(d1.toEpochDay());
			d1 = LocalDate.of(1970, Month.JANUARY, 2);
			System.out.println(d1.toEpochDay());
			d1 = LocalDate.of(1970, Month.JANUARY, 3);
			System.out.println(d1.toEpochDay());
			
			
			d1 = LocalDate.of(1981,Month.OCTOBER, 14);
			d1.plusDays(5); //No effect
			System.out.println(d1);
			d1 = d1.plusDays(5);
			System.out.println(d1);
						
			d1 = d1.of(1970, 02, 01);
			
			System.out.println(d1.plusMonths(1));
			System.out.println(d1.plusYears(1));
			
			System.out.println(d1.plus(1, ChronoUnit.DAYS));
			try {
				System.out.println(d1.plus(1, ChronoUnit.HOURS)); // <- Throws UnsupportedTemporalTypeException
			} catch (UnsupportedTemporalTypeException e) {
				System.out.println(e.getMessage());
			}
			
			System.out.println(d1.lengthOfYear());
			System.out.println(d1.lengthOfMonth());
			System.out.println(d1.isLeapYear());
			System.out.println(d1.plusYears(2).isLeapYear());
			System.out.println(d1.getYear());
			System.out.println(d1.getMonth());
			System.out.println(d1.getMonthValue());
			System.out.println(d1.getEra());
			System.out.println(d1.getDayOfWeek());
			
			t1 = d1.atStartOfDay();
			System.out.println(t1);
			
			System.out.println(d1.atTime(12, 30));
			System.out.println(d1.atTime(12, 30, 30, 500));
			
			System.out.println(LocalDate.of(2014, 10, 14).equals(LocalDate.of(2014, 10, 14)));
			System.out.println(LocalDate.of(2014, 10, 14).equals(LocalDate.of(2014, 10, 15)));
			
			System.out.println(d1.get(ChronoField.DAY_OF_MONTH)); //ChronoField is not ChronoUnit. It represents fraction of time unit in another unit, like day of month or month of year.
			
			//Formatting
			
			Stream.of(
			
				BASIC_ISO_DATE, //OK
				ISO_LOCAL_DATE, //OK
				ISO_OFFSET_DATE, //ERROR
				ISO_DATE, //OK
				ISO_LOCAL_TIME, //ERROR
				ISO_OFFSET_TIME, //ERROR
				ISO_TIME, //ERROR
				ISO_LOCAL_DATE_TIME, //ERROR
				ISO_OFFSET_DATE_TIME, //ERROR
				ISO_ZONED_DATE_TIME, //ERROR
				ISO_DATE_TIME, //ERROR
				ISO_ORDINAL_DATE, //OK
				ISO_WEEK_DATE, //OK
				ISO_INSTANT,  //ERROR
				RFC_1123_DATE_TIME //ERROR
			
			).forEach((o) -> {
				try {
					LocalDate d = LocalDate.of(1981, 10, 14);
					System.out.print("\t\t");
					System.out.println(d.format(o));
				} catch (Exception e) {
					System.out.println("ERROR ");
				}
			});
			
			
			
			
			System.out.println();
			
		});
		
		
		m("LocalTime", () -> {
			
			Stream.of(
			
				BASIC_ISO_DATE, //ERROR
				ISO_LOCAL_DATE, //ERROR
				ISO_OFFSET_DATE, //ERROR
				ISO_DATE, //ERROR
				ISO_LOCAL_TIME, //OK
				ISO_OFFSET_TIME, //ERROR
				ISO_TIME, //OK
				ISO_LOCAL_DATE_TIME, //ERROR
				ISO_OFFSET_DATE_TIME, //ERROR
				ISO_ZONED_DATE_TIME, //ERROR
				ISO_DATE_TIME, //ERROR
				ISO_ORDINAL_DATE, //ERROR
				ISO_WEEK_DATE, //ERROR
				ISO_INSTANT,  //ERROR
				RFC_1123_DATE_TIME //ERROR

			).forEach((o) -> {
				try {
					LocalTime t = LocalTime.of(22, 10, 30);
					System.out.print("\t\t");
					System.out.println(t.format(o));
				} catch (Exception e) {
					System.out.println("ERROR ");
				}
			});
			
			System.out.println();
			
		});
		
		
		LocalTime t1;
		
		t1 = LocalTime.now();
		LocalDateTime ldt1 = t1.atDate(LocalDate.now());
		System.out.println(ldt1);
		
		
		System.out.println(LocalTime.now().compareTo(LocalTime.now().plus(20, ChronoUnit.SECONDS)));
		
		System.out.println(LocalTime.now().equals(LocalTime.now()));
		
		
		System.out.println(LocalTime.now().getHour());
		System.out.println(LocalTime.now().getMinute());
		System.out.println(LocalTime.now().getSecond());
		System.out.println(LocalTime.now().getNano());

		System.out.println(LocalTime.now().isBefore(LocalTime.now().plus(1, ChronoUnit.SECONDS)));
		System.out.println(LocalTime.now().isAfter(LocalTime.now().plus(1, ChronoUnit.SECONDS)));
		
		System.out.println(LocalTime.now().plusHours(2));
		System.out.println(LocalTime.now().plusMinutes(10));
		System.out.println(LocalTime.now().plusSeconds(3600));
		
		
		System.out.println(LocalTime.of(12, 10, 30, 500));
		
		System.out.println(LocalTime.now().truncatedTo(ChronoUnit.HOURS));
		System.out.println(LocalTime.now().truncatedTo(ChronoUnit.DAYS)); // <-THAT COMPILES AND RUNS PROPERLY!!!
		try {
			System.out.println(LocalTime.now().truncatedTo(ChronoUnit.MONTHS)); // <- That compiles but throws an exception
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
				
		m("LocalDateTime", () -> {
			
			Stream.of(
			
				BASIC_ISO_DATE, //OK
				ISO_LOCAL_DATE, //OK
				ISO_OFFSET_DATE, //ERROR
				ISO_DATE, //OK
				ISO_LOCAL_TIME, //OK
				ISO_OFFSET_TIME, //ERROR
				ISO_TIME, //OK
				ISO_LOCAL_DATE_TIME, //OK
				ISO_OFFSET_DATE_TIME, //ERROR
				ISO_ZONED_DATE_TIME, //ERROR
				ISO_DATE_TIME, //OK
				ISO_ORDINAL_DATE, //OK
				ISO_WEEK_DATE, //OK
				ISO_INSTANT,  //ERROR
				RFC_1123_DATE_TIME //ERROR

			).forEach((o) -> {
				try {
					LocalDateTime dt = LocalDateTime.of(1981, 10, 14, 22, 10, 30, 500);
					System.out.print("\t\t");
					System.out.println(dt.format(o));
				} catch (Exception e) {
					System.out.println("ERROR ");
				}
			});
			
		
		LocalDateTime dt = LocalDateTime.now();
			
		System.out.println(dt.getDayOfWeek());
		System.out.println(dt.getDayOfYear());
		System.out.println(dt.getHour());
		System.out.println(dt.getMinute());
		System.out.println(dt.getMonth());
		System.out.println(dt.getMonthValue());
		System.out.println(dt.getNano());
		System.out.println(dt.getSecond());
		System.out.println(dt.getYear());

		System.out.println(dt.getLong(ChronoField.DAY_OF_YEAR));			
		System.out.println(dt.getLong(ChronoField.MINUTE_OF_HOUR));				
		
		
		System.out.println(LocalDateTime.of(2017, Month.DECEMBER, 06, 16, 58, 30, 500));
		
		System.out.println(LocalDateTime.now().withDayOfMonth(1).withMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0));
			
			
		});
		
		
		m("ZonedDateTime", () -> {
			Stream.of(
			
				//All are ok
				BASIC_ISO_DATE, 
				ISO_LOCAL_DATE,
				ISO_OFFSET_DATE,
				ISO_DATE,
				ISO_LOCAL_TIME,
				ISO_OFFSET_TIME,
				ISO_TIME,
				ISO_LOCAL_DATE_TIME,
				ISO_OFFSET_DATE_TIME,
				ISO_ZONED_DATE_TIME,
				ISO_DATE_TIME,
				ISO_ORDINAL_DATE,
				ISO_WEEK_DATE,
				ISO_INSTANT,
				RFC_1123_DATE_TIME

			).forEach((o) -> {
				try {
					ZonedDateTime zdt = ZonedDateTime.of(1981, 10, 14, 22, 10, 30, 500, ZoneId.systemDefault());
					System.out.print("\t\t");
					System.out.println(zdt.format(o));
				} catch (Exception e) {
					System.out.println("ERROR ");
				}
			});
		});
		
		System.out.println(ZonedDateTime.now().getOffset());
		
		ZonedDateTime zdt;
		zdt = ZonedDateTime.of(1981, 10, 14, 22, 10, 30, 500, ZoneId.of("Z"));
		System.out.println(zdt);
		zdt = ZonedDateTime.of(1981, 10, 14, 22, 10, 30, 500, ZoneId.of("+10:00"));
		System.out.println(zdt);
		zdt = ZonedDateTime.of(1981, 10, 14, 22, 10, 30, 500, ZoneId.of("GMT"));
		System.out.println(zdt);
		zdt = ZonedDateTime.of(1981, 10, 14, 22, 10, 30, 500, ZoneId.of("UTC"));
		System.out.println(zdt);
		
		
	}
	
	public static void m(String descr, Runnable r) throws Exception {
		System.out.print(++counter + " - ");
		System.out.println(descr);
		r.run();
		System.out.println();
	}


}
