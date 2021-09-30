package com.mg.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.mg.challenge.pojos.Bus;
import com.mg.challenge.pojos.Driver;
import com.mg.challenge.pojos.Role;
import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.pojos.User;
import com.mg.challenge.repositories.BusRepository;
import com.mg.challenge.repositories.RoleRepository;
import com.mg.challenge.repositories.ScheduleRepository;
import com.mg.challenge.repositories.UserRepository;

@SpringBootApplication
public class ChallengeApplication {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ScheduleRepository scheduleRepository;

//	@Autowired
//	private DriverRepository driverRepository; 

	@Autowired
	private BusRepository busRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@PostConstruct
	protected void init() {
		Role employeeRole = createAuthority("ROLE_EMPLOYEE", "Employee Role");
		Role managerRole = createAuthority("ROLE_MANAGER", "Manager Role");
		roleRepository.saveAllAndFlush(Arrays.asList(employeeRole, managerRole));
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByCode("ROLE_MANAGER"));
		roles.add(roleRepository.findByCode("ROLE_EMPLOYEE"));
		
		User user = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.enabled(true)
				.roles(roles)
				.build();
		userRepository.save(user);
		
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(roleRepository.findByCode("ROLE_EMPLOYEE"));
		
		User user2 = User.builder()
				.username("employee")
				.password(passwordEncoder.encode("employee"))
				.enabled(true)
				.roles(roles2)
				.build();
		userRepository.save(user2);
		
		List<Role> roles3 = new ArrayList<Role>();
		roles3.add(roleRepository.findByCode("ROLE_MANAGER"));
		
		User user3 = User.builder()
				.username("manager")
				.password(passwordEncoder.encode("manager"))
				.enabled(true)
				.roles(roles3)
				.build();
		userRepository.save(user3);

		Driver driver = new Driver("1234-1234-1234", "John", "Doe");
		Bus bus = Bus.builder()
				.capacity(10)
				.model("Model 1")
				.make("Make 1")
				.associatedDriver(driver)
				.build();
		busRepository.save(bus);
		
		Schedule schedule = Schedule.builder()
				.busID(1)
				.driverSSN("1234-1234-1234")
				.day(new Date())
				.timeFrom(new Date())
				.timeTo(new Date())
				.build();
		scheduleRepository.saveAndFlush(schedule);
	}

	private Role createAuthority(String code, String description) {
		return Role.builder()
				.code(code)
				.description(description)
				.build();
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
//		config.addAllowedOrigin("*"); // this allows all origin
		config.addAllowedHeader("*"); // this allows all headers
		config.addAllowedMethod("OPTIONS");
		config.addAllowedMethod("HEAD");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("PATCH");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
