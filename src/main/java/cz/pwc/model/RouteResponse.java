package cz.pwc.model;

import java.util.Collection;
import lombok.Data;

@Data
public class RouteResponse {

    private final Collection<String> route;
}
