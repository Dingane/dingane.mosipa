package za.co.discovery.assignment.dinganemosipa.model;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class NodeLinkTest {

    @Test
    public void properties() {
        String description = "Desc";
        String symbol = "A";
        BigInteger id = BigInteger.ONE;

        Node node = new Node();
        node.setDescription(description);
        node.setSymbol(symbol);
        node.setId(id);

        assertEquals(description, node.getDescription());
        assertEquals(symbol, node.getSymbol());
        assertEquals(id, node.getId());
    }
}