package by.sadko.training.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

import static by.sadko.training.entity.ContractStatus.NOT_PAYED;

public class Contract implements Entity, Serializable {

    private static final long serialVersionUID = 619661866144625142L;

    private Long id;
    private Long userAccountID;
    private Long customerID;
    private Long productID;
    private int quantity;
    private BigDecimal price;
    private ContractStatus status = NOT_PAYED;
    private Calendar paymentDate;
    private Calendar completionDate;
    private Long progressID;

    public Contract() {
    }

    public Contract(Long id, Long userAccountID, Long customerID, Long productID, int quantity,
                    BigDecimal price, ContractStatus status, Calendar paymentDate, Calendar completionDate, Long progressID) {
        this.id = id;
        this.userAccountID = userAccountID;
        this.customerID = customerID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.paymentDate = paymentDate;
        this.completionDate = completionDate;
        this.progressID = progressID;
    }

    public Contract(Long userAccountID, Long customerID, Long productID, int quantity,
                    BigDecimal price, Calendar paymentDate, Calendar completionDate, Long progressID) {
        this.userAccountID = userAccountID;
        this.customerID = customerID;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.paymentDate = paymentDate;
        this.completionDate = completionDate;
        this.progressID = progressID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return quantity == contract.quantity &&
                Objects.equals(id, contract.id) &&
                Objects.equals(userAccountID, contract.userAccountID) &&
                Objects.equals(customerID, contract.customerID) &&
                Objects.equals(productID, contract.productID) &&
                Objects.equals(price, contract.price) &&
                status == contract.status &&
                Objects.equals(paymentDate, contract.paymentDate) &&
                Objects.equals(completionDate, contract.completionDate) &&
                Objects.equals(progressID, contract.progressID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAccountID, customerID, productID, quantity, price,
                status, paymentDate, completionDate, progressID);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(Long userAccountID) {
        this.userAccountID = userAccountID;
    }

    public Long getCustomerId() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getProductId() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public Calendar getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Calendar paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Calendar getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Calendar completionDate) {
        this.completionDate = completionDate;
    }

    public Long getProgressId() {
        return progressID;
    }

    public void setProgressID(Long progressID) {
        this.progressID = progressID;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", price=" + price +
                ", status=" + status +
                ", paymentDate=" + paymentDate.getTime() +
                ", completionDate=" + completionDate.getTime() +
                '}';
    }
}
