package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("register", this::postAddAccountHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postAddMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        app.get("messages/{message_id}", this::getMessageByIdHandler);     
        app.delete("messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("messages/{message_id}", this::patchUpdateMessagebyIdHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);
        
        return app;
    }

    /**
     * This is an example handler for the register endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void postAddAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedAccount));
        } else {
            ctx.status(400);
        }
    }

    /**
     * This is an example handler for the login endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loggedInAccount = accountService.login(account);
        if (loggedInAccount != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(loggedInAccount));
        } else {
            ctx.status(401);
        }
    }

    /**
     * Handler for the addMessage endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void postAddMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(addedMessage));
        } else {
            ctx.status(400);
        }
    }

    /**
     * Handler for the getAllMessages endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        ctx.status(200);
        ctx.json(mapper.writeValueAsString(messages));
    }

    /**
     * Handler for the getMessageById endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(message));
        } else {
            ctx.status(200);
            ctx.json(""); // Empty response body if no message is found
        }
    }

    /**
     * Handler for the deleteMessageById endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(deletedMessage));
        } else {
            ctx.status(200);
            ctx.json(""); // Empty response body if no message is found
        }
    }

    /**
     * Handler for the patchUpdateMessagebyId endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void patchUpdateMessagebyIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message existingMessage = messageService.getMessageById(messageId);

        if (existingMessage == null) {
            ctx.status(400); // Message does not exist
            return;
        }

        Message updatedMessage = mapper.readValue(ctx.body(), Message.class);
        updatedMessage.setMessage_id(messageId); // Ensure the ID is set correctly

        Message result = messageService.updateMessageById(updatedMessage);
        if (result != null) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(result));
        } else {
            ctx.status(400); // Invalid update
        }
    }

    /**
     * This is an example handler for the getAllMessagesbyAccountId endpoint.
     * @param ctx The Javalin Context object manages information about both the HTTP request and response.
     * @throws JsonProcessingException will be thrown if the JSON cannot be processed.
     */
    private void getAllMessagesByAccountIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
        ctx.status(200);
        ctx.json(mapper.writeValueAsString(messages));
    }
}