package oikos.guara.modules.actions;

import java.util.Set;

import br.com.ibnetwork.guara.modules.ModuleSupport;
import br.com.ibnetwork.guara.parameters.Parameters;
import br.com.ibnetwork.guara.rundata.Outcome;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.template.Context;

public class UserControl
    extends ModuleSupport
{
    public Outcome doStore(RunData data, Context context) 
        throws Exception
    {
        Parameters p = data.getParameters();
        Set keys = p.keySet();
        for (Object key : keys)
        {
            String value = p.get((String) key);
            System.out.println(key + " = " + value);
        }
        data.getPageInfo().setTemplate("user.Index");
        return Outcome.UNKNOWN;
    }

}
