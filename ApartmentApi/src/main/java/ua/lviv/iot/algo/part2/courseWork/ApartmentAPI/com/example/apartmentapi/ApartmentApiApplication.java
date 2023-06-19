package ua.lviv.iot.algo.part2.courseWork.ApartmentAPI.com.example.apartmentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan("ua.lviv.iot.algo.part2.courseWork.ApartmentAPI")
public class ApartmentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApartmentApiApplication.class, args);
	}

}
