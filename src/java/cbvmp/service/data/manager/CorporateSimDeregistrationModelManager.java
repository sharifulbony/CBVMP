/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.CorporateSimDeregistrationModel;
import cbvmp.service.util.log.SingletoneLogger;
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
public class CorporateSimDeregistrationModelManager extends ModelManager {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public CorporateSimDeregistrationModel deregisterCorporateSim(
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
            Integer cmpNo,
            Date ecSessTime,
            String ecTrn) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date dateOfDereg = null , expirationTime=null;
//
//        try {
//
//            dateOfDereg = formatter.parse(deRegDate);
//            //expirationTime = formatter.parse(expTime);
//        } catch (ParseException e) {
//            logger.warn(e.getMessage());
//            e.printStackTrace();
//        }

        CorporateSimDeregistrationModel list = new CorporateSimDeregistrationModel();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_corporate_sim_deregistration");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", documentId);
            qu.setParameter("p4", retailId);
            qu.setParameter("p5", msisdn);
            qu.setParameter("p6", cmpoTrnId);
            qu.setParameter("p7", deRegDate);
            qu.setParameter("p8", isVerified);
            qu.setParameter("p9", ecSessId);
            qu.setParameter("p10", cmpNo);
            if (ecSessTime == null) {
                qu.setParameter("p11", "");
            } else {
                qu.setParameter("p11", ecSessTime);
            }
            qu.setParameter("p12", ecTrn);

            list = (CorporateSimDeregistrationModel) qu.list().get(0);

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
