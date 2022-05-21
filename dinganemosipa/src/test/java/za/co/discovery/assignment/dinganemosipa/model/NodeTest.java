package za.co.discovery.assignment.dinganemosipa.model;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Test
    public void properties() {

        String description = "Desc";
        String symbol = "A";
        BigInteger id = BigInteger.ONE;

        Node sourceNode = new Node();
        sourceNode.setDescription(description);
        sourceNode.setSymbol(symbol);
        sourceNode.setId(id);

        Node targetNode = new Node();
        targetNode.setDescription(description);
        targetNode.setSymbol(symbol);
        targetNode.setId(id);

        NodeLink nodeLink = new NodeLink();

        BigDecimal distance = BigDecimal.TEN;
        nodeLink.setDistance(distance);
        nodeLink.setSource(sourceNode);
        nodeLink.setTarget(targetNode);
        BigInteger nodeLinkId = BigInteger.TWO;

        nodeLink.setId(nodeLinkId);

        assertEquals(sourceNode, nodeLink.getSource());
        assertEquals(targetNode, nodeLink.getTarget());
        assertEquals(distance, nodeLink.getDistance());
        assertEquals(nodeLinkId, nodeLink.getId());
    }
}