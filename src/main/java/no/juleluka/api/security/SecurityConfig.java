package no.juleluka.api.security;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value(value = "${auth0.apiAudience}")
    private String apiAudience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    private static final String[] SWAGGER_UI_RESOURCES = {
        "/swagger-resources/**",
        "/swagger-ui.html",
        "/v2/api-docs",
        "/webjars/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("KEN> configure security");
        JwtWebSecurityConfigurer
                .forRS256(apiAudience, issuer)
                .configure(http)
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/open/**").permitAll()
//                .antMatchers(HttpMethod.GET, "/calendars/**").hasAuthority("read:calendars")
//                .antMatchers(HttpMethod.POST, "/edit/**").hasRole("PARTICIPANT")
//                .antMatchers(HttpMethod.DELETE, "/edit/**").hasAuthority("admin:calendars")
                .anyRequest().authenticated();
//                .accessDecisionManager(accessDecisionManager());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_UI_RESOURCES);
    }

}