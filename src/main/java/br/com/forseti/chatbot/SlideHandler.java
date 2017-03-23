package br.com.forseti.chatbot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class SlideHandler extends TelegramLongPollingBot {
	
	private List<String> slides = new ArrayList<>();
	private int currentSlide;
	
	public SlideHandler() {
		currentSlide = -1;
		
		slides.add("*TEMA:* chatbot");
		slides.add("Quem sou eu?");
		slides.add("Para quem n�o me conhece meu nome � Diego");
		slides.add("Para quem conhece tamb�m � Diego");		
		slides.add("[Pai do Jo�o](https://drive.google.com/open?id=1D5k8cOHDfwPbZU67m1-4AQbM2SEtP_gdyg)");
		slides.add("[O Pequeno Chefinho](http://www.filmesonlinenopcgratis.net/wp-content/uploads/2016/10/11-2.jpg?x83076)");
		slides.add("*O que � um chat bot?*");
		slides.add("� um programa de computador que tenta simular um ser humano na conversa��o com as pessoas\nhttps://pt.wikipedia.org/wiki/Chatterbot");
		slides.add("S�o softwares que funcionam dentro de aplica��es de mensagens.\nhttps://medium.com/botsbrasil/o-que-%C3%A9-um-chatbot-7fa2897eac5d#.loj17a8m3");
		slides.add("[Ajuda dos universit�rios](http://www.sensacionalista.com.br/wp-content/uploads/2010/11/silviosantos-300x209.jpg)");
		slides.add("https://www.youtube.com/watch?v=XIqe7z2GUOg");
		slides.add("*Tipos de chatbot*\n\n- baseados em regras\n- baseados em intelig�ncia artificial");
		slides.add("*Baseados em regras*\n- funcionam atrav�s de comandos\n- se parece mais com um programa (software)\n- ex: essa apresenta��o");
		slides.add("*Baseados em intelig�ncia artificial*\n- tem a capacidade de entender o que foi escrito ou dito atrav�s de linguagem natural\n- tende a parecer mais com ser humano\n- alguns chats at� aprendem");
		slides.add("*Processamento de linguagem natural (PLN)*");
		slides.add("� uma sub�rea da ci�ncia da computa��o, intelig�ncia artificial e da lingu�stica que estuda os problemas da gera��o e compreens�o autom�tica de l�nguas humanas naturais");
		slides.add("*API*\n- recast.ai\n- wit.ai\n- www.motion.ai\n- dev.botframework.com\n- api.ai\n- www.ibm.com/watson/developercloud/conversation.html\n- aws.amazon.com/pt/lex\n- cloud.google.com/natural-language");
		slides.add("*Chatbots em aplicativos de mensagens*\n- Facebook\n- Telegram\n- Allo\n- Slack\n");
		slides.add("*Exemplos*\n - Allo\n- Casas Bahia\n- Poupatempo\n- Uol Not�cias\n- dsaouda/fiap-telegram-bot-digital-bank");
		slides.add("[Obrigado por seu tempo humano](http://ceticismo.net/wp-content/uploads/2014/robo-like.jpg)");		
	}
	
	public String getBotUsername() {
		return "forseti_se_vira_nos_30_bot";
	}

	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		
		if (!message.hasText()) {
			return ;
		}
		
		
		String mensagemRecebida = message.getText();
		System.out.println(mensagemRecebida);
		
		
		if (isProximo(mensagemRecebida)) {
			currentSlide++;
		} else if (isAnterior(mensagemRecebida)) {
			currentSlide--;
		} else {
			sendMessage(message, "Humano, voc� poderia passar os comandos corretamente? Tente novamente... Para voc� n�o errar use os bot�es abaixo.");
			return ;
		}
		
		
		if (currentSlide >= slides.size()) {
			//iniciar novamente
			currentSlide = -1;
			
			sendMessage(message, "Humano fazendo humanice ... voc� j� est� no final da apresenta��o");
			
		} else {
			sendMessage(message, slides.get(currentSlide));
		}
	}

	private boolean isProximo(String mensagem) {
		return mensagem.indexOf("proximo") == 0 || mensagem.indexOf("avancar") == 0 || mensagem.indexOf("next") == 0 || mensagem.indexOf("n") == 0;
	}
	
	private boolean isAnterior(String mensagem) {
		return mensagem.indexOf("anterior") == 0 || mensagem.indexOf("voltar") == 0 || mensagem.indexOf("back") == 0 || mensagem.indexOf("b") == 0;
	}

	@Override
	public String getBotToken() {
		return "<coloque o token aqui>";
	}
	
	private ReplyKeyboardMarkup getControleSlide() {
		List<KeyboardRow> commands = new ArrayList<>();
		
		if (currentSlide < slides.size()) {
			KeyboardRow avancar = new KeyboardRow();		
			avancar.add("avancar");
			commands.add(avancar);
		}
		
		if (currentSlide > 0) {
			KeyboardRow voltar = new KeyboardRow();
			voltar.add("voltar");
			commands.add(voltar);
		}
		
		ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
		replyMarkup.setResizeKeyboard(true);
		
		replyMarkup.setOneTimeKeyboad(false);
		replyMarkup.setKeyboard(commands);
		replyMarkup.setSelective(false);
		
        return replyMarkup;
    }
	
	private void sendMessage(Message message, String texto) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId());
        
        sendMessage.setText(texto);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getControleSlide());

        try {
            sendMessage(sendMessage);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
    }

}
