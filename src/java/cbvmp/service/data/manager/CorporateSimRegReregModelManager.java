/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.CorporateSimRegReregModel;
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
 * @author SIT
 */
public class CorporateSimRegReregModelManager {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public CorporateSimRegReregModel registrationCoporateSim(
            String schema,
            Integer purposeNo,
            Integer docTypeNo,
            String documentId,
            String retailId,
            String msisdn,
            Date dob,
            String cmpoTrnId,
            Date regDate,
            Integer isVerified,
            String ecSessId,
            Integer isCorporate,
            Integer cmpoNO,
            String imsi,
            Integer foreignFlag,
            Date expTime,
            Date ecSessTime,
            String ecTrn) 
    {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//	//String dateInString = "07/06/2013";
//            Date registrationDate=null;
//            Date dateOfBirth= null;
//            Date expirationTime= null;
//	try {
//                dateOfBirth=format.parse(dob);
//		registrationDate = formatter.parse(regDate);
//                expirationTime = formatter.parse(expTime);
//
//	} catch (ParseException e) {
//		e.printStackTrace();
//	}
        Transaction tx = null;
        CorporateSimRegReregModel list = new CorporateSimRegReregModel();
        Session session = HibernateUtil.getSession(schema);
        try {

            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_corporate_reg_rereg");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", documentId);
            qu.setParameter("p4", retailId);
            qu.setParameter("p5", msisdn);
            qu.setParameter("p6", dob);
            qu.setParameter("p7", cmpoTrnId);
            qu.setParameter("p8", regDate);
            qu.setParameter("p9", isVerified);
            qu.setParameter("p10", ecSessId);
            qu.setParameter("p11", isCorporate);
            qu.setParameter("p12", cmpoNO);
            qu.setParameter("p13", imsi);
            qu.setParameter("p14", foreignFlag);
            if (expTime == null) {
                qu.setParameter("p15", "");
            } else {
                qu.setParameter("p15", expTime);
            }

            if (ecSessTime == null) {
                qu.setParameter("p16", "");
            } else {
                qu.setParameter("p16", ecSessTime);
            }
            qu.setParameter("p17", ecTrn);
            

            list = (CorporateSimRegReregModel) qu.list().get(0);
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
