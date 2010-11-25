package oikos.guara.modules.actions;

import java.util.Map;

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
        Parameters params = data.getParameters();
        Map<String, String> values = params.getProperties("person.", true); 
        Person person = factory.create(Person.class);
        BeanUtils.populate(person, values);
        return person;
    }

    //TODO: move this code to Parameters

    @Override
    protected Class getBeanClass()
    {
        return null;
    }
}
