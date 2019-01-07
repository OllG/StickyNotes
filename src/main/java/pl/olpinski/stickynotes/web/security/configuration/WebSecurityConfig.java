package pl.olpinski.stickynotes.web.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.olpinski.stickynotes.web.security.provider.DatabaseAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private DatabaseAuthenticationProvider authenticationProvider;

    public WebSecurityConfig(DatabaseAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register", "/user/activate", "/user/activate/*", "/h2-console", "/h2-console/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/notes/")
                .permitAll()
                .and()
                // configure logout
                .logout()
                .logoutUrl("/logout")
                .permitAll()
                //enable using h2 console
                .and()
                .headers()
                .frameOptions()
                .disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }

}
