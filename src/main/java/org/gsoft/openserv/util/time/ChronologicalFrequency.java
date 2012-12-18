package org.gsoft.openserv.util.time;

import java.util.Date;
import java.util.List;

public interface ChronologicalFrequency {
	public Date findNextDateAfter(Date theDate);
	public List<Date> findAllDatesBetween(Date fromDate, Date toDate);
}
