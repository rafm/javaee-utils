package br.org.rafm.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public abstract class AbstractMailService {

	/**
	 * @param mailSession
	 * @param from
	 * @param subject
	 * @param htmlPageContent
	 * @param toGroup
	 * @throws MessagingException 
	 */
	protected void sendHTMLMail(final Session mailSession, final String from, final String subject, final String htmlPageContent, final String... toGroup) throws MessagingException {
		final MimeMessage m = new MimeMessage(mailSession);
		
		m.setFrom(from);
        m.setSubject(subject);
		
		final List<InternetAddress> to = new ArrayList<>();
		for (final String address : toGroup)
			to.add(new InternetAddress(address));
		m.setRecipients(Message.RecipientType.TO, to.toArray(new InternetAddress[to.size()]));
		
        m.setContent(htmlPageContent, "text/html; charset=UTF-8");
        m.setSentDate(new Date());
        
        Transport.send(m);
	}
}
