package hellogbye.com.hellogbyeandroid.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by nyawka on 7/18/16.
 */
public class HGBUtilityDate {
    private static String BASE_SERVER_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss";

    public static ArrayList<String> getYears(final int NUMBER_OF_FUTURE_YEARS){
        ArrayList<String> yearArray = new ArrayList<>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear; i <= thisYear + NUMBER_OF_FUTURE_YEARS; i++) {
            yearArray.add(String.valueOf(i));
        }
        return yearArray;
    }


    public static ArrayList<String> getMonths(){
        ArrayList<String> monthArray = new ArrayList<>();
        for(int i=1; i<=12;i++){
            monthArray.add(String.valueOf(i));
        }
        return monthArray;
    }


    public static long dayDifference(String startDay, String endDate){
        Date date1 = getDateFromServer(startDay);
        Date date2 = getDateFromServer(endDate);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;
        return delta;
    }

    public static long nightDifference(String startDay, String endDate){
        Date date1 = getDateFromServer(startDay);
        Date date2 = getDateFromServer(endDate);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = ((timeTwo - timeOne) / oneDay) - 1;
        return delta;
    }

    public static String parseDateToServertime(String time) {

        String inputPattern = "dd/MM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        String str = null;

        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = null;
            try {
                date = inputFormat.parse(time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateToyyyyMMddTHHmmss(String time) {
        String outputPattern = BASE_SERVER_TIME_ZONE;//"EEE,MM dd,yyyy";

        String inputPattern = "dd/MM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateToEEEMMMDyy(String time) {
        String outputPattern = "EEE, MMM d, yy";//"EEE,MM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateFromddMMyyyyToddmmYYYY(String time) {
        String outputPattern = "EEE, MMM dd, yyyy";
        String inputPattern = "MM/dd/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        Date date = null;
        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToddMMyyyyMyTrip(String time) {
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);

            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String addDayTo(String sourceDate){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = format.parse(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        myDate = cal.getTime();


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string
// representation of a date with the defined format.
        String reportDate = df.format(myDate);


        return reportDate;
        //DateUtil.addDays(myDate, 1);

    }


/*    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "EEE, MMM d, yy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/


    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }



/*    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        DateFormat df = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/



    public static String addDayToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + (24 * 60 * 60 * 1000));
            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }


   /* public static String addDayToDate(String dateToIncr) {
        String newDate="";
        DateFormat df = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + (24 * 60 * 60 * 1000));
            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/


    public static String parseDateToddMMyyyy(String time) {

        String outputPattern = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToMMddyyyyForPayment(String time) {

        String outputPattern = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }



    public static String parseDateToddMMyyyyForPayment(String time) {

        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateToHHmm(String time) {

        String outputPattern = "HH:mm a";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToDateHHmm(String time) {

        String outputPattern = "MM/dd/yyyy HH:mm:ss";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }




    public static String getDateDiffString(String strDate1, String strDate2)
    {

        Date date1 = getDateFromServer(strDate1);
        Date date2 = getDateFromServer(strDate2);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;

        if (delta > 0) {
            return delta + " Nights";
        }
        else {
            delta *= -1;
            return delta + " Nights";
        }
    }
    public static int getDateDiffInt(String strDate1, String strDate2)
    {

        Date date1 = getDateFromServer(strDate1);
        Date date2 = getDateFromServer(strDate2);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;

        if (delta > 0) {
            return (int)delta;
        }
        else {
            delta *= -1;
            return (int)delta;
        }
    }





    public static Date getDateFromServer(String time){
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date = null;
        try {
            date = inputFormat.parse(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static String formattDateToStringMonthDate(String dateInString) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate;
        try {
            startDate = df.parse(dateInString);
            Calendar mydate = new GregorianCalendar();
            mydate.setTime(startDate);
            String strDate= mydate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + ":" + mydate.get(Calendar.DATE);
            return strDate;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}
