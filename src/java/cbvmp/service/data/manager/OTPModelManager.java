/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.OTPModel;
import cbvmp.service.util.log.SingletoneLogger;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
public class OTPModelManager extends ModelManager{
    
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");
    public OTPModel generateOneTimePassword(String schema,String NID,Integer purpose,Integer cmpoId,String IP, Integer docTypNo){
        //List<User> list = new ArrayList<User>();
        List<OTPModel> list = new ArrayList<>();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_generate_otp");
            qu.setParameter("p1", NID);
            qu.setParameter("p2", purpose);
            qu.setParameter("p3", cmpoId);
            qu.setParameter("p4", IP);
            qu.setParameter("p5", docTypNo);
            list = qu.list();
            
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
        return list.get(0);
    }
    
}
