package telran.time;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateTimeTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void localDateTest() {
		LocalDate birthDateAS = LocalDate.parse("1799-06-06");
		LocalDate barMizvaAS = birthDateAS.plusYears(13);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,dd", Locale.CANADA);
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM,YYYY,dd", 
//				Locale.forLanguageTag("he"));
		System.out.println(barMizvaAS.format(dtf));
		ChronoUnit unit = ChronoUnit.MONTHS;
		System.out.printf("Number of %s between %s and %s is %d", unit, 
				birthDateAS, barMizvaAS, unit.between(birthDateAS, barMizvaAS));

	}

	@Test
	void barMizvaTest() {
		LocalDate current = LocalDate.now();
		assertEquals(current.plusYears(13), current.with(new BarMizvaAdjuster()));
	}

	@Test
	void displayCurrentDateTimeCanadaTimeZones() {
		// displaying current local date and time for all Canada time zones
		// displaying should contains time zone name
		System.out.println();
//		for (String zone : ZoneId.getAvailableZoneIds()) {
//			if (zone.contains("Canada")) {
//				System.out.println(ZonedDateTime.now(ZoneId.of(zone)));			
//			}
//		}
		ZoneId.getAvailableZoneIds().stream()
									.filter(x -> x.contains("Canada"))
									.forEach(x -> System.out.println(ZonedDateTime.now(ZoneId.of(x))));
	}
	
	@Test
	void nextFriday13test() {
		LocalDate today = LocalDate.parse("2023-02-15");
		LocalDate next13 = LocalDate.parse("2023-10-13");
		assertEquals(next13, today.with(new NextFriday13()));
	}
	
	@Test
	void workingDaysTest() {
		LocalDate today = LocalDate.parse("2023-02-15");
		LocalDate exp = LocalDate.parse("2023-03-01");
		assertEquals(exp, today.with(new WorkingDays(10, new DayOfWeek[]{ DayOfWeek.FRIDAY, DayOfWeek.SATURDAY })));
	}

}