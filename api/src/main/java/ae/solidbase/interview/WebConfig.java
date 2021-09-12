package ae.solidbase.interview;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * Hero is the Configuration Class we'll be using for handling cors
 *
 * @author Farooq Ahmed
 *
 */
@Configuration
public class WebConfig {

    @Value( "${app.allow.origins}" )
    private String allowOrigins;
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        System.out.println("allow origin: "+allowOrigins);
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081/","http://localhost:8081/user/","http://localhost:8081/**")
                        .allowedOrigins(allowOrigins)
                        .allowedMethods("PUT", "DELETE","GET", "POST");
            }
        };
    }
}
