package br.com.forseti.chatbot;

import java.util.logging.Level;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.logging.BotLogger;

public class Main {
	public static void main(String[] args) throws TelegramApiRequestException {
		System.out.println("iniciou");
		
		BotLogger.setLevel(Level.ALL);
		ApiContextInitializer.init();
		
		TelegramBotsApi bot = new TelegramBotsApi();
		bot.registerBot(new SlideHandler());
	}
}
