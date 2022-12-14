package bg.softuni.paintShop.config;


import bg.softuni.paintShop.model.enums.RoleEnum;
import bg.softuni.paintShop.repository.UserRepository;
import bg.softuni.paintShop.service.PaintShopDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //@formatter:off
        http.

                        authorizeRequests().
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().

                        antMatchers("/", "/users/login","/users/register",
                        "/products/all","/used/products/forSale","/contacts").permitAll().
                        antMatchers("/admin/**").hasRole(RoleEnum.ADMIN.name()).
                        anyRequest().
                authenticated().
                and().
                        formLogin().
                        loginPage("/users/login").
                        usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY).
                        passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).
                        defaultSuccessUrl("/").
                        failureForwardUrl("/users/login-error").
                and().
                        logout().
                        logoutUrl("/users/logout").
        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                deleteCookies("JSESSIONID");
//        @formatter:off

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new PaintShopDetailsService(userRepository);
    }
}
