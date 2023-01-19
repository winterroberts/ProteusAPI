package net.winrob.proteus.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormatUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
	/**
	 * Converts a Unix epoch time to the HTTP GMT format,
	 * required for cache control.
	 * @param time A long, prospectively the system's current time.
	 * @return A String representing the Unix epoch time passed to this method
	 * in HTTP standard.
	 */
	public static String getLastModifiedAsHTTPString(long time) {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(new Date(time));
	}
	
	/**
	 * Converts a {@link Date} to the HTTP GMT format,
	 * required for cache control.
	 * @param time A {@link Date}, prospectively the system's current time.
	 * @return A String representing the {@link Date} passed to this method
	 * in HTTP standard.
	 */
	public static String getLastModifiedAsHTTPString(Date d) {
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		return sdf.format(d);
	}
	
}
