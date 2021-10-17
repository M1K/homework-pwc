package cz.pwc;

import cz.pwc.util.JsonUtils;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Map<String, List<String>> countryMap(@Value("${pwc.countries-filepath}") String filepath) {
        return JsonUtils.countryBorderMapFromFile(filepath);
    }
}
