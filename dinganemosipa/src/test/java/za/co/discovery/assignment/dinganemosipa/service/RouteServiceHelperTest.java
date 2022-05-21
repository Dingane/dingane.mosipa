package za.co.discovery.assignment.dinganemosipa.service;

import org.junit.Before;
import org.junit.Test;
import za.co.discovery.assignment.dinganemosipa.model.Node;
import za.co.discovery.assignment.dinganemosipa.model.NodeLink;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for RouteServiceHelper
 */
public class RouteServiceHelperTest {

    private Fixture fixture;

    @Before
    public void init() {
        fixture = new Fixture();
    }

    @Test
    public void shortestDistanceAtoE() {
        fixture.givenDependenciesAreSet(15);
        fixture.whenFindShortestPathIsCalled("A", "E");
        fixture.thenShortestPathIs("[(A : B), (B : D), (D : E)]");
    }

    @Test
    public void shortestDistanceAtoEAdjustedWeight() {
        fixture.givenDependenciesAreSet(10);
        fixture.whenFindShortestPathIsCalled("A", "E");
        fixture.thenShortestPathIs("[(A : C), (C : E)]");
    }

    @Test
    public void shortestDistanceBtoE() {
        fixture.givenDependenciesAreSet(15);
        fixture.whenFindShortestPathIsCalled("B", "E");
        fixture.thenShortestPathIs("[(B : D), (D : E)]");
    }

   @Test
    public void shortestDistanceBtoD() {
        fixture.givenDependenciesAreSet(15);
        fixture.whenFindShortestPathIsCalled("B", "D");
        fixture.thenShortestPathIs("[(B : D)]");
    }

    /**
     * TODO uncomment when reverse logic works and test should pass
     *
     * @Test public void shortestDistanceEtoB()
     * {
     * fixture.givenDependenciesAreSet(15);
     * fixture.whenFindShortestPathIsCalled("E","B");
     * fixture.thenShortestPathIs("[(E : D), (D : B)]");
     * }
     **/

    private class Fixture {

        List<Node> nodeList;
        List<NodeLink> nodeLnkList;

        private RouteServiceHelper routeServiceHelper;
        private String shortestPath;

        public Fixture() {
            routeServiceHelper = new RouteServiceHelper();
        }

        private void givenDependenciesAreSet(long weigthDif) {

            nodeList = new ArrayList<>();
            nodeLnkList = new ArrayList<>();

            String[] planets = new String[]{"A", "B", "C", "D", "E", "F"};

            for (String planet : planets) {
                Node node = new Node();
                node.setSymbol(planet);
                nodeList.add(node);
            }

            nodeLnkList.add(getNodeLink("A", "B", BigDecimal.valueOf(10)));
            nodeLnkList.add(getNodeLink("A", "C", BigDecimal.valueOf(weigthDif)));
            nodeLnkList.add(getNodeLink("C", "E", BigDecimal.valueOf(10)));

            nodeLnkList.add(getNodeLink("B", "D", BigDecimal.valueOf(12)));

            nodeLnkList.add(getNodeLink("D", "E", BigDecimal.valueOf(2)));

            nodeLnkList.add(getNodeLink("B", "F", BigDecimal.valueOf(15)));

            nodeLnkList.add(getNodeLink("D", "F", BigDecimal.valueOf(1)));
            nodeLnkList.add(getNodeLink("F", "E", BigDecimal.valueOf(5)));


            routeServiceHelper.initRouteCalculation(nodeList, nodeLnkList);
        }

        private NodeLink getNodeLink(String source, String target, BigDecimal distance) {
            NodeLink nodeLink = new NodeLink();
            nodeLink.setSource(getNodeBySymbol(source));
            nodeLink.setTarget(getNodeBySymbol(target));
            nodeLink.setDistance(distance);
            return nodeLink;
        }

        private Node getNodeBySymbol(String symbol) {
            for (Node node : nodeList) {
                if (symbol.equals(node.getSymbol())) {
                    return node;
                }
            }

            return null;
        }

        private void whenFindShortestPathIsCalled(String source, String dest) {
            shortestPath = routeServiceHelper.findShortestPath(source, dest);
        }

        private void thenShortestPathIs(String value) {
            assertEquals(value, shortestPath);
        }

    }


}
