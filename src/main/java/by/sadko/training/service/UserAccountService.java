package by.sadko.training.service;

import by.sadko.training.entity.UserAccount;
import by.sadko.training.exception.DAOException;

import java.util.List;

public interface UserAccountService {

    UserAccount login(String email, String password) throws DAOException;

    Long signUp(UserAccount userAccount) throws DAOException;

    List<UserAccount> findAll() throws DAOException;

    /*boolean deleteAccount(UserAccount userAccount) throws DAOException;*/

    boolean deleteAccount(Long id) throws DAOException;

    UserAccount findById(Long id) throws DAOException;

    UserAccount findByEmail(String email) throws DAOException;
}
