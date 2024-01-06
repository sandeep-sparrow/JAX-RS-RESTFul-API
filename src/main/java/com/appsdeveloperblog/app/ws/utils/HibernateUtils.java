package com.appsdeveloperblog.app.ws.utils;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 *
 * @author: Sandeep prajapati
 *
 */
public class HibernateUtils {

    private static SessionFactory sessionFactory = null;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();

        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch (HibernateException exception){
            System.err.println("Initial SessionFactory creation failed." + exception);
            throw new ExceptionInInitializerError(exception);
        }

    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
