package cz.pwc.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.pwc.model.Country;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

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
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return OBJECT_MAPPER.readValue(sb.toString(), new TypeReference<>() {
        });
    }
}
