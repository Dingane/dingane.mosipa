package za.co.discovery.assignment.dinganemosipa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.discovery.assignment.dinganemosipa.dto.ResponseDTO;
import za.co.discovery.assignment.dinganemosipa.service.RouteService;

@RestController
public class InterstellarController {

    @Autowired
    @Lazy
    private RouteService shortestPathService;

    //TODO add planet name/desc on return results
    @GetMapping("/route/{source}/{dest}")
    public ResponseEntity<ResponseDTO> getShortestPath(@PathVariable("source") String source, @PathVariable("dest") String destination) {

        ResponseDTO result = new ResponseDTO();
        try {
            result.setResult(shortestPathService.findShortestPath(source, destination));
            result.setMsg("success");
        } catch (Exception e) {
            result.setMsg("error");
            result.setResult("an error occurred whilst finding shortest path.Please try again later");
        }

        return ResponseEntity.ok(result);
    }

}
