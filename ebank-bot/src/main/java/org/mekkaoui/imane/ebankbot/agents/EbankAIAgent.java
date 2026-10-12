package org.mekkaoui.imane.ebankbot.agents;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class EbankAIAgent {

    private final ChatClient chatClient;

    public EbankAIAgent(
            ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            ToolCallbackProvider tools) {

        this.chatClient = chatClientBuilder
                .defaultSystem("""
                        Vous etes un assistant qui se charge de répondre aux questions
                        de l'utilisateur en fonction du contexte fournni à propos des clients et des comptes bancaire.
                        Si aucun contexte n'est fourni, répond avec Je Ne SAIS PAS
                        """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .defaultTools(tools)
                .build();
    }

    public String chat(Prompt prompt, String conversationId) {

        return chatClient
                .prompt(prompt)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .call()
                .content();
    }
    public Flux<String> chatStream(Prompt prompt, String conversationId) {

        return chatClient
                .prompt(prompt)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        conversationId
                ))
                .stream()
                .content();
    }
}