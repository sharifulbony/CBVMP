/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.SimTransferModel;
import cbvmp.service.util.log.SingletoneLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
public class SimTransferModelManager extends ModelManager {

    /*
 
    
     */
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public SimTransferModel transferSim(
            String schema,
            Integer purposeNo,
            Integer srcDocTypeNo,
            String srcDocTypeID,
            Integer destDocTypeNo,
            String destDocTypeID,
            String retailId,
            String msisdn,
            Date destDob,//date type
            String cmpoTrnId,
            Date regDate, //date type
            Integer isVerified,
            String ecSessionID,
            Integer cmpoNo,
            String destIMSI,
            Integer otpNo,
            Integer destForeignFlag,
            Date destExpTime,
            Date ecSessTime,
            String ecTrn) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        Date dateOfBirth = null, registrationDate = null, destExpirationTime=null, ecSessionTime=null;
//
//        try {
//
//            dateOfBirth = format.parse(destDob);
//            registrationDate = formatter.parse(regDate);
//            destExpirationTime = formatter.parse(destExpTime);
//            ecSessionTime = formatter.parse(ecSessTime);
//            System.out.println( "3:    " + destExpTime);
//
//            //System.out.println(date);
//            //System.out.println(formatter.format(date));
//        } catch (ParseException e) {
//            logger.warn(e.getMessage());
//            e.printStackTrace();
//        }

        SimTransferModel simTransferModel = null;
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {

            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_transfer_sim");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", srcDocTypeNo);
            qu.setParameter("p3", srcDocTypeID);
            qu.setParameter("p4", destDocTypeNo);
            qu.setParameter("p5", destDocTypeID);
            qu.setParameter("p6", retailId);
            qu.setParameter("p7", msisdn);
            qu.setParameter("p8", destDob);
            qu.setParameter("p9", cmpoTrnId);
            qu.setParameter("p10", regDate);
            qu.setParameter("p11", isVerified);
            qu.setParameter("p12", ecSessionID);
            qu.setParameter("p13", cmpoNo);
            qu.setParameter("p14", destIMSI);
            qu.setParameter("p15", otpNo);
            qu.setParameter("p16", destForeignFlag);
            if (destExpTime == null) {
                qu.setParameter("p17", "");
            } else {
                qu.setParameter("p17", destExpTime);
            }

            if (ecSessTime == null) {
                qu.setParameter("p18", "");
            } else {
                qu.setParameter("p18", ecSessTime);
            }
            qu.setParameter("p19", ecTrn);
           

            //Arraylist al=qu.getNamedParameters();
            //qu.
            //System.out.println(qu.getNamedParameters().length+"    ******* "+qu.getNamedParameters().toString());
            simTransferModel = (SimTransferModel) qu.list().get(0);

            //for (int i=0;i<list.size();i++) System.out.println(list.get(i));
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

        return simTransferModel;
    }

}
