package cz.pwc.service;

import cz.pwc.model.Step;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoutingServiceImpl implements RoutingService {

    private final Map<String, List<String>> countryMap;

    /**
     * @param origin      start country
     * @param destination end country
     * @return empty collection for invalid route, otherwise names of countries from start to end
     */
    @Override
    public List<String> route(String origin, String destination) {
        if (!countryMap.containsKey(origin) || !countryMap.containsKey(destination) || origin.equals(destination)) {
            return Collections.emptyList();
        }

        Step routeStep = routeStep(origin, destination);
        if (routeStep == null) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();
        while (routeStep != null) {
            result.add(routeStep.getCca3());
            routeStep = routeStep.getFrom();
        }
        // Reverse result to get order from origin to destination
        Collections.reverse(result);
        return result;
    }

    /**
     * @param origin      start country
     * @param destination end country
     * @return step with reference to previous step, from that we can get country names
     */
    private Step routeStep(String origin, String destination) {
        Set<Step> steps = Set.of(new Step(origin));
        Set<String> processed = new HashSet<>(countryMap.size());

        while (steps.size() > 0) {
            Set<Step> nextSteps = new HashSet<>();
            for (Step step : steps) {
                for (String country : countryMap.get(step.getCca3())) {
                    Step nextStep = new Step(country, step);

                    if (country.equals(destination)) {
                        // Destination found, we can stop here
                        return nextStep;
                    } else if (processed.contains(country)) {
                        // We already processed this country, skip
                        continue;
                    }

                    // Process in next loop
                    nextSteps.add(nextStep);
                    processed.add(country);
                }
            }
            steps = nextSteps;
        }

        return null;
    }
}
