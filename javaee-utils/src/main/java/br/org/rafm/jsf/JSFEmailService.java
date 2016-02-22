package br.org.rafm.jsf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@ApplicationScoped
public class JSFEmailService {

	/**
	 * 
	 * @param mailSession
	 * @param remetente
	 * @param assunto
	 * @param htmlPage
	 * @param destinatarios
	 * @throws MessagingException
	 */
	public void enviarEmail(final Session mailSession, final String remetente, final String assunto, final String htmlPageCode, final String... destinatarios) throws MessagingException {
		enviarEmailHTML(mailSession, remetente, assunto, true, htmlPageCode, destinatarios);
	}
	
	/**
	 * 
	 * @param mailSession
	 * @param remetente
	 * @param assunto
	 * @param htmlPage
	 * @param destinatarios
	 * @throws MessagingException
	 */
	public void enviarEmailHTML(final Session mailSession, final String remetente, final String assunto, final String htmlPageId, final String... destinatarios) throws MessagingException {
		enviarEmailHTML(mailSession, remetente, assunto, false, htmlPageId, destinatarios);
	}
	
	/**
	 * 
	 * @param mailSession
	 * @param remetente
	 * @param assunto
	 * @param isRendered
	 * @param htmlPage
	 * @param destinatarios
	 * @throws MessagingException
	 */
	private void enviarEmailHTML(final Session mailSession, final String remetente, final String assunto, final boolean isRendered, final String htmlPage, final String... destinatarios) throws MessagingException {
		try {
			final MimeMessage m = new MimeMessage(mailSession);
			
			m.setFrom(remetente);
	        m.setSubject(assunto);
			
			final List<InternetAddress> to = new ArrayList<>();
			for (final String address : destinatarios)
				to.add(new InternetAddress(address));
			m.setRecipients(Message.RecipientType.TO, to.toArray(new InternetAddress[to.size()]));
			
	        m.setContent(isRendered ? htmlPage : getHTMLPageContent(htmlPage), "text/html; charset=UTF-8");
	        m.setSentDate(new Date());
	        
	        Transport.send(m);
        } catch (IOException e) {
        	throw new IllegalArgumentException("Arquivo de e-mail/HTML não encontrado", e);
		} catch (AddressException e) {
        	throw new IllegalArgumentException("Endereço(s) de e-mail do(s) destinatário(s) inválido(s)", e);
		}
	}
	
	private String getHTMLPageContent(final String htmlPageId) throws IOException {
		final StringBuilder sb = new StringBuilder();
		
		try (final BufferedInputStream bis = new BufferedInputStream(FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(htmlPageId+".html"))) {
			final byte[] buffer = new byte[1024];
			
			int nBytesRead;
			while ((nBytesRead = bis.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, nBytesRead));
			}
		}
		
		return sb.toString();
	}
}
