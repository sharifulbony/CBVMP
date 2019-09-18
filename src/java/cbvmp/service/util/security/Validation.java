/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author rahat
 */
public class Validation {

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isValidDob(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean isValidNID(String nid) {
        int length = nid.length();
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(nid);

        if ((length == 17) && m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidSmartNID(String nid) {
        int length = nid.length();
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(nid);

        if ((length == 10) && m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidMSISDN(String msisdn) {
        int length = msisdn.length();
        //Pattern p = Pattern.compile("^((880|0)\\d{10})?$");
        Pattern p = Pattern.compile("^((880)\\d{10})?$");
        Matcher m = p.matcher(msisdn);

        //if ((length == 11 || length == 13) && m.find()) {
        if (length == 13 && m.find()) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isNullFound(String entry) {

        if (entry == null || entry.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean isEcVerified(Integer flag) {

        if (flag == 0 || flag == 1) {
            return true;
        } else {
            return false;
        }

    }

    public static Date formatDate(String d, Integer type) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        //date of birth
        if (d == null || d == "") {
            return date;
        }
        else {
            try {
                //date of birth

                if (type == 1) {
                    date = format.parse(d);

                } else {
                    date = formatter.parse(d);

                }

                //System.out.println(date);
                //System.out.println(formatter.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

    }

}
