package utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.codecube.keshav.motif.HomeActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethod {


    public static final String DISPLAY_MESSAGE_ACTION =
            "com.codecube.broking.gcm";

    public static final String EXTRA_MESSAGE = "message";


    public static String SimpleDateTime(String findate) {
        Log.d("SimpleDateTime", "1: " + findate);
        DateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat toFormat = new SimpleDateFormat("dd-MMM-yyyy");
        DateFormat toFormatTime = new SimpleDateFormat("HH:mm");
        toFormat.setLenient(false);

        String dateStr = findate;
        String timeStr = findate;

        Date date = null;
        Date dateTime = null;
        try {
            date = fromFormat.parse(dateStr);
            dateTime = fromFormat.parse(timeStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateNew = toFormat.format(date) + " at " + toFormatTime.format(dateTime);
        //String timeNew=	toFormatTime.format(dateTime);


        return dateNew;
    }

    public static String rupeesFormat(String value)
    {
        value = value.replace(",", "");
        char lastDigit = value.charAt(value.length() - 1);
        String result = "";
        int len = value.length() - 1;
        int nDigits = 0;
        for (int i = len - 1; i >= 0; i--) {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0)) {
                result = "," + result;
            }
        }
        return (result + lastDigit);
    }


    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    /**
     * Called for Showing Alert in Application
     */



    public static void myCustomAddPopup(final Activity activity) {
        new android.support.v7.app.AlertDialog.Builder(activity)
                .setTitle("Are you sure you want to exit?")
                .setMessage("")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                activity.finish();

                            }
                        }).setNegativeButton("No", null).show();

    }

//    public static void myCustomAddPopupFragment(final Activity activity) {
//        new android.support.v7.app.AlertDialog.Builder(activity)
//                .setTitle("Are you sure you want to exit?")
//                .setMessage("")
//                .setCancelable(false)
//                .setPositiveButton("Yes",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which) {
//                                Intent i =new Intent(activity,HomeActivity.class);
//                                activity.startActivity(i);
//                            }
//                        }).setNegativeButton("No", null).show();
//    }


    public static void showAlert(String message, Activity context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message).setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * Called for checking Internet connection
     */
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo;
        try {
            netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Called for Checking Email Validation
     */

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public  static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
//    public static boolean isNetworkAvailable(Context ctx) {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }

    public static boolean isInternetAvailable(Context ctx) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


    public static String DateFormatApp(String findate) {

        DateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy");

        DateFormat toFormat = new SimpleDateFormat("dd MMM yyyy");
        toFormat.setLenient(false);
        String dateStr = findate;
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateNew=	toFormat.format(date);

        return dateNew;
    }
    public static String DateFormatServer(String findate) {

        DateFormat fromFormat = new SimpleDateFormat("dd-MMM-yyyy");

        DateFormat toFormat = new SimpleDateFormat("yyyy-MM-dd");
        toFormat.setLenient(false);
        String dateStr = findate;
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateNew=	toFormat.format(date);

        return dateNew;
    }

    public static String SimpleDateFormat(String findate) {

        DateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy");

        DateFormat toFormat = new SimpleDateFormat("dd MMM yyyy");
        toFormat.setLenient(false);
        String dateStr = findate;
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateNew=	toFormat.format(date);

        return dateNew;
    }


}
