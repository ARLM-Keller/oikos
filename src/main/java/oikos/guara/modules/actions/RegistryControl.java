package oikos.guara.modules.actions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import oikos.user.Person;

import org.apache.commons.beanutils.BeanUtils;

import br.com.ibnetwork.guara.app.modules.actions.SingleCrudControlSupport;
import br.com.ibnetwork.guara.parameters.Parameters;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.store.PersistentBean;

public class RegistryControl
    extends SingleCrudControlSupport
{

    @Override
    protected PersistentBean createFromRequest(RunData data) 
        throws Exception
    {
        Map<String, String> values = getProperties(data, "person.", true);
        Person person = factory.create(Person.class);
        BeanUtils.populate(person, values);
        return person;
    }

    //TODO: move this code to Parameters
    private Map<String, String> getProperties(RunData data, String prefix, boolean stripPrefixFromKey)
    {
        int length = prefix.length();
        Map<String, String> values = new HashMap<String, String>();
        Parameters pp = data.getParameters(); 
        Set<String> keys = pp.keySet();
        for (String key : keys)
        {
            if(key.startsWith(prefix))
            {
                String value = pp.get(key);
                String propertyName = key;
                if(stripPrefixFromKey)
                {
                    propertyName = key.substring(length);
                }
                values.put(propertyName, value);
            }
        }
        return values;
    }

    @Override
    protected Class getBeanClass()
    {
        return null;
    }
}
