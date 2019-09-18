/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.CorporateSimTransferModel;
import cbvmp.service.util.log.SingletoneLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author rahat
 */

public class CorporateSimTransferModelManager extends ModelManager {

    private Log4jLoggerAdapter logger = SingletoneLogger.getLogger(CorporateSimTransferModelManager.class);

    public CorporateSimTransferModel transferCorporateSim(
            String schema,
            Integer purposeNo,
            Integer docTypeNo,
            String docID,
            Integer destDocTypeNo,
            String destDocID,
            String retailID,
            String msisdn,
            Date destDob,
            String cmpoTrnID,
            Date regDate,
            Integer isVerify,
            String ecSessID,
            Integer cmpoNo,
            String destImsi,
            Integer destForeignFlag,
            Date destExpTime,
            Date ecSessTime,
            String ecTrn
    ) 
    {

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        //String dateInString = "07/06/2013";
//        Date dateOfBirth = null, registrationDate = null, destExpirationTime=null;
//
//        try {
//
//            dateOfBirth = format.parse(destDob);
//            registrationDate = formatter.parse(regDate);
//            destExpirationTime = formatter.parse(destExpTime);
//
//            //System.out.println(date);
//            //System.out.println(formatter.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        CorporateSimTransferModel list = new CorporateSimTransferModel();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();

            Query qu = session.getNamedQuery("sp_corporate_sim_transfer");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", docID);
            qu.setParameter("p4", destDocTypeNo);
            qu.setParameter("p5", destDocID);
            qu.setParameter("p6", retailID);
            qu.setParameter("p7", msisdn);
            qu.setParameter("p8", destDob);
            qu.setParameter("p9", cmpoTrnID);
            qu.setParameter("p10", regDate);
            qu.setParameter("p11", isVerify);
            qu.setParameter("p12", ecSessID);
            qu.setParameter("p13", cmpoNo);
            qu.setParameter("p14", destImsi);
            qu.setParameter("p15", destForeignFlag);
            if (destExpTime == null) {
                qu.setParameter("p16", "");
            } else {
                qu.setParameter("p16", destExpTime);
            }

            if (ecSessTime == null) {
                qu.setParameter("p17", "");
            } else {
                qu.setParameter("p17", ecSessTime);
            }
            qu.setParameter("p18", ecTrn);

            list = (CorporateSimTransferModel) qu.list().get(0);
            //System.out.println("loggedin");
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
        return list;
    }

}
