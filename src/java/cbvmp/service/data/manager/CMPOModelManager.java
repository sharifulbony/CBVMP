/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.CMPOModel;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.CustomeEncryption;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
public class CMPOModelManager extends ModelManager{
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");
    
    public CMPOModel verifyChangePasswordRequest(String username)
    {
       CMPOModel cmpoModel = null;
        Session session = HibernateUtil.getSession("CBVMP_MASTER");
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_verify_user");
            qu.setParameter("p1", username);
            if(!qu.list().isEmpty()){
                cmpoModel =(CMPOModel) qu.list().get(0);
                //logger.debug("Validation Password: "+cmpoModel.getPassword()+" "+cmpoModel.getSchemaCode());
            }
            tx.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } 
//        finally {
//            session.close();
//        }
        return cmpoModel;
    }
    
    public CMPOModel verifyCMPOUserNamePassword(String schema,String username,String password){
        
        CMPOModel cmpoModel = null;
        Session session = HibernateUtil.getSession(schema);
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_verify_user");
            qu.setParameter("p1", username);
            if(!qu.list().isEmpty()){
                cmpoModel =(CMPOModel) qu.list().get(0);
                //logger.debug("Validation Password: "+cmpoModel.getPassword()+" "+cmpoModel.getSchemaCode());
                if(!CustomeEncryption.validatePassword(password,cmpoModel.getPassword())){
                    cmpoModel = null;
                } 
                
            }
            tx.commit();
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } 
//        finally {
//            session.close();
//        }
        return cmpoModel;
        
    }
    
}
