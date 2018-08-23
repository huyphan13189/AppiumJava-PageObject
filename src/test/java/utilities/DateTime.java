package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
	
	// ex: DateTime.getCurrentTime("MM/dd/yyyy")
	public static String getCurrentTime(String timeFormat){
		return new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
	}
	
	// ex: DateTime.getCustomTime("MM/dd/yyyy",10)
	// ex: DateTime.getCustomTime("MM/dd/yyyy",-10)
	public static String getCustomTime(String timeFormat, int datePast){
		DateFormat dateFormat = new SimpleDateFormat(timeFormat);
        Date myDate = new Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, datePast);
        return dateFormat.format(cal.getTime());
	}
}
