package cz.pwc.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.pwc.model.Country;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

@Slf4j
@UtilityClass
public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static Map<String, List<String>> countryBorderMapFromFile(String filepath) {
        return countriesFromFile(filepath).stream()
                .filter(country -> !CollectionUtils.isEmpty(country.getBorders()))
                .collect(Collectors.toMap(Country::getCca3, Country::getBorders));
    }

    @SneakyThrows
    public static List<Country> countriesFromFile(String filepath) {
        InputStream inputStream = JsonUtils.class.getClassLoader().getResourceAsStream(filepath);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filepath);
        }

        StringBuilder sb = new StringBuilder();
        try (InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String content = sb.toString();
        return OBJECT_MAPPER.readValue(content, new TypeReference<>() {
        });
    }
}
