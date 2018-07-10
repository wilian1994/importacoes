package br.com.produtos.importacoes;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private ComercialUserDetailsService comercialUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("*/*.png", "*/*.jpg", "/imagem/**", "/static/**", "/resources/**").permitAll()
				.antMatchers("/usuario/**").hasAuthority("admin")
				.antMatchers("/permissao/**").hasAuthority("admin")
				.antMatchers("/email/**").permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/usuario/login").permitAll()
				.and().rememberMe()
				.and().csrf().disable();
		
		http.logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true)
				.logoutSuccessUrl( "/usuario/login" ).deleteCookies("JSESSIONID")
				.invalidateHttpSession(true); 
		
				
	}
	

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(comercialUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
//		builder
//        .inMemoryAuthentication()
//        .withUser("garrincha").password("123")
//            .roles("USER")
//        .and()
//        .withUser("zico").password("123")
//            .roles("USER");

	}
	
}
