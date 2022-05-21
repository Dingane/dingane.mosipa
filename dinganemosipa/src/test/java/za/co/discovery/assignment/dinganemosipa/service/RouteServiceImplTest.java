package za.co.discovery.assignment.dinganemosipa.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test class for RouteServiceImpl
 */
@RunWith(MockitoJUnitRunner.class)
public class RouteServiceImplTest {

    @Mock
    private RouteServiceHelper routeServiceHelper;

    @InjectMocks
    private RouteServiceImpl classUnderTest;

    private String SOURCE = "A";
    private String DESTINATION = "B";



    @Test
    public void thatCorrectHttpStatusIsReturnedWhenAnErrorOccurs() {
        when(routeServiceHelper.findShortestPath(SOURCE, DESTINATION)).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> {
            classUnderTest.findShortestPath(SOURCE, DESTINATION);
        });
    }

    @Test
    public void thatCorrectHttpStatusIsReturnedWhenNoErrorOccurs() {
        when(routeServiceHelper.findShortestPath(SOURCE,DESTINATION)).thenReturn("[shortest route]");
        String results = classUnderTest.findShortestPath(SOURCE, DESTINATION);

        assertEquals("[shortest route]", results);
    }
}
