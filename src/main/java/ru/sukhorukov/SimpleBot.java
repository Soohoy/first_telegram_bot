package ru.sukhorukov;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import ru.sukhorukov.service.ServiceBot;

public class SimpleBot extends TelegramLongPollingBot {
    static ServiceBot service;
    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("app-context.xml");
        ctx.refresh();
        service = ctx.getBean("serviceBot", ServiceBot.class);
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try{
            botsApi.registerBot(new SimpleBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if(message != null && message.hasText()){
            if("/start".equals(message.getText())){

                service.saveId(message.getChatId());

            }else if("/write".equals(message.getText().split(" ")[0])){

                long id = service.writeMessage(message.getChatId(), message.getText().substring(7));
                sendMsg(message, "заметка <" + id + "> сохранена");

            }else if("/read_last".equals(message.getText())){

                String last_message = service.getLastMessage(message.getChatId());
                sendMsg(message, last_message);

            }else if("/read".equals(message.getText().split(" ")[0])){
                String[] strings = message.getText().split(" ");
                if(strings.length > 1) {
                    String id = strings[1];
                    try {
                        String text = service.getMessageById(message.getChatId(), Long.valueOf(id));
                        sendMsg(message, text);
                    }catch (NumberFormatException e){
                        sendMsg(message, "Укажите <id> после команды <read>");
                    }
                }else {
                    sendMsg(message, "Укажите <id> после команды <read>");
                }

            }else if("/read_all".equals(message.getText())){

                String text = service.getAllMessages(message.getChatId());
                sendMsg(message, text);

            }else {
                StringBuilder text = new StringBuilder();
                text.append("Список команд:");
                text.append('\n');
                text.append("/write");
                text.append('\n');
                text.append("/read");
                text.append('\n');
                text.append("/read_last");
                text.append('\n');
                text.append("/read_all");
                sendMsg(message, text.toString());
            }
        }
    }

    public String getBotUsername() {
        return "IeshuaBot";
    }

    public String getBotToken() {
        return "532824047:AAG6mBVVCzMAjuI_cH0BErno51yU3G0EVy8";
    }

    @SuppressWarnings("deprecation")
    private void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try{
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
