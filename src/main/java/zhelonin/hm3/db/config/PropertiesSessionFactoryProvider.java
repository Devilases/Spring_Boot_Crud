package zhelonin.hm3.db.config;

import zhelonin.hm3.entity.Lesson;
import zhelonin.hm3.entity.Student;
import zhelonin.hm3.entity.Teacher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PropertiesSessionFactoryProvider {
    public SessionFactory getSessionFactory(){
        return new Configuration()
                .addAnnotatedClass(Lesson.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Teacher.class)
            .buildSessionFactory();
    }
}
