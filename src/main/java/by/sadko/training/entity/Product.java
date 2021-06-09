package by.sadko.training.entity;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Entity, Serializable {

    private static final long serialVersionUID = -2277346691381829705L;

    private Long id;
    private String name;
    private double weight;
    private Long materialID;
    private Long technologicalProcessID;

    public Product() {
    }

    public Product(String name, double weight, Long materialID, Long technologicalProcessID) {
        this.name = name;
        this.weight = weight;
        this.materialID = materialID;
        this.technologicalProcessID = technologicalProcessID;
    }

    public Product(Long id, String name, double weight, Long materialID, Long technologicalProcessID) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.materialID = materialID;
        this.technologicalProcessID = technologicalProcessID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", materialID=" + materialID +
                ", technologicalProcessID=" + technologicalProcessID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.weight, weight) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(materialID, product.materialID) &&
                Objects.equals(technologicalProcessID, product.technologicalProcessID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, materialID, technologicalProcessID);
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(Long materialID) {
        this.materialID = materialID;
    }

    public Long getTechnologicalProcessID() {
        return technologicalProcessID;
    }

    public void setTechnologicalProcessID(Long technologicalProcessID) {
        this.technologicalProcessID = technologicalProcessID;
    }
}
