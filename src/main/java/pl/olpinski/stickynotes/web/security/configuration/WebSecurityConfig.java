package pl.olpinski.stickynotes.web.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.olpinski.stickynotes.web.exceptions.DisabledUserException;
import pl.olpinski.stickynotes.web.exceptions.NotActivatedUserException;
import pl.olpinski.stickynotes.web.security.provider.DatabaseAuthenticationProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                .failureHandler((request, response, exception) ->
                        redirectFailure(request, response, exception))
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

    private void redirectFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String errMsg ="";
        if(exception.getClass().isAssignableFrom(BadCredentialsException.class)){
            errMsg="Invalid username or password.";
        } else if(exception.getClass().isAssignableFrom(NotActivatedUserException.class)){
            errMsg="This user is not activated yet, please confirm your mail.";
        } else if(exception.getClass().isAssignableFrom(DisabledUserException.class)){
            errMsg="This user is disabled, contact administration for help.";
        } else{
            errMsg="Unknown error - " + exception.getMessage();
        }
        request.getSession().setAttribute("message", errMsg);
        response.sendRedirect("/login");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);

    }

}
