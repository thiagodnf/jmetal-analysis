package thiagodnf.jmetal.analysis.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static String getDateNow() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS");

		Date date = new Date();

		return dateFormat.format(date);
	}
}
