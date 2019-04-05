package com.miracle.mft.dbUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseUtills {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.password}")
	private String dbPassword;
	@Value("${spring.datasource.username}")
	private String dbUserName;
	@Value("${spring.datasource.driver.name}")
	private String driverNmae;

	@Bean
	public JdbcTemplate getJdbcTemplate() throws ClassNotFoundException {

		DriverManagerDataSource dataSource = new DriverManagerDataSource(url, dbUserName, dbPassword);
		// Class.forName("com.ibm.db2.jcc.DB2Driver");
		dataSource.setDriverClassName(driverNmae);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		return jdbcTemplate;
	}

}
