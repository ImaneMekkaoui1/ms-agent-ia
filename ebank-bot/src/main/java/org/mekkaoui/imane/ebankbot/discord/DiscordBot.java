package org.mekkaoui.imane.ebankbot.discord;

import com.zgamelogic.discord.annotations.DiscordController;
import com.zgamelogic.discord.annotations.DiscordMapping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.mekkaoui.imane.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.prompt.Prompt;

@DiscordController
public class DiscordBot {

    private final EbankAIAgent ebankAIAgent;

    public DiscordBot(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }

    @DiscordMapping
    private void perform(MessageReceivedEvent event) {

        System.out.println("========================================");
        System.out.println("DISCORD EVENT RECEIVED!");
        System.out.println("Author: " + event.getAuthor().getName());
        System.out.println("Message: " + event.getMessage().getContentRaw());
        System.out.println("Channel: " + event.getChannel().getName());
        System.out.println("========================================");

        if (event.getAuthor().isBot()) {
            return;
        }

        String query = event.getMessage().getContentRaw();

        String conversationId =
                "discord-user-" + event.getAuthor().getId()
                        + "-channel-" + event.getChannel().getId();

        String response = ebankAIAgent.chat(new Prompt(query), conversationId);

        event.getChannel()
                .sendMessage(response)
                .queue();
    }
}