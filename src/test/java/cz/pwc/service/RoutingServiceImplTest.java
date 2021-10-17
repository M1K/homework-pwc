package cz.pwc.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class RoutingServiceImplTest {

    private final RoutingServiceImpl routingService = new RoutingServiceImpl(Map.of(
            "CZE", List.of("AUT"),
            "AUT", List.of("CZE", "ITA"),
            "ITA", List.of("AUT"),
            "MEX", List.of("BLZ"),
            "BLZ", List.of("MEX")
    ));

    @ParameterizedTest
    @MethodSource
    void route(String origin, String destination, List<String> expected) {
        List<String> actual = routingService.route(origin, destination);
        assertThat(actual).isEqualTo(expected);
    }

    @SuppressWarnings("unused")
    private static Stream<Arguments> route() {
        return Stream.of(
                Arguments.of("CZE", "XXX", Collections.emptyList()),
                Arguments.of("XXX", "CZE", Collections.emptyList()),
                Arguments.of("XXX", "XXX", Collections.emptyList()),
                Arguments.of("CZE", "MEX", Collections.emptyList()),
                Arguments.of("MEX", "CZE", Collections.emptyList()),
                Arguments.of("CZE", "AUT", List.of("CZE", "AUT")),
                Arguments.of("AUT", "CZE", List.of("AUT", "CZE")),
                Arguments.of("CZE", "ITA", List.of("CZE", "AUT", "ITA")),
                Arguments.of("ITA", "CZE", List.of("ITA", "AUT", "CZE"))
        );
    }
}