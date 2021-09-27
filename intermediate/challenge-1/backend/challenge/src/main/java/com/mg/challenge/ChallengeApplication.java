package com.mg.challenge;

import java.util.ArrayList;
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
import com.mg.challenge.repositories.BusRepository;
import com.mg.challenge.repositories.UserDetailsRepository;

@SpringBootApplication
public class ChallengeApplication {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

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
		List<Authority> authorities = new ArrayList<Authority>();
		authorities.add(createAuthority("ROLE_EMPLOYEE", "Employee Role"));
		authorities.add(createAuthority("ROLE_MANAGER", "Manager Role"));

		User user = new User();
		user.setUsername("admin");
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setEmail("admin@domain.name");
		user.setPhoneNumber("000000");
		user.setEnabled(true);
		user.setAuthorities(authorities);
		userDetailsRepository.save(user);

		Driver driver = new Driver("1234-1234-1234", "John", "Doe");
		Bus bus = new Bus();
		bus.setCapacity(10);
		bus.setModel("Model 1");
		bus.setMake("Make 1");
		bus.setAssociatedDriver(driver);
		busRepository.save(bus);
	}

	private Authority createAuthority(String code, String description) {
		Authority authority = new Authority();
		authority.setCode(code);
		authority.setDescription(description);
		return authority;
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
