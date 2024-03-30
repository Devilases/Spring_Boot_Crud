package zhelonin.hm3.repo.hiber;

import zhelonin.hm3.db.config.PropertiesSessionFactoryProvider;
import zhelonin.hm3.entity.Teacher;
import zhelonin.hm3.repo.specification.TeacherRepo;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class TeacherRepoHibernateImpl implements TeacherRepo {

  private PropertiesSessionFactoryProvider p;
  private SessionFactory sessionFactory = p.getSessionFactory();

  public TeacherRepoHibernateImpl(PropertiesSessionFactoryProvider p) {
    this.p = p;
  }

  @Override
  public Teacher save(Teacher teacher) {

    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Integer id = (Integer)session.save(teacher);

      transaction.commit();
      return session.get(Teacher.class, id);
    }

  }

  @Override
  public void update(Teacher teacher) {
    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      session.update(teacher);
      transaction.commit();
    }
  }

  @Override
  public boolean deleteById(Integer id) {
    try(Session session = sessionFactory.openSession()){
      Transaction transaction = session.getTransaction();
      transaction.begin();
      Teacher teacher = session.get(Teacher.class, id);
      session.delete(teacher);
      transaction.commit();
      return true;
    }
  }

  @Override
  public Optional<Teacher> findById(Integer id) {
    Teacher teacher = null;
    try(Session session = sessionFactory.openSession()){
      teacher = session.get(Teacher.class, id);
    }
    return Optional.ofNullable(teacher);
  }

  @Override
  public List<Teacher> findAll() {
    try(Session session = sessionFactory.openSession()){
      Query<Teacher> query = session.createQuery("from Teacher ", Teacher.class);
      return query.list();
    }
  }

  @Override
  public boolean exitsById(Integer id) {
    boolean isExist = false;
    try(Session session = sessionFactory.openSession()){
      Teacher teacher = session.get(Teacher.class, id);
      if(teacher != null) isExist = true;
    }
    return isExist;
  }
}
