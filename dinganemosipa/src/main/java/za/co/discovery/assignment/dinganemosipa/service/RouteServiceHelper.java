package za.co.discovery.assignment.dinganemosipa.service;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import za.co.discovery.assignment.dinganemosipa.model.Node;
import za.co.discovery.assignment.dinganemosipa.model.NodeLink;
import za.co.discovery.assignment.dinganemosipa.repository.NodeLinkRepository;
import za.co.discovery.assignment.dinganemosipa.repository.NodeRepository;

import java.util.List;

@Component
public class RouteServiceHelper {

    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(
            DefaultWeightedEdge.class);

    public void initRouteCalculation(List<Node> nodeList, List<NodeLink> nodeLnkList) {
        mapNodes(nodeList);
        mapNodeLink(nodeLnkList);
    }

    private void mapNodes(List<Node> nodeList) {
        nodeList.stream().forEach(node -> {
            this.graph.addVertex(node.getSymbol());
        });
    }

    private void mapNodeLink(List<NodeLink> nodeLnkList) {

        nodeLnkList.stream().forEach(nodeLink -> {
            String source = nodeLink.getSource().getSymbol();
            String destination = nodeLink.getTarget().getSymbol();

            DefaultWeightedEdge edge = this.graph.addEdge(source, destination);
            this.graph.setEdgeWeight(edge, nodeLink.getDistance().floatValue());
        });
    }


    public String findShortestPath(String source, String destination) {
        return DijkstraShortestPath.findPathBetween(this.graph, source, destination).toString();
    }
}
