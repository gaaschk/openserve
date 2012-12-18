package org.gsoft.openserv.util.time;

import java.util.Date;
import java.util.List;

public interface ChronologicalFrequency {
	Date findNextDateAfter(Date theDate);
	List<Date> findAllDatesBetween(Date fromDate, Date toDate);
}
