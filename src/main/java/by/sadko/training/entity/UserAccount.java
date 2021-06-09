package by.sadko.training.entity;

import by.sadko.training.util.AppConstants;
import by.sadko.training.validation.Email;
import by.sadko.training.validation.MaxLength;
import by.sadko.training.validation.MinLength;
import by.sadko.training.validation.Password;
import by.sadko.training.validation.ValidBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ValidBean("userAccount")
public class UserAccount implements Entity, Serializable {

    private static final long serialVersionUID = -7042250598599249365L;

    private Long id;
    @MinLength(4)
    @MaxLength(20)
    private String name;
    @Password(regex = AppConstants.PASSWORD_PATTERN)
    private String password;
    @Email(regex = AppConstants.EMAIL_PATTERN)
    private String email;

    private boolean isActive = true;
    private Long walletID;
    private List<UserRole> userRoles = new ArrayList<>();

    public UserAccount() {
        userRoles.add(UserRole.CUSTOMER);
    }

    public UserAccount(Long id, String name, String password, String email,
                       boolean isActive, Long walletID, List<UserRole> userRoles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.walletID = walletID;
        this.userRoles = userRoles;
    }

    public UserAccount(String name, String email, String password) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.walletID = 0L;
        this.userRoles.add(UserRole.CUSTOMER);
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", walletID=" + walletID +
                ", userRoles=" + userRoles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return isActive == that.isActive &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(walletID, that.walletID) &&
                Objects.equals(userRoles, that.userRoles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, isActive, walletID, userRoles);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getWalletId() {
        return walletID;
    }

    public void setWalletID(Long walletID) {
        this.walletID = walletID;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
