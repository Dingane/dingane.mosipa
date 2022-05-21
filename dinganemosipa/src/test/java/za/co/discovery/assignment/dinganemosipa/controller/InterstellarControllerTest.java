package za.co.discovery.assignment.dinganemosipa.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.co.discovery.assignment.dinganemosipa.dto.ResponseDTO;
import za.co.discovery.assignment.dinganemosipa.service.RouteService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for InterstellarController
 */
@RunWith(MockitoJUnitRunner.class)
public class InterstellarControllerTest {


    @Mock
    private RouteService routeService;

    @InjectMocks
    private InterstellarController classUnderTest;

    private String SOURCE = "A";
    private String DESTINATION = "B";



    @Test
    public void thatCorrectHttpStatusIsReturnedWhenAnErrorOccurs() {
        when(routeService.findShortestPath(SOURCE, DESTINATION)).thenThrow(NullPointerException.class);
        ResponseEntity<ResponseDTO> results = classUnderTest.getShortestPath(SOURCE, DESTINATION);
        assertEquals(HttpStatus.OK, results.getStatusCode());

        ResponseDTO body = results.getBody();
        assertEquals("an error occurred whilst finding shortest path.Please try again later", body.getResult());
        assertEquals("error", body.getMsg());
    }

    @Test
    public void thatCorrectHttpStatusIsReturnedWhenNoErrorOccurs() {
        when(routeService.findShortestPath(SOURCE,DESTINATION)).thenReturn("[shortest route]");
        ResponseEntity<ResponseDTO> results = classUnderTest.getShortestPath(SOURCE, DESTINATION);
        assertEquals(HttpStatus.OK, results.getStatusCode());

        ResponseDTO body = results.getBody();
        assertEquals("[shortest route]", body.getResult());
        assertEquals("success", body.getMsg());
    }
}
