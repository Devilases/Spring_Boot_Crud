package zhelonin.hm3.repo.hiber;

import zhelonin.hm3.db.config.PropertiesSessionFactoryProvider;
import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.entity.Student;
import zhelonin.hm3.repo.specification.LessonRepo;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LessonRepoHibernateImpl implements LessonRepo {


  PropertiesSessionFactoryProvider p;
  private SessionFactory sessionFactory = p.getSessionFactory();


  public LessonRepoHibernateImpl(PropertiesSessionFactoryProvider p) {
    this.p = p;
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
