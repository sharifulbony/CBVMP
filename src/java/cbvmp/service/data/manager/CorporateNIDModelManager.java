/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.CorporateNIDModel;
import cbvmp.service.util.log.SingletoneLogger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author rahat
 */
public class CorporateNIDModelManager extends ModelManager {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    public CorporateNIDModel nidFound(
            String schema,
            String nid) 
    {

        CorporateNIDModel list = new CorporateNIDModel();
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_corporate_nid");
            qu.setParameter("p1", nid);

            list = (CorporateNIDModel) qu.list().get(0);

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
