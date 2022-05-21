package za.co.discovery.assignment.dinganemosipa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.dinganemosipa.model.Node;


@Repository
public interface NodeRepository extends JpaRepository<Node, String> {

    Node findBySymbol(String symbol);
}
