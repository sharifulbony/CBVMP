/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.ExpTimeModel;
import cbvmp.service.util.log.SingletoneLogger;
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
public class ExpTimeModelManager extends ModelManager{
    
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");
    public ExpTimeModel updateExpTime(String schema,Integer docTypNo,String docId,Date expTime){
        //List<User> list = new ArrayList<User>();
        List<ExpTimeModel> list = new ArrayList<>();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_update_exp_time");
            qu.setParameter("p1", docTypNo);
            qu.setParameter("p2", docId);
            if (expTime == null) {
                qu.setParameter("p3", "");
            } else {
                qu.setParameter("p3", expTime);
            }
       
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
