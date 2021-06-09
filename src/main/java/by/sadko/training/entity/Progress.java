package by.sadko.training.entity;

import java.io.Serializable;
import java.util.Objects;

public class Progress implements Entity, Serializable {

    private static final long serialVersionUID = -8562905872946753642L;

    private Long id;
    private int status;
    private String log;

    public Progress() {
    }

    public Progress(Long id, int status, String log) {
        this.id = id;
        this.status = status;
        this.log = log;
    }

    public Progress(int status, String log) {
        this.status = status;
        this.log = log;
    }

    @Override
    public String toString() {
        return "Progress{" +
                "id=" + id +
                ", status=" + status +
                ", log='" + log + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Progress progress = (Progress) o;
        return Objects.equals(id, progress.id) &&
                Objects.equals(status, progress.status) &&
                Objects.equals(log, progress.log);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, log);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
