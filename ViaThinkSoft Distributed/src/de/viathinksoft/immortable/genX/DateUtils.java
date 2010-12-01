package de.viathinksoft.immortable.genX;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @see http://www.rgagnon.com/javadetails/java-0106.html (modified)
 */
public class DateUtils {
	public static String now(String format) {
		Calendar cal = Calendar.getInstance();
		Locale lok = Locale.ENGLISH;
		SimpleDateFormat sdf = new SimpleDateFormat(format, lok);
		return sdf.format(cal.getTime());
	}

	private DateUtils() {
	}
}
