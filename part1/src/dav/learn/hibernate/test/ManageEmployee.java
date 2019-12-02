package dav.learn.hibernate.test;

import java.util.List;
import java.util.Iterator;

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

    Integer employee1Id = manager.addEmployee("Alex", "Dolodarenko", 1000000);
    Integer employee2Id = manager.addEmployee("Dyadya", "Petrovich", 13500);
    Integer employee3Id = manager.addEmployee("Keyser", "Söze", 27085);

    manager.listEmployees();

    manager.updateEmployee(employee1Id, 1000000000);

    manager.deleteEmployee(employee2Id);

    manager.listEmployees();

  }

  public Integer addEmployee(String firstName, String lastName, int salary) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer employeeId = null;

    try {
      tx = session.beginTransaction();

      Employee employee = new Employee(firstName, lastName, salary);
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
      for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
        Employee employee = (Employee) iterator.next();
        System.out.print("First name: " + employee.getFirstName());
        System.out.print("Last name: " + employee.getLastName());
        System.out.println("Salary: " + employee.getSalary());
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
