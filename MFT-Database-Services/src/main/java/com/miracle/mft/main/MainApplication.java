package com.miracle.mft.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import com.miracle.mft.dbUtils.LoadGlobalProperties;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.miracle.mft.*")
public class MainApplication extends SpringBootServletInitializer {
/*
 * Run following commands in maven goal
 * run ->run configuration->place below commands in goal
 * mvn install:install-file  -Dfile="C:\Users\mthadi\Documents\GITHUB\mft-explorer\MFT-Database-Services\lib\db2jcc4.jar" -DgroupId=com.miracle.mft *-DartifactId=db2jcc4 -Dversion=3.2.0 -Dpackaging=jar -DlocalRepositoryPath="C:\Users\mthadi\.m2\repository"
 * mvn install:install-file  -Dfile="C:\Users\mthadi\Documents\GITHUB\mft-explorer\MFT-Database-Services\lib\db2jcc_license_cu.jar"  *-DgroupId=com.miracle.mft -DartifactId=db2jcc4_license -Dversion=3.2.0 -Dpackaging=jar -DlocalRepositoryPath="C:\Users\mthadi\.m2\repository"
 *mvn install:install-file  -Dfile="C:\Users\mthadi\Documents\GITHUB\mft-explorer\MFT-Database-Services\lib\db2jcc.jar" -DgroupId=com.miracle.mft *-DartifactId=db2jcc -Dversion=3.2.0 -Dpackaging=jar -DlocalRepositoryPath="C:\Users\mthadi\.m2\repository"
 * 
 */
	public static void main(String[] args) {
		System.setProperty("spring.global.filepath", "C:\\MFTDB\\globalProperties.properties");
		LoadGlobalProperties.refreshGlobalProperties();
		SpringApplication.run(MainApplication.class, args);
	}
//
	static {
		System.setProperty("spring.global.filepath", "C:\\MFTDB\\globalProperties.properties");
		LoadGlobalProperties.refreshGlobalProperties();
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}
}
