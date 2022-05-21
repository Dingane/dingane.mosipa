package za.co.discovery.assignment.dinganemosipa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import za.co.discovery.assignment.dinganemosipa.model.NodeLink;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface NodeLinkRepository extends JpaRepository<NodeLink, BigInteger>
{
  List<NodeLink> findBySourceSymbol(String source);
  NodeLink findBySourceSymbolAndTargetSymbol(String source, String target);
  List<NodeLink> findByTargetSymbol(String target);
}
