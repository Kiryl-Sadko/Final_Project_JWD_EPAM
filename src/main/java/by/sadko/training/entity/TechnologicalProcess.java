package by.sadko.training.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TechnologicalProcess implements Entity, Serializable {

    private static final long serialVersionUID = 8168354186324970357L;

    private Long id;
    private String name;
    private List<Operation> operationQueue;

    public TechnologicalProcess() {
    }

    public TechnologicalProcess(Long id, String name) {
        this.id = id;
        this.name = name;
        this.operationQueue = new ArrayList<>();
    }

    public TechnologicalProcess(String name, List<Operation> operationQueue) {
        this.name = name;
        this.operationQueue = operationQueue;
    }

    @Override
    public String toString() {
        return "TechnologicalProcess{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", operationQueue=" + operationQueue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TechnologicalProcess that = (TechnologicalProcess) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(operationQueue, that.operationQueue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, operationQueue);
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

    public List<Operation> getOperationQueue() {
        return operationQueue;
    }

    public void setOperationQueue(List<Operation> operationQueue) {
        this.operationQueue = operationQueue;
    }
}
