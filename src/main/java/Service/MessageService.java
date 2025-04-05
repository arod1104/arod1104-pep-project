package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import Service.AccountService;

import java.util.List;
import java.util.ArrayList;

public class MessageService {
    private MessageDAO messageDAO;
    private AccountService accountService;

    /**
     * Default constructor for MessageService that initializes the MessageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
        accountService = new AccountService();
    }

    /**
     * Constructor for a MessageService when a MessageDAO is passed in.
     * @param messageDAO The MessageDAO to be used by this service.
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }


    /**
     * Adds a message to the database if the message text is not blank, 
     * the length is less than 255 characters, and the account exists.
     * 
     * @param message The message to be added.
     * @return The added message with its generated ID, or null if the operation fails.
     */
    public Message addMessage(Message message) {
        if (message.getMessage_text() == null || 
            message.getMessage_text().isBlank() ||
            message.getMessage_text().length() >= 255 ||
            !accountService.isAccountValid(message.getPosted_by())) {
            return null; // Message content is invalid
        }
        return messageDAO.addMessage(message); // Persist the message
    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return A list of all messages, or an empty list if no messages are found.
     */
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    
}
