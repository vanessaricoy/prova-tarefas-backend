package br.edu.prova;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.*;
@Configuration
public class SecurityConfig {
 @Bean JwtDecoder jwtDecoder(@Value("${auth.issuer}") String issuer,@Value("${auth.audience}") String audience){
  var decoder=NimbusJwtDecoder.withJwkSetUri(issuer+".well-known/jwks.json").build();
  OAuth2TokenValidator<Jwt> aud=jwt->jwt.getAudience().contains(audience)?OAuth2TokenValidatorResult.success():OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token","Audience inválida",null));
  decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefaultWithIssuer(issuer),aud));return decoder;
 }
 @Bean SecurityFilterChain security(HttpSecurity http,@Qualifier("corsConfigurationSource") CorsConfigurationSource cors) throws Exception{
  return http.csrf(c->c.disable()).cors(c->c.configurationSource(cors))
   .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.anyRequest().authenticated())
   .oauth2ResourceServer(o->o.jwt(j->{})).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.cors-origins}") String origins){
  var c=new CorsConfiguration();c.setAllowedOrigins(Arrays.stream(origins.split(",")).map(String::trim).toList());
  c.setAllowedMethods(List.of("GET","POST","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Authorization","Content-Type"));
  var source=new UrlBasedCorsConfigurationSource();source.registerCorsConfiguration("/**",c);return source;
 }
}
