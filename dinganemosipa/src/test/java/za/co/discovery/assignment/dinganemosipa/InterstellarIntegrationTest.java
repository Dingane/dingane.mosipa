package za.co.discovery.assignment.dinganemosipa;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterstellarIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAToD() throws Exception {
        String convertTedValue = this.restTemplate.getForObject("http://localhost:" + port + "/route/A/D", String.class);
        assertEquals( "{\"msg\":\"success\",\"result\":\"[(A : D)]\"}",convertTedValue);

    }

    @Test
    public void testAtoM() throws Exception {
        String convertTedValue = this.restTemplate.getForObject("http://localhost:" + port + "/route/A/M", String.class);
        assertEquals( "{\"msg\":\"success\",\"result\":\"[(A : D), (D : M)]\"}",convertTedValue);
    }


    @Test
    public void testInvalidRoute() throws Exception {
        String convertTedValue = this.restTemplate.getForObject("http://localhost:" + port + "/route/A/SX", String.class);
        assertEquals("{\"msg\":\"error\",\"result\":\"an error occurred whilst finding shortest path.Please try again later\"}", convertTedValue);
    }

}
