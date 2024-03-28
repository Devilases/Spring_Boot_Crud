package hm3.db.config;

import hm3.entity.Lesson;
import hm3.entity.Student;
import hm3.entity.Teacher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class PropertiesSessionFactoryProvider {
    public SessionFactory getSessionFactory(){
        return new Configuration()
                .addAnnotatedClass(Lesson.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
            .buildSessionFactory();
    }
}
