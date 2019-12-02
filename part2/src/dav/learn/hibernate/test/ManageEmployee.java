package dav.learn.hibernate.test;

import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

public class ManageEmployee {

  private static SessionFactory factory;

  public static void main(String[] args) {

    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Couldn't create a SessionFactory object: " + ex);
      throw new ExceptionInInitializerError(ex);
    }

    ManageEmployee manager = new ManageEmployee();

    Set employee1Certificates = new HashSet();
    employee1Certificates.add(new Certificate("MCA"));
    employee1Certificates.add(new Certificate("MBA"));
    employee1Certificates.add(new Certificate("PMP"));

    Integer employee1Id = manager.addEmployee("Krut", "Dikaya", 5000, employee1Certificates);

    Set employee2Certificates = new HashSet();
    employee2Certificates.add(new Certificate("BCA"));
    employee2Certificates.add(new Certificate("BA"));

    Integer employee2Id = manager.addEmployee("Almost", "Possible", 25000, employee2Certificates);

    manager.listEmployees();

    manager.updateEmployee(employee1Id, 10000);
    manager.deleteEmployee(employee2Id);

    manager.listEmployees();
  }

  public Integer addEmployee(String firstName, String lastName, int salary, Set certificates) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer employeeId = null;

    try {
      tx = session.beginTransaction();

      Employee employee = new Employee(firstName, lastName, salary);
      employee.setCertificates(certificates);

      employeeId = (Integer) session.save(employee);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    } finally {
      session.close();
    }

    return employeeId;
  }

  public void listEmployees() {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      List employees = session.createQuery("from Employee").list();
      for (Iterator employeeIterator = employees.iterator(); employeeIterator.hasNext();) {
        Employee employee = (Employee) employeeIterator.next();
        System.out.print("First name: " + employee.getFirstName());
        System.out.print("Last name: " + employee.getLastName());
        System.out.println("Salary: " + employee.getSalary());

        Set certificates = employee.getCertificates();
        for (Iterator certificatesIterator = certificates.iterator(); certificatesIterator.hasNext();) {
          Certificate certificate = (Certificate) certificatesIterator.next();
          System.out.println("Certificate: " + certificate.getName());
        }
      }

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    } finally {
      session.close();
    }
  }

  public void updateEmployee(Integer employeeId, int salary) {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      Employee employee = (Employee) session.get(Employee.class, employeeId);
      employee.setSalary(salary);
      session.update(employee);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    } finally {
      session.close();
    }

  }

  public void deleteEmployee(Integer employeeId) {
    Session session = factory.openSession();
    Transaction tx = null;

    try {
      tx = session.beginTransaction();

      Employee employee = (Employee) session.get(Employee.class, employeeId);
      session.delete(employee);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null)
        tx.rollback();

      e.printStackTrace();
    } finally {
      session.close();
    }
  }

}
