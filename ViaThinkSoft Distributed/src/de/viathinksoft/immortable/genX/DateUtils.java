package de.viathinksoft.immortable.genX;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @see http://www.rgagnon.com/javadetails/java-0106.html
 */
public class DateUtils {
	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}

	private DateUtils() {
	}
}
