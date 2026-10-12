package org.mekkaoui.imane.ebankbot.telegram;

import jakarta.annotation.PostConstruct;
import org.mekkaoui.imane.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.token}")
    private String telegramBotToken;

    @Value("${telegram.username}")
    private String telegramBotUsername;

    private final EbankAIAgent aiAgent;

    public TelegramBot(EbankAIAgent aiAgent) {
        this.aiAgent = aiAgent;
    }

    @PostConstruct
    public void registerTelegramBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(this);

            System.out.println("========================================");
            System.out.println("Telegram Bot registered successfully!");
            System.out.println("Bot: " + telegramBotUsername);
            System.out.println("========================================");

        } catch (TelegramApiException e) {
            throw new RuntimeException("Failed to register Telegram bot", e);
        }
    }

    @Override
    public void onUpdateReceived(Update telegramRequest) {

        // Ignore updates that don't contain a message
        if (!telegramRequest.hasMessage()) {
            return;
        }

        var message = telegramRequest.getMessage();

        Long chatId = message.getChatId();

        // Get the text sent by the user
        String query = message.hasText()
                ? message.getText()
                : message.getCaption();

        // Ignore messages without text/caption
        if (query == null || query.isBlank()) {
            sendMessage(
                    chatId,
                    "Je peux traiter les messages texte pour le moment."
            );
            return;
        }

        System.out.println("========================================");
        System.out.println("TELEGRAM MESSAGE RECEIVED");
        System.out.println("Chat ID: " + chatId);
        System.out.println("Message: " + query);
        System.out.println("========================================");

        try {

            // Show "typing..." while Gemini is processing
            sendTypingQuestion(chatId);

            // Keep a separate conversation memory for each Telegram chat
            String conversationId = "telegram-" + chatId;

            // Send the user's question to the AI agent
            String response = aiAgent.chat(new Prompt(query), conversationId);

            // Send Gemini's response back to Telegram
            sendMessage(chatId, response);

        } catch (Exception e) {

            System.err.println("Error while processing Telegram message:");
            e.printStackTrace();

            sendMessage(
                    chatId,
                    "Désolé, une erreur s'est produite lors du traitement de votre demande."
            );
        }
    }

    /**
     * Displays the "typing..." status in Telegram
     */
    private void sendTypingQuestion(Long chatId) {
        try {
            SendChatAction action = new SendChatAction();
            action.setChatId(chatId.toString());
            action.setAction(ActionType.TYPING);
            execute(action);
        } catch (TelegramApiException e) {
            System.err.println("Could not send typing status: "
                    + e.getMessage());
        }
    }

    /**
     * Sends a text message to a Telegram chat
     */
    private void sendMessage(Long chatId, String text) {

        try {

            SendMessage message = new SendMessage();

            message.setChatId(chatId.toString());
            message.setText(text);

            execute(message);

        } catch (TelegramApiException e) {
            System.err.println("Could not send Telegram message: "
                    + e.getMessage());
        }
    }

    /**
     * Telegram bot username
     */
    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    /**
     * Telegram bot token
     */
    @Override
    public String getBotToken() {
        return telegramBotToken;
    }
}
