package DAO;

import Util.ConnectionUtil;
import Model.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {

    /**
     * Adds a message to the database.
     * 
     * @param message The message to be added.
     * @return The added message with its generated ID, or null if the operation fails.
     */
    public Message addMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Set the parameters for the prepared statement
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            // Execute the update
            preparedStatement.executeUpdate();
            // Get the generated keys
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generatedId = pkeyResultSet.getInt(1);
                // Return the message with the generated ID
                return new Message(generatedId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null; // Placeholder return statement
    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return A list of all messages, or an empty list if no messages are found.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                // Create a new Message object for each row in the result set
                int messageId = rs.getInt("message_id");
                int postedBy = rs.getInt("posted_by");
                String messageText = rs.getString("message_text");
                long timePostedEpoch = rs.getLong("time_posted_epoch");

                Message message = new Message(messageId, postedBy, messageText, timePostedEpoch);
                messages.add(message); // Add the message to the list
            }
            return messages; // Return the list of messages
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(); // return an empty list regardless if an exception occurs
    }
}
