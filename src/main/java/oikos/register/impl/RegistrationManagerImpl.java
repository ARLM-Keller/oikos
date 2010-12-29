package oikos.register.impl;

import java.util.List;

import oikos.email.ConfirmEmail;
import oikos.email.EmailManager;
import oikos.register.Registration;
import oikos.register.RegistrationManager;

import org.apache.avalon.framework.activity.Initializable;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.store.ObjectStore;

public class RegistrationManagerImpl
    implements RegistrationManager, Initializable
{
    @Inject
    private EmailManager emailManager;
    
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
    public void store(Registration registration)
        throws Exception
    {
        store.store(registration);
    }

    @Override
    public Registration register(String name, String email)
        throws Exception
    {
        String code = nextAvailableCode();
        Registration registration = new RegistrationImpl(email, code);
        store.store(registration);
        registrations.add(registration);
        logger.info("New registration {} for {}", code, email);
        try
        {
            emailManager.sendMessage(new ConfirmEmail(name, registration.getEmail(), registration.getCode()));
        }
        catch (Exception t)
        {
            logger.error("Error sending confirmation email to: "+email, t);
            throw t;
        }
        return registration;
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
