package hm3.repo.hiber;

import hm3.db.config.PropertiesSessionFactoryProvider;
import hm3.entity.Lesson;
import hm3.entity.Student;
import hm3.repo.specification.StudentRepo;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class StudentRepoHibernateImpl implements StudentRepo {

  PropertiesSessionFactoryProvider p = new PropertiesSessionFactoryProvider();
  private SessionFactory sessionFactory = p.getSessionFactory();
  private static StudentRepo instance;

  public static synchronized StudentRepo getInstance() {
    if (instance == null) {
      instance = new StudentRepoHibernateImpl();
    }
    return instance;
  }


  @Override
  public Student save(Student student) {
    try(Session session = sessionFactory.openSession()) {
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Integer id = (Integer) session.save(student);
      transaction.commit();

      return session.get(Student.class, id);
    }
  }

  @Override
  public void update(Student student) {
    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      session.update(student);
      transaction.commit();
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    return false;
  }

  @Override
  public Optional<Student> findById(Integer id) {
    Student student = null;
    try(Session session = sessionFactory.openSession()){
      Query<Student> query = session.createQuery("from Student s left join fetch s.lessons where s.id = :id", Student.class);
      query.setParameter("id",id);
      student = query.getSingleResult();
    }
    return Optional.ofNullable(student);
  }

  @Override
  public List<Student> findAll() {
    try(Session session = sessionFactory.openSession()){
      Query<Student> query = session.createQuery("from Student s left join fetch s.lessons", Student.class);
      return query.list();
    }
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try(Session session = sessionFactory.openSession()){
      Student student = session.get(Student.class, id);
      if(student != null) isExist = true;
    }
    return isExist;
  }

  @Override
  public List<Lesson> findLessonByStudentId(Integer stdId) {
    Student student = null;
    try(Session session = sessionFactory.openSession()){
      Query<Student> query = session.createQuery("from Student s join fetch s.lessons where s.id = :id", Student.class);
      query.setParameter("id",stdId);
      student = query.getSingleResult();
    }
    return student.getLessons();
  }
}
