package za.co.discovery.assignment.dinganemosipa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteServiceHelper routeServiceHelper;

    //TODO implement cache since calculation is the same for performance
    @Override
    public String findShortestPath(String source, String destination) {
        return routeServiceHelper.findShortestPath(source,destination);
    }
}
