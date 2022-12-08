package learn.thruhike.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();


        //TODO: Add paths
        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("USER","ADMIN")
                .antMatchers("/**").denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
