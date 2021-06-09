package by.sadko.training.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Operation implements Entity, Serializable {

    private static final long serialVersionUID = -3408501620576269122L;

    private Long id;
    private OperationType type;
    private BigDecimal cost;
    private double time;

    public Operation() {
    }

    public Operation(Long id, OperationType type, BigDecimal cost, double time) {
        this.id = id;
        this.type = type;
        this.cost = cost;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", type=" + type +
                ", cost=" + cost +
                ", time=" + time +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Double.compare(operation.time, time) == 0 &&
                Objects.equals(id, operation.id) &&
                type == operation.type &&
                Objects.equals(cost, operation.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, cost, time);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
