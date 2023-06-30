package lycoris62.socialLoginApp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

    // 정적 자원에 대해서 인증된 사용자 접근의 인가를 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/login", "/", "/profile");
    }

    // HTTP에 대해서 인증과 인가 담당.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.csrf(c -> c.ignoringRequestMatchers("/ciao"));
        http.cors(getCorsConfigurerCustomizer());
        http.headers().frameOptions().disable();
//        http.formLogin().disable();
        http.formLogin().loginPage("/login");
        http.logout().logoutSuccessUrl("/");
        http.authorizeHttpRequests((authz) -> authz.anyRequest().permitAll()); // 토큰 활용시 모든 요청에 대해 인가
        // 테스트할 때만 밑 2개 꺼두기
        http.addFilterBefore(jwtAuthorizationFilter(), BasicAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 기존 방식인 session은 사용 안함
//        http.authorizeHttpRequests()
//                .requestMatchers(HttpMethod.GET, "/profile/{userId:^[0-9]*$}").hasRole("Admin")
//                .anyRequest().authenticated();

        return http.build();
    }

    private static Customizer<CorsConfigurer<HttpSecurity>> getCorsConfigurerCustomizer() {
        return c -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(List.of("example.com", "*"));
                config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                return config;
            };
        };
    }

    // 비밀번호 암호화를 위한 BCrypt 인코딩
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(secretKey);
    }

//    @Bean
//    public UserDetailsService uds() {
//        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
//
//        UserDetails user = User.withUsername("mary").password("12345").authorities("READ").build();
//        uds.createUser(user);
//        return uds;
//    }
}
