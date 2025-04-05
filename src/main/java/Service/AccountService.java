package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for an AccountService when an AccountDAO is passed in.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * Registers a new account if the username is not blank, the password is at least 4 characters long,
     * and the username does not already exist in the database.
     * 
     * @param account The account to be registered.
     * @return The registered account with its account_id, or null if registration fails.
     */
    public Account addAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4 ||
            accountDAO.getAccountByUsername(account.getUsername()) != null) {
            return null; // Registration fails
        }
        return accountDAO.registerAccount(account); // Persist the account
    }

    /**
     * Verifies user login by checking if the username and password match an existing account.
     * 
     * @param account The account containing the username and password to verify.
     * @return The account with its account_id if login is successful, or null if login fails.
     */
    public Account login(Account account) {
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount; // Login successful
        }
        return null; // Login fails
    }

    /**
     * Checks if an account exists in the database by its account_id.
     * 
     * @param accountId The account_id to check.
     * @return true if the account exists, false otherwise.
     */
    public boolean isAccountValid(int accountId) {
        if (accountDAO.getAccountById(accountId) == null) {
            return false; // Account does not exist
        }
        return true; // Account exists
    }
}
