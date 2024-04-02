package zhelonin.hm3.repo.hiber;

import zhelonin.hm3.db.SessionFactoryProvider;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.entity.Student;
import zhelonin.hm3.repo.specification.StudentRepo;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepoHibernateImpl implements StudentRepo {

  private SessionFactory sessionFactory = null;
  private SessionFactoryProvider sessionFactoryProvider;


  public StudentRepoHibernateImpl(SessionFactoryProvider sessionFactoryProvider) {
    this.sessionFactoryProvider = sessionFactoryProvider;
  }

  private SessionFactory initSessionFactory(){
    if (sessionFactory == null) {
      sessionFactory = sessionFactoryProvider.getSessionFactory();
    }
    return sessionFactory;
  }
  @Override
  public Student save(Student student) {
    try(Session session = initSessionFactory().openSession()) {
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Integer id = (Integer) session.save(student);
      transaction.commit();

      return session.get(Student.class, id);
    }
  }

  @Override
  public void update(Student student) {
    try(Session session = initSessionFactory().openSession()){
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
    Student student;
    try(Session session = initSessionFactory().openSession()){
      Query<Student> query = session.createQuery("from Student s left join fetch s.lessons where s.id = :id", Student.class);
      query.setParameter("id",id);
      student = query.getSingleResult();
    }
    return Optional.ofNullable(student);
  }

  @Override
  public List<Student> findAll() {
    try(Session session = initSessionFactory().openSession()){
      Query<Student> query = session.createQuery("from Student s left join fetch s.lessons", Student.class);
      return query.list();
    }
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try(Session session = initSessionFactory().openSession()){
      Student student = session.get(Student.class, id);
      if(student != null) isExist = true;
    }
    return isExist;
  }

  @Override
  public List<Lesson> findLessonByStudentId(Integer stdId) {
    Student student;
    try(Session session = initSessionFactory().openSession()){
      Query<Student> query = session.createQuery("from Student s join fetch s.lessons where s.id = :id", Student.class);
      query.setParameter("id",stdId);
      student = query.getSingleResult();
    }
    return student.getLessons();
  }
}
