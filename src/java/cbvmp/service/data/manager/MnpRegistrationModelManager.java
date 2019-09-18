/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.MnpRegistrationModel;
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

public class MnpRegistrationModelManager extends ModelManager {

    private Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public MnpRegistrationModel mnpRegistration(
            String schema,
            Integer purposeNo,
            Integer docTypeNo,
            String docID,
            String retailID,
            String msisdn,
            Date dob,
            String cmpoTrnID,
            Date regDate,
            Integer isVerify,
            String ecSessID,
            Integer isCorp,
            Integer cmpoNo,
            String imsi,
            Integer foreignFlag,
            Date expDate,
            Date ecSessTime,
            String ecTrn) {

//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        //String dateInString = "07/06/2013";
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        Date dateOfBirth = null, registrationDate = null, expirationDate=null;
//
//        try {
//
//            dateOfBirth = format.parse(dob);
//            registrationDate = formatter.parse(regDate);
//            expirationDate = formatter.parse(expDate);
//
//            //System.out.println(date);
//            //System.out.println(formatter.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        MnpRegistrationModel list = new MnpRegistrationModel();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();

            Query qu = session.getNamedQuery("sp_mnp_reg");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", docID);
            qu.setParameter("p4", retailID);
            qu.setParameter("p5", msisdn);
            qu.setParameter("p6", dob);
            qu.setParameter("p7", cmpoTrnID);
            qu.setParameter("p8", regDate);
            qu.setParameter("p9", isVerify);
            qu.setParameter("p10", ecSessID);
            qu.setParameter("p11", isCorp);
            qu.setParameter("p12", cmpoNo);
            qu.setParameter("p13", imsi);
            qu.setParameter("p14", foreignFlag);

            if (expDate == null) {
                qu.setParameter("p15", "");
            } else {
                qu.setParameter("p15", expDate);
            }

            if (ecSessTime == null) {
                qu.setParameter("p16", "");
            } else {
                qu.setParameter("p16", ecSessTime);
            }
             qu.setParameter("p17", ecTrn);
           
            list = (MnpRegistrationModel) qu.list().get(0);
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
