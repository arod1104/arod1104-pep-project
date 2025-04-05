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
// import java.util.ArrayList;

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
        // app.get("example-endpoint", this::exampleHandler);
        app.post("register", this::postAddAccountHandler);
        app.post("login", this::postLoginHandler);
        app.post("messages", this::postAddMessageHandler);
        app.get("messages", this::getAllMessagesHandler);
        
        return app;
    }

    // /**
    //  * This is an example handler for an example endpoint.
    //  * @param context The Javalin Context object manages information about both the HTTP request and response.
    //  */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

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

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        if (messages != null && !messages.isEmpty()) {
            ctx.status(200);
            ctx.json(mapper.writeValueAsString(messages));
        }
    }
}