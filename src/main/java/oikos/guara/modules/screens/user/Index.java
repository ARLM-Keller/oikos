package oikos.guara.modules.screens.user;

import br.com.ibnetwork.guara.app.modules.GuaraModuleSupport;
import br.com.ibnetwork.guara.parameters.Parameters;
import br.com.ibnetwork.guara.rundata.Outcome;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.template.Context;

public class Index
    extends GuaraModuleSupport
{
    @Override
    public Outcome doPerform(RunData data, Context context)
        throws Exception
    {
        Parameters params = data.getParameters();
        long id = params.getLong("id", 0);
        System.out.println(id);
        if(id > 0)
        {
            //TODO: retrieve Person/Church from DB
        }
        else
        {
            
        }
        return Outcome.UNKNOWN;
    }
}
