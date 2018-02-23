package org.producer;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SpringBootApplication
@PropertySource("datasource.properties")
public class ProducerApplication 
{
    
    private LocalSessionFactoryBean  sessionFactory=null;
    
    private Properties hibernateProperties = new Properties();
    
    @Value("classpath:hibernate.properties")
    private Resource hibernatePropertiesResource = null;
    
    private Properties datasourceProperties = new Properties();
    
    @Value("classpath:datasource.properties")
    private Resource datasourceResource = null;
        
    public static void main( String[] args )
    {
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    @Bean
    public DataSource getDataSource() {
    	
    	ComboPooledDataSource ds = new ComboPooledDataSource();
    	
    	try {
    		datasourceProperties.load(datasourceResource.getInputStream());
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    	
    	ds.setProperties(datasourceProperties);
    	
    	return ds;
    }
    
    @Bean
    public LocalSessionFactoryBean  getSessionFactory() { 
    	
    	if(sessionFactory==null) {
    	
    	sessionFactory = new LocalSessionFactoryBean();
    	
    	sessionFactory.setDataSource(getDataSource());
    	    	
    	String []packagesToScan = {
    			
    	};
    	
    	sessionFactory.setPackagesToScan(packagesToScan);
    	    	

    	try {
			hibernateProperties.load(hibernatePropertiesResource.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	sessionFactory.setHibernateProperties(hibernateProperties);
    	
    	}
    	
    	return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
}
