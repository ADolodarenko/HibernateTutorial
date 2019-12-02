package dav.learn.hibernate.test;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class ManageEmployee {

  private static SessionFactory factory;

  public static void main(String[] args) {

    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Couldn't create a SessionFactory object: " + ex);
      throw new ExceptionInInitializerError(ex);
    }

  }

}
