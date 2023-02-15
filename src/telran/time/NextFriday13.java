package telran.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster {
	final int HAPPY_DAY = 13;
	final DayOfWeek DAY_OF_WEEK = DayOfWeek.FRIDAY;

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		while (!DayOfWeek.from(res).equals(DAY_OF_WEEK)) {
			res = res.plus(1, ChronoUnit.DAYS);
		}
		while (LocalDate.from(res).getDayOfMonth() != HAPPY_DAY) {
			res = res.plus(1, ChronoUnit.WEEKS);
		}
		return res;
	}

}
