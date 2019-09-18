/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.HibernateUtil;
import cbvmp.service.data.model.APIUserInfoModel;
import cbvmp.service.data.model.OTPModel;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.CustomeEncryption;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author rahat
 *
 *
 *
 */
public class APIUserInfoModelManager extends ModelManager {
    
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");
    
    public APIUserInfoModel changePassword(Integer cmpoNo, String userID, String oldPass, String newPass) {
        Transaction tx = null;
        APIUserInfoModel list = new APIUserInfoModel();
        Session session = HibernateUtil.getSession("CBVMP_MASTER");
        try {
            String newPassword = CustomeEncryption.encryptPassword(newPass);
            //logger.debug("New Password:"+newPassword);
            tx = session.getTransaction();
            tx.begin();
            Query qu = session.getNamedQuery("sp_change_password_req");
            qu.setParameter("p1", cmpoNo);
            qu.setParameter("p2", userID);
            qu.setParameter("p3", oldPass);          
            qu.setParameter("p4", newPassword);

            list = (APIUserInfoModel) qu.list().get(0);
            System.out.println("loggedin");
            //for (int i=0;i<list.size();i++) System.out.println(list.get(i));

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            logger.error(e.getMessage());
        } 
//        finally {
//            session.close();
//        }
        return list;
    }

}
