package oikos.register;

import oikos.user.Person;
import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.lang.NotImplementedYet;
import br.com.ibnetwork.xingu.messaging.MessageDispatcher;

public class RegistrationManagerImpl
    implements RegistrationManager
{
    @Inject
    private MessageDispatcher messageDispatcher;
    
    @Override
    public Registration register(Person person)
    {
        //TODO: send confirmation email
        //TODO: store registration for later use
        
        throw new NotImplementedYet("TODO");
    }

}
