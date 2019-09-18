/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.error;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author tanbir
 */
public class ApplicationError {

    // Application ERROR codes
    public static final String EXP010201 = "EXP010201";  // for requered parameter validation
    public static final String EXP010202 = "EXP010202";
    public static final String EXP010203 = "EXP010203";
    public static final String EXP010204 = "EXP010204";
    public static final String EXP010205 = "EXP010205";
    public static final String EXP010206 = "EXP010206";
    public static final String EXP010207 = "EXP010207";
    public static final String EXP010208 = "EXP010208";
    public static final String EXP010209 = "EXP010209";
    public static final String EXP010210 = "EXP010210";
    public static final String EXP010211 = "EXP010211";
    public static final String EXP010212 = "EXP010212";
    public static final String EXP010213 = "EXP010213";
    public static final String EXP010214 = "EXP010214";
    public static final String EXP010215 = "EXP010215";
    public static final String EXP010216 = "EXP010216";
    public static final String EXP010217 = "EXP010217";
    public static final String EXP010218 = "EXP010218";
    public static final String EXP010219 = "EXP010219";
    public static final String EXP010220 = "EXP010220";
    public static final String EXP010221 = "EXP010221";
    public static final String EXP010222 = "EXP010222";
    
    public static final String EXP010223 = "EXP010223";
    
    public static final Integer RQEXP404 = 404;

    // ERROR code descriptions
    public static final String EXP010202_DESC = "Access Denied for this functionality !!!";
    public static final String EXP010203_DESC = "Access token expired!!!";
    public static final String EXP010204_DESC = "Request token malformed";
    public static final String EXP010205_DESC = "Password change is required.";
    public static final String EXP010206_DESC = "Username or Password mismatch";
    public static final String EXP010207_DESC = "Date format not matched";
    public static final String EXP010208_DESC = "MSISDN format not matched";
    public static final String EXP010209_DESC = "NID or SMART CARD NID format not matched";    
    public static final String EXP010210_DESC = "Bulk request too big";
    public static final String EXP010211_DESC = "Invalid purpose no";
    public static final String EXP010212_DESC = "Invalid corporate field";
    public static final String EXP010213_DESC = "Invalid foriegn flag";
    public static final String EXP010214_DESC = "Same Document ID cannot be used";
    public static final String EXP010215_DESC = "required field 'ec_sess_id' or 'ec_sess_time' or 'ec_trn_id' is null";
    public static final String EXP010216_DESC = "required field 'exp_time' is null";
    public static final String EXP010217_DESC = "Invalid document type no";
    public static final String EXP010218_DESC = "Database Transaction error";
    public static final String EXP010219_DESC = "Invalid 'is_verified' entry";
    public static final String EXP010220_DESC = "You can not use this password!!!";
    public static final String EXP010221_DESC = "Password should be at least 8 characters long!!!";
    public static final String EXP010222_DESC = "Smart Card NID format not matched";
    
    public static final String EXP010223_DESC = "Invalid document type for Expire Time";
    public static final String RQEXP404_DESC = "Request method not allowed";

    public static String validationError(String parameter) {
        return parameter.toLowerCase() + " is required ";
    }

    public static <T> List validateRequired(T target, Class<T> targetClass) throws IllegalArgumentException, IllegalAccessException {
        ArrayList<String> errorDesc = new ArrayList<String>();
        StringBuilder errors = new StringBuilder();
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            XmlElement annotation = field.getAnnotation(XmlElement.class);
            if (annotation != null && annotation.required()) {
                field.setAccessible(true);
                if (field.get(target) == null) {
//                    if (errors.length() != 0) {
//                        errors.append(System.getProperty("line.separator"));
//                    }
//                    String message = String.format("%s: required field '%s' is null.",
//                            targetClass.getSimpleName(),
//                            annotation.name());
                    String message = String.format("required field '%s' is null.",annotation.name());
                    errors.append(message);
                    break;
                }

                //if (field.get(target).getClass() == String.class && field.get(target).toString().length() == 0) {
                if (field.get(target) == "" ) {    
                String message = String.format("required field '%s' is empty.",annotation.name());
                    errors.append(message);
                    break;
                }
                /*if (field.get(target).getClass() == Integer.class && Integer.parseInt(field.get(target).toString()) == 0) {
                    String message = String.format("required field '%s' is ZERO.",annotation.name());
                    errors.append(message);
                    break;
                    
                }*/
            }
        }
        if (errors.length() != 0) {
            errorDesc.add(ApplicationError.EXP010201);
            errorDesc.add(errors.toString());
        }
        return errorDesc;

    }
    
    

}
