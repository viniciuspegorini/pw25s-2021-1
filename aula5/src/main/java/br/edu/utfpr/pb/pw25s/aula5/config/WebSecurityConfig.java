package br.edu.utfpr.pb.pw25s.aula5.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("usuarioServiceImpl")
    private UserDetailsService usuarioDetailServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().accessDeniedPage("/403")
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=bad_credentials").permitAll()
                .and().logout().logoutSuccessUrl("/login")
                .and().authorizeRequests()
                    .antMatchers("/categoria/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/marca/**").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/produto/**").hasAnyRole("USER", "ADMIN")
                    .antMatchers("/produto/**").hasRole("ADMIN")
                    .antMatchers("/cadastro/**").permitAll()
                    .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/images/**")
                .antMatchers("/webjars/**")
                .antMatchers("/js/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService( usuarioDetailServiceImpl )
                .passwordEncoder( new BCryptPasswordEncoder());
    }
}
