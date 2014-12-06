package ua.pp.bizon.filemanager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@org.springframework.context.annotation.Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class Configuration {


	  @Bean
	  public DataSource dataSource() {

	    MysqlDataSource mysqlDataSource = new MysqlDataSource();
	    mysqlDataSource.setUrl("jdbc:mysql://127.0.0.1/fileserver");
	    mysqlDataSource.setUser("root");
	    mysqlDataSource.setPassword("amdsus");
		return mysqlDataSource;
	  }

	  @Bean
	  public EntityManagerFactory entityManagerFactory() {

		  OpenJpaVendorAdapter vendorAdapter = new OpenJpaVendorAdapter();
		    vendorAdapter.setGenerateDdl(true);
	    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	    factory.setJpaVendorAdapter(vendorAdapter);
	    factory.setPackagesToScan("ua.pp.bizon.filemanager.data.jpa");
	    factory.setDataSource(dataSource());
	    factory.afterPropertiesSet();

	    return factory.getObject();
	  }

	  @Bean
	  public PlatformTransactionManager transactionManager() {

	    JpaTransactionManager txManager = new JpaTransactionManager();
	    txManager.setEntityManagerFactory(entityManagerFactory());
	    return txManager;
	  }
}
