/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data;

import java.util.ArrayList;
import java.util.HashMap;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author tanbir
 */
public class HibernateUtil {

    private static HashMap<String, SessionFactory> hm = new HashMap<String, SessionFactory>();
    static final Log4jLoggerAdapter HIBERNATE_LOGGER = (Log4jLoggerAdapter) LoggerFactory.getLogger(HibernateUtil.class);
    static ArrayList<String> cmpoList = new ArrayList<String>();
    static ArrayList<String> cfgList = new ArrayList<String>();

    static {
        SessionFactory sessionFactory;
        cmpoList.add("CBVMP_MASTER");
        cmpoList.add("CMPO_GP");
        cmpoList.add("CMPO_BL");
        cmpoList.add("CMPO_ROBI");
        cmpoList.add("CMPO_CITYCELL");
        cmpoList.add("CMPO_TELETALK");
        cmpoList.add("CMPO_AIRTEL");
        cmpoList.add("CBVMP_MNP");

        cfgList.add("hibernate_master.cfg.xml");
        cfgList.add("hibernate_GP.cfg.xml");
        cfgList.add("hibernate_BL.cfg.xml");
        cfgList.add("hibernate_ROBI.cfg.xml");
        cfgList.add("hibernate_CITYCELL.cfg.xml");
        cfgList.add("hibernate_TELETALK.cfg.xml");
        cfgList.add("hibernate_AIRTEL.cfg.xml");
        cfgList.add("hibernate_MNP.cfg.xml");

        Configuration configuration = null;
        try {

            for (int i = 0; i < cfgList.size(); i++) {
                HIBERNATE_LOGGER.debug("Configuration File:" + cfgList.get(i));
                configuration = new Configuration().configure(cfgList.get(i));
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                HIBERNATE_LOGGER.debug("Creating session Factory:" + builder.toString());
                sessionFactory = configuration.buildSessionFactory(builder.build());
                HIBERNATE_LOGGER.debug("Session Factory:" + sessionFactory.toString());
                hm.put(cmpoList.get(i), sessionFactory);

            }

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex + "  test:" + configuration + " size:" + cfgList.size());
        }
    }

    public static Session getSession(String schema) {
        SessionFactory sessionFactory = hm.get(schema);
        Session session = sessionFactory.getCurrentSession();
//        if(!session.isOpen()){
//            HIBERNATE_LOGGER.warn("New sesssion opened");
//            session = sessionFactory.openSession();
//            
//        }
        return session;
    }
}
