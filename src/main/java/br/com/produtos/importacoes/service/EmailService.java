package br.com.produtos.importacoes.service;


import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	public void enviar(String nome, String emailConvidado, String senha) {
		
		
		String emailFrom = "joao.importa@gmail.com";
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(emailFrom, "joao123456"));
			email.setSSLOnConnect(true);
			
			email.setFrom(emailFrom);
			email.setSubject("Reset de senha ");
			email.setMsg("Olá " + nome + ", foi solicitado a alteração de senha. Segue a nova senha " + senha);
			email.addTo(emailConvidado);
			email.send();

		} catch (EmailException e) {
			e.printStackTrace();
		}
	}	

}
