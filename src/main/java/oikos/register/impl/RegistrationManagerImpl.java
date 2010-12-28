package oikos.register.impl;

import java.util.List;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import oikos.register.Registration;
import oikos.register.RegistrationManager;
import oikos.user.Person;

import org.apache.avalon.framework.activity.Initializable;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.messaging.MessageDispatcher;
import br.com.ibnetwork.xingu.store.ObjectStore;

public class RegistrationManagerImpl
    implements RegistrationManager, Initializable
{
    @Inject
    private MessageDispatcher messageDispatcher;
    
    @Inject
    private ObjectStore store;
    
    private List<Registration> registrations;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Override
    public void initialize()
        throws Exception
    {
        registrations = store.getAll(Registration.class);
    }

    @Override
    public Registration register(Person person)
    {
        String email = person.getEmail();
        String code = nextAvailableCode();
        Registration registration = new RegistrationImpl(email, code);
        store.store(registration);
        registrations.add(registration);
        logger.info("New registration {} for {}", code, email);
        try
        {
            Message message = createConfirmationMessage(registration, person.getName());
            //messageDispatcher.sendMessage(message);
        }
        catch (Throwable t)
        {
            logger.error("Error sending confirmation email to: "+email, t);
        }
        return registration;
    }

    private Message createConfirmationMessage(Registration registration, String name)
        throws Exception
    {
        Message message = new MimeMessage(messageDispatcher.getSession());
        message.setFrom(new InternetAddress("cadastro@kidux.net", "Oikos"));
        message.setRecipient(RecipientType.TO, new InternetAddress(registration.getEmail()));
        message.setSubject("Confirme seu email no Oikos");
        //message.setContent(messageContent(account, url), "text/html; charset=utf-8");
        return message;
    }

    private String nextAvailableCode()
    {
        String result = RandomStringUtils.randomAlphanumeric(8);
        for(Registration registration : registrations)
        {
            String code = registration.getCode(); 
            if(code.equals(result))
            {
                return nextAvailableCode();
            }
        }
        return result;
    }

    @Override
    public Registration byCode(String wanted)
    {
        for(Registration registration : registrations)
        {
            String code = registration.getCode(); 
            if(code.equals(wanted))
            {
                return registration;
            }
        }
        return null;
    }

    @Override
    public Registration byEmail(String wanted)
    {
        for(Registration registration : registrations)
        {
            String email = registration.getEmail(); 
            if(email.equals(wanted))
            {
                return registration;
            }
        }
        return null;
    }
}
