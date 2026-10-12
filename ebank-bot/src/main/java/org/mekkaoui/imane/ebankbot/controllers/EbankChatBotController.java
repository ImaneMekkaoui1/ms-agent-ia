package org.mekkaoui.imane.ebankbot.controllers;

import org.mekkaoui.imane.ebankbot.agents.EbankAIAgent;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;

@RestController
public class EbankChatBotController {
    private EbankAIAgent ebankAIAgent;

    public EbankChatBotController(EbankAIAgent ebankAIAgent) {
        this.ebankAIAgent = ebankAIAgent;
    }
    @GetMapping(value="/chat",produces= MediaType.TEXT_PLAIN_VALUE)
    public String chat(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {

        return ebankAIAgent.chat(new Prompt(query), conversationId);
    }
    @GetMapping(value="/chatStream",produces= MediaType.TEXT_PLAIN_VALUE)
    public Flux<String> chatStream(
            @RequestParam(name = "query", defaultValue = "Bonjour") String query,
            @RequestParam(name = "conversationId", defaultValue = "default") String conversationId) {

        return ebankAIAgent.chatStream(new Prompt(query), conversationId);
    }


}
