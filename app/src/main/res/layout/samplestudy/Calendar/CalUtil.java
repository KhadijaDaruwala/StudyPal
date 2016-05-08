package layout.samplestudy.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static java.util.Calendar.DATE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class CalUtil {

    public static List<GridItem> getGridList(Date date, List<Integer> daysWithEvents) {
        List<GridItem> dates = new ArrayList<>();
        Calendar calendar = getCalendar(date);

        int today = -1;
        Calendar now = getCalendar(new Date());
        if (now.get(YEAR) == calendar.get(YEAR) && now.get(MONTH) == calendar.get(MONTH)) {
            today = now.get(DAY_OF_MONTH);
        }

        int monthBeginning = 1;

        int monthEnd = calendar.getActualMaximum(DATE);

        calendar.set(DAY_OF_MONTH, 1);
        int firstDay_DayOfWeek = calendar.get(DAY_OF_WEEK);

        for (int i = 1; i < firstDay_DayOfWeek; ++i) {
            dates.add(null);
        }

        for (int i = monthBeginning; i <= monthEnd; ++i) {
            calendar.set(DAY_OF_MONTH, i);
            dates.add(new GridItem(
                    i,
                    i == today,
                    daysWithEvents.contains(i)
            ));
        }

        int extras = dates.size() % 7;
        if (extras > 0) {
            for (int i = 0; i < 7 - extras; ++i) {
                dates.add(null);
            }
        }

        return dates;
    }

    public static int getYear(Date date) {
        return getCalendar(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return getCalendar(date).get(Calendar.MONTH);
    }

    public static int getDayOfMonth(Date date) {
        return getCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        return getCalendar(date).get(Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(Date date) {
        return getCalendar(date).get(Calendar.MINUTE);
    }


    public static Date stringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.ENGLISH);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date) {
        return dateToFormattedString(date, "yyyy/MM/dd");
    }

    public static String dateTimeToString(Date date) {
        return dateToFormattedString(date, "yyyy/MM/dd HH:mm");
    }

    public static String dateToDisplayString(Date date) {
        return dateToFormattedString(date, "MMMM d, yyyy");
    }

    public static String timeToString(Date date) {
        return dateToFormattedString(date, "HH:mm");
    }

    public static String dateToFormattedString(Date date, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.ENGLISH);
        return format.format(date);
    }

    public static Date getBeginningOfDay(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        return calendar.getTime();
    }

    public static Date getBeginningOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(DATE, 1);
        calendar.set(HOUR_OF_DAY, 0);
        calendar.set(MINUTE, 0);
        return calendar.getTime();
    }

    public static Date getEndOfMonth(Date date) {
        Calendar calendar = getCalendar(date);
        calendar.set(DATE, calendar.getActualMaximum(DATE));
        calendar.set(HOUR_OF_DAY, 23);
        calendar.set(MINUTE, 59);
        return calendar.getTime();
    }

    public static Date subtractMonth(Date date, int amount) {
        return changeMonthByAmount(date, -amount);
    }

    public static Date addMonth(Date date, int amount) {
        return changeMonthByAmount(date, amount);
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getDate(int year, int month, int day, int hour, int minute) {
        GregorianCalendar calendar = new GregorianCalendar(year, month - 1, day, hour, minute);
        return calendar.getTime();
    }

    public static Date subtractDay(Date date, int amount) {
        return changeDayByAmount(date, -amount);
    }

    public static Date addDay(Date date, int amount) {
        return changeDayByAmount(date, amount);
    }

    private static Date changeMonthByAmount(Date date, int amount) {
        Calendar cal = getCalendar(date);
        cal.set(DAY_OF_MONTH, 1);
        cal.add(MONTH, amount);
        return cal.getTime();
    }

    private static Date changeDayByAmount(Date date, int amount) {
        Calendar cal = getCalendar(date);
        cal.add(DAY_OF_MONTH, amount);
        return cal.getTime();
    }
}