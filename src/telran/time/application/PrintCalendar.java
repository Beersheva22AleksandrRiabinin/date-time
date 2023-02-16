package telran.time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class PrintCalendar {

	private static final String LANGUAGE_TAG = "en";
	private static final int YEAR_OFFSET = 10;
	private static final int WIDTH_FIELD = 4;
	private static Locale locale = Locale.forLanguageTag(LANGUAGE_TAG);

	public static void main(String[] args) {
		try {
			int[] monthYear = getMonthYear(args);
			printCalendar(monthYear[0], monthYear[1], monthYear[2]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void printCalendar(int month, int year, int startingDay) {
		printTitle(month, year);
		printWeekDays(startingDay);
		printDates(month, year, startingDay);
	}

	private static void printDates(int month, int year, int startingDay) {
		int weekDayNumber = getFirstDay(month, year, startingDay);
		int offset = getOffset(weekDayNumber);
		int nDays = YearMonth.of(year, month).lengthOfMonth();
		System.out.printf("%s", " ".repeat(offset));
		int countWeek = 1;
		for (int date = 1; date <= nDays; date++) {
			System.out.printf("%4d", date);
			if (++weekDayNumber > 7) {
				printingWeeks(month, year, date);
				System.out.println();
				weekDayNumber = 1;
				countWeek++;
			}
		}
		int weekOffset = (countWeek * 7 - nDays) * WIDTH_FIELD - offset;
		System.out.printf("%s", " ".repeat(weekOffset));
		printingWeeks(month, year, nDays);
	}

	private static void printingWeeks(int month, int year, int date) {
		System.out.printf("%4d", LocalDate.of(year, month, date).
									get(WeekFields.of(Locale.getDefault()).
									weekOfWeekBasedYear()));
	}

	private static int getOffset(int weekDayNumber) {

		return (weekDayNumber - 1) * WIDTH_FIELD;
	}

	private static int getFirstDay(int month, int year, int startingDay) {
		int firstDay = LocalDate.of(year, month, 1).getDayOfWeek().getValue();
		int delta = firstDay - startingDay;
		return delta < 0 ? delta + 7 + 1 : delta + 1;
	}

	private static void printWeekDays(int startingDay) {
		System.out.printf("  ");
		DayOfWeek[] week = DayOfWeek.values();
		for (int i = startingDay - 1; i < week.length; i ++) {
			System.out.printf("%s ", week[i].getDisplayName(TextStyle.SHORT, locale));
		}
		for (int i = 0; i < startingDay - 1; i++) {
			System.out.printf("%s ", week[i].getDisplayName(TextStyle.SHORT, locale));
		}
		System.out.print(" WN");
//		Arrays.stream(DayOfWeek.values())
//				.forEach(dw -> System.out.printf("%s ", dw.getDisplayName(TextStyle.SHORT, locale)));
		
		System.out.println();
	}

	private static void printTitle(int month, int year) {
		System.out.printf("%s%d, %s\n", " ".repeat(YEAR_OFFSET), year,
				Month.of(month).getDisplayName(TextStyle.FULL, locale));

	}

	private static int[] getMonthYear(String[] args) throws Exception {

		return args.length == 0 ? getCurrentMonthYear() : getMonthYearArgs(args);
	}

	private static int[] getMonthYearArgs(String[] args) throws Exception {

		return new int[] { getMonthArgs(args), getYearArgs(args), getFirstCalendarDayArgs(args) };
	}

	private static int getYearArgs(String[] args) throws Exception {
		int res = LocalDate.now().getYear();
		if (args.length > 1) {
			try {
				res = Integer.parseInt(args[1]);
				if (res < 0) {
					throw new Exception("year must be positive number");
				}
			} catch (Exception e) {
				throw new Exception("year must be a number");
			}
		}
		return res;
	}

	private static int getMonthArgs(String[] args) throws Exception {
		try {
			int res = Integer.parseInt(args[0]);
			if (res < 1 || res > 12) {
				throw new Exception("month should be a number in the range [1-12]");
			}
			return res;
		} catch (Exception e) {
			throw new Exception("month should be a number");
		}
	}
	
	private static int getFirstCalendarDayArgs (String [] args) throws Exception { 	
		try {
			if (args.length < 3) {
				return 1;
			}
			String firstDay = args[2].toUpperCase();
			int res = DayOfWeek.valueOf(firstDay).getValue();
			return res;
		} catch (Exception e) {
			throw new Exception("nobody knows a day like this");
		}	
	}

	private static int[] getCurrentMonthYear() {
		LocalDate current = LocalDate.now();
		return new int[] { current.getMonth().getValue(), current.getYear() };
	}

}
