package za.co.discovery.assignment.dinganemosipa.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigInteger;

@Setter
@Getter
@Entity
public class Node {
    @Id
    @GeneratedValue
    private BigInteger id;
    private String symbol;
    private String description;
}
