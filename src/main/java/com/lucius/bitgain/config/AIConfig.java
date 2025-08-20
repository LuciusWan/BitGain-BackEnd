package com.lucius.bitgain.config;

import com.lucius.bitgain.constant.AIConstant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AIConfig {
    @Bean
    public ChatClient bitGainChatClient(OpenAiChatModel model) {
        return ChatClient.builder(model)
                .defaultSystem(AIConstant.MAIN)
                .defaultAdvisors(
                        new SimpleLoggerAdvisor())
                .build();
    }
}
