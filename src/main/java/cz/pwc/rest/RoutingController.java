package cz.pwc.rest;

import cz.pwc.model.RouteResponse;
import cz.pwc.service.RoutingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/routing")
@RequiredArgsConstructor
public class RoutingController {

    private final RoutingService routingService;

    @GetMapping("/{origin}/{destination}")
    public RouteResponse route(@PathVariable String origin, @PathVariable String destination) {
        List<String> route = routingService.route(origin, destination);
        if (CollectionUtils.isEmpty(route)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new RouteResponse(route);
    }
}
