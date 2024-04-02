package zhelonin.hm3.db;

import org.hibernate.SessionFactory;

public interface SessionFactoryProvider {
   SessionFactory getSessionFactory();
}
