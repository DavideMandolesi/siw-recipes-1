package it.uniroma3.siw.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	@Autowired
	DataSource datasource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication()
		.dataSource(datasource)
		/*la seguente query serve a non generare errore visto che comunque spring security si aspetta una tabella
		authorities con i giusti ruoli all'interno (noi non li usiamo)*/
		.authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM credentials WHERE username = ?")
		.usersByUsernameQuery("SELECT username,password,1 as enabled FROM credentials WHERE username =?");
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
	
	@Bean
	protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
		httpSecurity
		//.csrf().and().cors().disable()
		.csrf().disable()
		.cors().disable()
        .authorizeHttpRequests()
        
        // chiunque (autenticato o no) può accedere alle pagine index, login, register, error ai css e alle immagini
        .requestMatchers(HttpMethod.GET,"/","/login","/formNewUser","/register","/error","/static/**", "/images/**", "favicon.ico").permitAll()
        .requestMatchers(HttpMethod.GET,"/profile","/formNewRecipe").authenticated()
        
		// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
        .requestMatchers(HttpMethod.POST, "/login","/register").permitAll()
        
        // tutti gli utenti autenticati possono accere a tutto 
        .anyRequest().permitAll()
        
        
        // LOGIN: qui definiamo il login
        .and().formLogin()
        .loginPage("/login")
        .permitAll()
        .defaultSuccessUrl("/", true) // <--- reindirizzamento sempre a home dopo il login (a parte errori)
        .failureUrl("/login?error=true")
        
        // LOGOUT: qui definiamo il logout
        .and()
        .logout()
        
        // il logout è attivato con una richiesta GET a "/logout"
        .logoutUrl("/logout")
        
        // in caso di successo, si viene reindirizzati alla home
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .clearAuthentication(true).permitAll();
		
        return httpSecurity.build();
	}
}
