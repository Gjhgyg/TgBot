package org.example.tgbot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class MyBot extends TelegramLongPollingBot {

    @Autowired
    private UserRepository userRepository;

    // 👇 ID человека, на которого бот будет реагировать
    private final Long targetUserId = 2017827907L; // замени на нужный userId

    @Override
    public String getBotUsername() {
        return "ped1kBot";
    }

    @Override
    public String getBotToken() {
        return "8446642573:AAHRjzLOkWoYoq6pe3VvZ-HyBgcvHEBgX_4";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Long chatId = update.getMessage().getChatId();
            Long userId = update.getMessage().getFrom().getId();
            String username = update.getMessage().getFrom().getUserName();
            String firstName = update.getMessage().getFrom().getFirstName();
            String incomingMessage = update.getMessage().getText().toLowerCase();

            // ❌ Если пишет не нужный человек — выходим
            if (!userId.equals(targetUserId)) {
                return;
            }

            // 🎲 70% шанс, что бот ответит
            Random random = new Random();
            int chance = random.nextInt(100);
            if (chance >= 60) { // 30% случаев — молчит
                System.out.println("🤫 я не буду отвечать на эту хуйню");
                return;
            }

            // ✅ Подготовленные ответы
            Map<String, String> replyMap = new HashMap<>();
            replyMap.put("педик привет", "Привет! 😎");
            replyMap.put("педик как дела", "Нормально, у тебя как?");
            replyMap.put("педик чем занимаешься", "еще не придумал 😅");
            replyMap.put("педик пока", "Пока, лох👋");

            // ✅ Случайные ответы (если нет заготовки)
            List<String> randomReplies = Arrays.asList(
                    "Рома еблан 😏",
                    "Роман жирный уебан",
                    "Ромчик ай сын ИГИЛА",
                    "бля Рома заебал😎",
                    "Рома дай денег",
                    "да завали ебало",
                    "хз не придумал😳"
            );

            boolean responded = false;

            // Проверяем — есть ли совпадение с заготовкой
            for (Map.Entry<String, String> entry : replyMap.entrySet()) {
                if (incomingMessage.contains(entry.getKey())) {
                    sendMessage(chatId, entry.getValue());
                    responded = true;
                    break;
                }
            }

            // 🧠 Если нет заготовки — выбираем случайный ответ
            if (!responded) {
                String randomReply = randomReplies.get(random.nextInt(randomReplies.size()));
                sendMessage(chatId, randomReply);
            }

            // 🗂 Добавляем пользователя в БД, если его нет
            if (userRepository.findByChatId(chatId).isEmpty()) {
                userRepository.save(new User(chatId, username, firstName));
                sendText(chatId, "Привет! Я Сглыпа, теперь я с вами 😎");
            }
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendText(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        System.out.println("Отправка в чат " + chatId + ": " + text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}