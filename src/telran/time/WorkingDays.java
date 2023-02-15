package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class WorkingDays implements TemporalAdjuster {

	private int workingDays;
	private DayOfWeek[] dayOffs;

	public WorkingDays(int workingDays, DayOfWeek[] dayOffs) {
		this.workingDays = workingDays;
		this.dayOffs = dayOffs;
	}

	@Override
	public Temporal adjustInto(Temporal temporal) {
		Temporal res = temporal;
		while (workingDays > 0) {
			res = res.plus(1, ChronoUnit.DAYS);
			if (!dayOff(res)) {
				workingDays--;
			}
		}
		return res;
	}

	public boolean dayOff(Temporal currentDay) {
		boolean res = false;
		int i = 0;
		while (!res && i < dayOffs.length) {
			if (DayOfWeek.from(currentDay).equals(dayOffs[i++])) {
				res = true;
			}
		}
		return res;

	}

}