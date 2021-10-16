package cz.pwc.service;

import java.util.List;

public interface RoutingService {

    List<String> route(String origin, String destination);
}
