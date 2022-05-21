package za.co.discovery.assignment.dinganemosipa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
@Entity
public class NodeLink {
    @Id
    @GeneratedValue
    private BigInteger id;
    @OneToOne
    @JoinColumn(name = "source")
    private Node source;
    private BigDecimal distance;
    @OneToOne
    @JoinColumn(name = "target")
    private Node target;
}
