package hm3.repo.hiber;

import hm3.db.config.PropertiesSessionFactoryProvider;
import hm3.entity.Lesson;
import hm3.entity.Student;
import hm3.repo.specification.LessonRepo;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class LessonRepoHibernateImpl implements LessonRepo {
  PropertiesSessionFactoryProvider p = new PropertiesSessionFactoryProvider();
  private SessionFactory sessionFactory = p.getSessionFactory();
  private static LessonRepo instance;

  public static synchronized LessonRepo getInstance() {
    if (instance == null) {
      instance = new LessonRepoHibernateImpl();
    }
    return instance;
  }


  @Override
  public Lesson save(Lesson lesson) {

    try(Session session = sessionFactory.openSession()) {
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Integer id = (Integer) session.save(lesson);
      transaction.commit();
      return session.get(Lesson.class, id);
    }
  }

  @Override
  public void update(Lesson lesson) {
    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      session.update(lesson);
      transaction.commit();
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Lesson lesson = session.get(Lesson.class, id);
      session.delete(lesson);
      transaction.commit();
      return true;
    }
  }

  @Override
  public Optional<Lesson> findById(Integer id) {
    Lesson lesson = null;
    try(Session session = sessionFactory.openSession()){
      Query<Lesson> query = session.createQuery("from Lesson l left join fetch l.students where l.id = :id", Lesson.class);
      query.setParameter("id",id);
      lesson = query.getSingleResult();
    }
    return Optional.ofNullable(lesson);
  }

  @Override
  public List<Lesson> findAll() {
    try(Session session = sessionFactory.openSession()){
      Query<Lesson> query = session.createQuery("from Lesson l left join fetch l.students ", Lesson.class);
      return query.list();
    }
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try(Session session = sessionFactory.openSession()){
      Lesson lesson = session.get(Lesson.class, id);
      if(lesson != null) isExist = true;
    }
    return isExist;
  }


  @Override
  public List<Student> findStudentByLessonId(Integer lessonId) {
    Lesson lesson = null;
    try(Session session = sessionFactory.openSession()){
      Query<Lesson> query = session.createQuery("from Lesson l left join fetch l.students where l.id = :id", Lesson.class);
      query.setParameter("id",lessonId);
      lesson = query.getSingleResult();
    }
    return lesson.getStudents();
  }
}
