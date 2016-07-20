package test;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestSpringSpringSec2Application {
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(TestSpringSpringSec2Application.class, args);
	}
	
	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.httpBasic().and()
				.authorizeRequests()
					.antMatchers("/index.html", "/home.html", "/login.html", "/").hasAnyAuthority("ROLE_USER")
					.anyRequest().authenticated();
//					.and()
//				.csrf()
//					.csrfTokenRepository(new CookieCsrfTokenRepository());
			// @formatter:on
		}
	}
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication().withUser("test").password("123").authorities("ROLE_USER");
		auth.inMemoryAuthentication().withUser("test1").password("1234").authorities("ROLE_USER");
	}
}
