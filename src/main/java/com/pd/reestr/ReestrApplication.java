package com.pd.reestr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReestrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReestrApplication.class, args);
	}

}


//search test
//package com.pd.reestr;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
//import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
//
//@SpringBootApplication(exclude = {
//		DataSourceAutoConfiguration.class,
//		HibernateJpaAutoConfiguration.class
//})
//public class ReestrApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ReestrApplication.class, args);
//	}
//}
