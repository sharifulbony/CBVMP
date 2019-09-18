/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.SimReplacementModel;
import cbvmp.service.util.log.SingletoneLogger;
import java.text.SimpleDateFormat;
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
public class SimReplacementModelManager {
     Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

     public SimReplacementModel replaceSim(
            String schema,
            Integer purposeNo,
            Integer docTypeNo,
            String docID,           
            String retailId,
            String msisdn,            
            String cmpoTrnId,         
            Integer cmpoNo,
            String imsi,          
            Date replaceDate
    ) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        Date replacementDate = null;
//
//        try {
//
//            replacementDate = formatter.parse(replaceDate);            
//
//        } catch (ParseException e) {
//            logger.warn(e.getMessage());
//            e.printStackTrace();
//        }

        SimReplacementModel simReplacementModel = null;
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_sim_replacement");
            qu.setParameter("p1", purposeNo);
            qu.setParameter("p2", docTypeNo);
            qu.setParameter("p3", docID);
            qu.setParameter("p4", retailId);
            qu.setParameter("p5", msisdn);
            qu.setParameter("p6", cmpoTrnId);           
            qu.setParameter("p7", cmpoNo);
            qu.setParameter("p8", imsi);
            qu.setParameter("p9", replaceDate);          
            
            
            simReplacementModel = (SimReplacementModel) qu.list().get(0);

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

        return simReplacementModel;
    }
}
