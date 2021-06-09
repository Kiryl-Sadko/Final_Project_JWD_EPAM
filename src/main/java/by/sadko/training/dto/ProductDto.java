package by.sadko.training.dto;

import by.sadko.training.entity.Operation;

import java.math.BigDecimal;
import java.util.List;

public class ProductDto {

    private long id;
    private String name;
    private double weight;
    private String materialName;
    private List<Operation> operationQueue;
    private BigDecimal cost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public List<Operation> getOperationQueue() {
        return operationQueue;
    }

    public void setOperationQueue(List<Operation> operationQueue) {
        this.operationQueue = operationQueue;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
