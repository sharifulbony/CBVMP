/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author rahat
 */
public class TypeList {

    private static HashMap<Integer, String> docTypeNo = new HashMap();
    private static HashMap<Integer, String> purpose = new HashMap();

    
    private static HashMap<String, List<String>> ipMap = new HashMap();

    private static List<String> ipGP = new ArrayList();
    private static List<String> ipBL = new ArrayList();
    private static List<String> ipROBI = new ArrayList();
    private static List<String> ipAIRTEL = new ArrayList();
    private static List<String> ipTELETALK = new ArrayList();
    private static List<String> ipCITYCELL = new ArrayList();
    
    static {
        docTypeNo.put(1, "NID");
        docTypeNo.put(2, "PASSPORT");
        docTypeNo.put(3, "DRIVING LICENSE");
        docTypeNo.put(4, "BIRTH CERTIFICATE");
        docTypeNo.put(5, "SMART CARD NID");

        purpose.put(1, "NEW REGISTRATION");
        purpose.put(2, "RE-REGISTRATION");
        purpose.put(3, "DE-REGISTRATION");
        purpose.put(5, "SIM TRANSFER");
        
        /* gp ip list */
        
        ipGP.add("192.168.254.33");
        ipGP.add("192.168.254.34");
        ipGP.add("192.168.254.35");
        ipGP.add("192.168.254.36");
        ipGP.add("192.168.254.37");
        ipGP.add("192.168.254.38");
        ipGP.add("192.168.254.39");
        ipGP.add("192.168.254.40");
        ipGP.add("192.168.254.41");
        ipGP.add("192.168.254.42");
        ipGP.add("192.168.254.43");
        ipGP.add("192.168.254.44");
        ipGP.add("192.168.254.45");
        ipGP.add("192.168.254.46");
        
        
        ipGP.add("192.168.254.17");
        ipGP.add("192.168.254.18");
        ipGP.add("192.168.254.19");
        ipGP.add("192.168.254.20");
        ipGP.add("192.168.254.21");
        ipGP.add("192.168.254.22");
        ipGP.add("192.168.254.23");
        ipGP.add("192.168.254.24");
        ipGP.add("192.168.254.25");
        ipGP.add("192.168.254.26");
        ipGP.add("192.168.254.27");
        ipGP.add("192.168.254.28");
        ipGP.add("192.168.254.29");
        ipGP.add("192.168.254.30"); 
               
        
        /* bl ip list */
        
        ipBL.add("192.168.254.72");
        ipBL.add("192.168.254.73");
        ipBL.add("192.168.254.74");
        ipBL.add("192.168.254.75");
        ipBL.add("192.168.254.76");
        ipBL.add("192.168.254.77");
        ipBL.add("192.168.254.78");
        ipBL.add("192.168.254.79");
        
        /*robi ip list*/
        
        ipROBI.add("192.168.254.51");
        ipROBI.add("192.168.254.49");
        ipROBI.add("192.168.254.50");
        ipROBI.add("192.168.254.52");       
        
        /*airtel ip list*/
        
        ipAIRTEL.add("192.168.254.51");
        ipAIRTEL.add("192.168.254.49");
        ipAIRTEL.add("192.168.254.50");
        ipAIRTEL.add("192.168.254.52");
        
        /*teletalk ip list*/        
        
        ipTELETALK.add("192.168.121.41");
//        ipTELETALK.add("172.16.24.180"); // testing purpose
              
         /*citycell ip list*/    
        ipCITYCELL.add("172.16.24.164"); // testing purpose        
        ipCITYCELL.add("172.16.24.180");
        ipCITYCELL.add("172.16.24.134");
        
        
        ipMap.put("CMPO_GP", ipGP);
        ipMap.put("CMPO_BL", ipBL);
        ipMap.put("CMPO_ROBI", ipROBI);
        ipMap.put("CMPO_AIRTEL", ipAIRTEL);
        ipMap.put("CMPO_TELETALK", ipTELETALK);
        ipMap.put("CMPO_CITYCELL", ipCITYCELL);
        

//        for(Integer key : docTypeNo.keySet())
//        {
//            System.out.print(key+ " ");
//            System.out.println(docTypeNo.get(key));
//        }
    }

    public static boolean typeFound(Integer typeNo) {
        if (docTypeNo.containsKey(typeNo)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean purposeFound(Integer purposeNo) {
        if (purpose.containsKey(purposeNo)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkValidForeign(Integer docType, Integer flag) {

        if (flag == 0 || flag == 1) {
            if (flag == 1 && docType != 2) {
                return false;
            } else if (flag != 0 && docType != 2 && docTypeNo.containsKey(docType)) {
                return false;
            }
            return true;
        } 
        else 
            return false;
    }
    
    public static boolean isValidIp(String cmpo, String remoteIp){
        
        //String cmpo= "CMPO_CITYCELL";
        
         if (ipMap.containsKey(cmpo)) {
                //List<String> values = entry.getValue();

                if (ipMap.get(cmpo).contains(remoteIp)) {
                    System.out.println("true");
                    return true;
                   
                } else {
                    System.out.println("false");
                    return false;
                }

            }
         else {
             return false;
         }
             

        
        //System.out.println("ip list: " + ipMap.entrySet());

        
    }
        

    
}
