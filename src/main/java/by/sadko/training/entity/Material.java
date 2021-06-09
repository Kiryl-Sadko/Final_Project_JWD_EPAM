package by.sadko.training.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Material implements Entity, Serializable {

    private static final long serialVersionUID = 3084995411172508705L;

    private Long id;
    private String name;
    private BigDecimal cost;
    private double deliveryTime;

    public Material() {
    }

    public Material(Long id, String name, BigDecimal cost, double deliveryTime) {

        this.id = id;
        this.name = name;
        this.cost = cost;
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", materialName=" + name +
                ", cost=" + cost +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return Double.compare(material.deliveryTime, deliveryTime) == 0 &&
                Objects.equals(id, material.id) &&
                Objects.equals(name, material.name) &&
                Objects.equals(cost, material.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cost, deliveryTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
