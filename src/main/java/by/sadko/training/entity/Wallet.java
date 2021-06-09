package by.sadko.training.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Wallet implements Entity, Serializable {

    private static final long serialVersionUID = -7924136153565574909L;

    private Long id = 0L;
    private BigDecimal balance = new BigDecimal(0);

    public Wallet() {

    }

    public Wallet(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(id, wallet.id) &&
                Objects.equals(balance, wallet.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
