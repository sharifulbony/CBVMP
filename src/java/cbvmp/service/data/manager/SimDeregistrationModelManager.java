/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.SimDeregistrationModel;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.CustomeEncryption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author SIT
 */
public class SimDeregistrationModelManager extends ModelManager {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public SimDeregistrationModel deRegisterSim(
            String schema, 
            Integer purposeNo, 
            Integer docTypeNo,
            String documentId,
            String retailId, 
            String msisdn, 
            String cmpoTrnId, 
            Date deRegDate, 
            Integer isVerified, 
            String ecSessId, 
            Integer cmpoNO,
            Integer otpNo,
            Date ecSessTime,
            String ecTrn
            )
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        //String dateInString = "07/06/2013";
//        Date dedate = null,expirationDate=null,ecSessionTime=null;
//        try {
//
//            dedate = formatter.parse(deRegDate);
//            ecSessionTime = formatter.parse(ecSessTime);
//            //expirationDate=formatter.parse(expTime);
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        Transaction tx = null;
        SimDeregistrationModel list = new SimDeregistrationModel();
        Session session = HibernateUtil.getSession(schema);
        try {

            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_save_deregistration");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", documentId);
            qu.setParameter("p4", retailId);
            qu.setParameter("p5", msisdn);
            qu.setParameter("p6", cmpoTrnId);
            qu.setParameter("p7", deRegDate);
            qu.setParameter("p8", isVerified);
            qu.setParameter("p9", ecSessId);
            qu.setParameter("p10", cmpoNO);
            qu.setParameter("p11", otpNo);
             if (ecSessTime == null) {
                qu.setParameter("p12", "");
            } else {
                qu.setParameter("p12", ecSessTime);
            }
             qu.setParameter("p13", ecTrn);
          
            
            

            list = (SimDeregistrationModel) qu.list().get(0);
            //System.out.println("loggedin");

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error(e.getMessage());
            e.printStackTrace();
        } 
//        finally {
//            session.close();
//        }
        return list;
    }

}
