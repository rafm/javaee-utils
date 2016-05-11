package br.org.rafm.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class AbstractMailService {

	/**
	 * @param mailSession
	 * @param subject
	 * @param htmlPageContent
	 * @param addresses
	 * @throws MessagingException 
	 */
	protected void sendHTMLMail(final Session mailSession, final String subject, final String htmlPageContent, final String... addresses) throws MessagingException {
		sendHTMLMail(mailSession.getProperty("mail.smtp.user"), mailSession, subject, htmlPageContent, addresses);
	}

	/**
	 * @param from
	 * @param mailSession
	 * @param subject
	 * @param htmlPageContent
	 * @param addresses
	 * @throws MessagingException 
	 */
	protected void sendHTMLMail(final String from, final Session mailSession, final String subject, final String htmlPageContent, final String... addresses) throws MessagingException {
		final MimeMessage m = new MimeMessage(mailSession);
		
		m.setFrom(from);
        m.setReplyTo(new InternetAddress[] {new InternetAddress(from)});
        m.setSubject(subject);
		
		for (final String address : addresses)
			m.addRecipients(Message.RecipientType.TO, address);
		
        m.setContent(htmlPageContent, "text/html; charset=UTF-8");
        m.setSentDate(new Date());
        
        Transport.send(m);
	}
}
