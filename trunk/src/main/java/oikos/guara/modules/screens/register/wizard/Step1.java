package oikos.guara.modules.screens.register.wizard;

import javax.servlet.http.HttpSession;

import oikos.register.Registration;
import br.com.ibnetwork.guara.message.SystemMessageBroker;
import br.com.ibnetwork.guara.modules.ModuleSupport;
import br.com.ibnetwork.guara.rundata.Outcome;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.template.Context;

public class Step1
    extends ModuleSupport
{
    @Inject
    private SystemMessageBroker messages;
    
    @Override
    public Outcome doPerform(RunData data, Context ctx)
        throws Exception
    {
        HttpSession session = data.getRequest().getSession();
        Registration registration = (Registration) session.getAttribute("registration");
        if(registration == null || !registration.isConfirmed())
        {
            //timeout or user is trying to access this page directly
            data.getPageInfo().setTemplate(null);
            data.setMessage(messages.getSystemMessage("registerWarn"));
            return Outcome.error(this);
        }
        return Outcome.success(this);
    }
}
