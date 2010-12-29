package oikos.email.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.internet.MimeMessage;

import oikos.email.Email;
import oikos.email.EmailManager;

import org.apache.avalon.framework.activity.Startable;
import org.apache.avalon.framework.configuration.Configurable;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.lang.Sys;
import br.com.ibnetwork.xingu.messaging.MessageDispatcher;
import br.com.ibnetwork.xingu.template.Context;
import br.com.ibnetwork.xingu.template.TemplateEngine;

public class EmailManagerImpl
    implements EmailManager, Runnable, Configurable, Startable
{
    @Inject
    private MessageDispatcher messageDispatcher;
    
    @Inject
    private TemplateEngine templateEngine;

    private Thread worker;
    
    private List<Message> queue = new ArrayList<Message>();
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    private boolean deliveryEnabled;    
    
    @Override
    public void configure(Configuration conf)
        throws ConfigurationException
    {
        this.deliveryEnabled = conf.getChild("delivery").getAttributeAsBoolean("enabled", true);
    }

    @Override
    public void start()
        throws Exception
    {
        worker = Sys.startDaemon(this, "EmailDispatcherThread");
    }

    @Override
    public void stop()
        throws Exception
    {
        worker.interrupt();
    }

    @Override
    public void run()
    {
        while(true)
        {
            synchronized (queue)
            {
                if(queue.size() > 0)
                {
                    Message message = queue.remove(0);
                    send(message);
                }
                else
                {
                    Sys.waitWithoutInterruptions(queue);
                }
            }
        }
    }

    private void send(Message message)
    {
        if(!deliveryEnabled)
        {
            logger.warn("Email delivery is not enabled!");
            return;
        }
        try
        {
            messageDispatcher.sendMessage(message);
        }
        catch (Throwable t)
        {
            logger.error("Error sending message ", t);
        }
    }

    @Override
    public void sendMessage(Email email)
        throws Exception
    {
        Message message = createMessageFrom(email);
        push(message);
    }

    private void push(Message message)
    {
        synchronized (queue)
        {
            queue.add(message);
            queue.notify();
        }
    }

    private Message createMessageFrom(Email email)
        throws Exception
    {
        Address to = email.getTo();
        Address from = email.getFrom();
        String subject = email.getSubject();
        Message message = new MimeMessage(messageDispatcher.getSession());
        message.setFrom(from);
        message.setRecipient(RecipientType.TO, to);
        message.setSubject(subject);

        String html = renderMessageContent(email);
        message.setContent(html, "text/html; charset=utf-8");
        return message;
    }

    private String renderMessageContent(Email email)
    {
        Context context = templateEngine.createContext();
        context.put("email", email);
        StringWriter writer = new StringWriter();
        String template = email.getTemplate();
        templateEngine.merge(template, context, writer);
        StringBuffer buffer = writer.getBuffer();
        String html = buffer.toString();
        return html;
    }
}
