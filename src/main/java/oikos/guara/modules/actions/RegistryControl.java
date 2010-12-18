package oikos.guara.modules.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import oikos.user.Person;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import br.com.ibnetwork.guara.app.modules.actions.SingleCrudControlSupport;
import br.com.ibnetwork.guara.metadata.BeanInfo;
import br.com.ibnetwork.guara.parameters.Parameters;
import br.com.ibnetwork.guara.rundata.Outcome;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.factory.Factory;
import br.com.ibnetwork.xingu.store.PersistentBean;
import br.com.ibnetwork.xingu.template.Context;
import br.com.ibnetwork.xingu.utils.ObjectUtils;
import br.com.ibnetwork.xingu.validator.ValidatorContext;
import br.com.ibnetwork.xingu.validator.ValidatorResult;
import br.com.ibnetwork.xingu.validator.validators.Validator;

public class RegistryControl
    extends SingleCrudControlSupport
{

    @Inject
    private Factory factory;
    
    public Outcome storeNew(RunData data, Context ctx) 
        throws Exception
    {
        Parameters params = data.getParameters();
        String formGroups = params.get("formGroups");
        String[] groups = formGroups.split(",");
        for (String group : groups)
        {
            BeanInfo beanInfo = createBean(params, group);
            boolean valid = validate(beanInfo);
        }
        return Outcome.UNKNOWN;
    }

    private boolean validate(BeanInfo beanInfo)
        throws Exception
    {
        Object bean = beanInfo.getBean();
        System.out.println("[" + bean + "]");
        
        ValidatorContext validatorContext = new ValidatorContext();
        boolean valid = validator.validate(bean, validatorContext);
        String[] fields = validatorContext.getFields();
        for (String fieldName : fields)
        {
            ValidatorResult result = validatorContext.get(fieldName);
            String value = beanInfo.getValueFormatted(fieldName);
            boolean fieldValid = result.isValid();
            System.out.print("\t" + (fieldValid ? StringUtils.EMPTY : "* ") + fieldName + " = " + value);

            if(!fieldValid)
            {
                Validator validator = result.getValidator(); 
                if(validator != null)
                {
                    String errorMessageId = validator.getMessageId();
                    System.out.print(" ("+errorMessageId+")");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        return valid;
    }

    private BeanInfo createBean(Parameters params, String beanClass)
        throws Exception
    {
        beanClass = StringUtils.trim(beanClass);
        Class<?> clazz = ObjectUtils.loadClass(beanClass);
        BeanInfo beanInfo = factory.create(BeanInfo.class, clazz);
        String prefix = beanInfo.getFieldPrefix();
        Map<String, String> values = params.getProperties(prefix, true); 
        Object bean = beanInfo.getBean(); 
        BeanUtils.populate(bean, values);
        return beanInfo;
    }

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
