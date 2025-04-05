package Service;

import Model.Message;
import DAO.MessageDAO;

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
            message.getMessage_text().length() > 255 ||
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

    /**
     * Retrieves a message by its ID.
     * 
     * @param messageId The ID of the message to be retrieved.
     * @return The message with the specified ID, or null if no such message exists.
     */
    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    /**
     * Deletes a message by its ID.
     * 
     * @param messageId The ID of the message to be deleted.
     * @return The deleted message, or null if no such message exists.
     */
    public Message deleteMessageById(int messageId) {
        Message messageToDelete = messageDAO.getMessageById(messageId);
        if (messageToDelete != null) {
            messageDAO.deleteMessageById(messageId);
            return messageToDelete;
        }
        return null; // Message does not exist
    }

    /**
     * Updates a message by its ID.
     * 
     * @param message The message to be updated.
     * @return The updated message, or null if the operation fails.
     */
    public Message updateMessageById(Message message) {
        if (message.getMessage_text() == null || 
            message.getMessage_text().isBlank() || 
            message.getMessage_text().length() > 255) {
            return null; // Invalid message text
        }

        Message existingMessage = messageDAO.getMessageById(message.getMessage_id());
        if (existingMessage == null) {
            return null; // Message does not exist
        }

        messageDAO.updateMessageById(message);
        return messageDAO.getMessageById(message.getMessage_id()); // Return the updated message
    }

    /**
     * Retrieves all messages posted by a specific account ID.
     * 
     * @param accountId The ID of the account whose messages are to be retrieved.
     * @return A list of messages posted by the specified account, or an empty list if no messages are found.
     */
    public List<Message> getAllMessagesByAccountId(int accountId) {
        if (!accountService.isAccountValid(accountId)) {
            return new ArrayList<>(); // Return an empty list if the account is invalid
        }
        return messageDAO.getAllMessagesByAccountId(accountId); // Retrieve messages from the DAO
    }
    
}