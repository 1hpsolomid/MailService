package cherkas.util;

import java.io.File;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	 
    private final SessionFactory sessionFactory;
 
    public HibernateUtil() throws HibernateException {
     sessionFactory = configureSessionFactory();
    }
    
    /**
     * Create SessionFactory
     * @return {@link SessionFactory}
     * @throws HibernateException
     */
    private SessionFactory configureSessionFactory()
            throws HibernateException {
    		
    		String userDir = System.getProperty("user.dir");
    		
    		Configuration configuration = new Configuration().configure(new File(userDir + "src/main/java/cherkas/util/hibernate.cfg.xml"));
            return configuration.buildSessionFactory();
            
    }
 
    /**
     * Получить фабрику сессий
     * @return {@link SessionFactory}
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}