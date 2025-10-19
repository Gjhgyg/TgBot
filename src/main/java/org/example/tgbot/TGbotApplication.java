package org.example.tgbot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class TGbotApplication implements CommandLineRunner {

    @Autowired
    private MyBot tgbotBot;

    public static void main(String[] args) {
        SpringApplication.run(TGbotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(tgbotBot);
        System.out.println("Бот педик запущен!");
    }
}