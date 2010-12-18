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
            Object bean = createBean(params, group);
            ValidatorContext validatorContext = new ValidatorContext();
            boolean valid = validator.validate(bean, validatorContext);
            System.out.println(bean + " ? " + valid);
            if(!valid)
            {
                String[] fields = validatorContext.getFields();
                for (String field : fields)
                {
                    ValidatorResult result = validatorContext.get(field);
                    Validator validator = result.getValidator(); 
                    String errorMessage = validator.getMessage();
                    String errorMessageId = validator.getMessageId();
                    
                    System.out.println(field + " = " + result.isValid());
                    System.out.println(errorMessage+" OR "+errorMessageId);
                    System.out.println("");
                }
            }
        }
        return Outcome.UNKNOWN;
    }

    private Object createBean(Parameters params, String beanClass)
        throws Exception
    {
        beanClass = StringUtils.trim(beanClass);
        Class<?> clazz = ObjectUtils.loadClass(beanClass);
        BeanInfo beanInfo = factory.create(BeanInfo.class, clazz);
        String prefix = beanInfo.getFieldPrefix();
        Map<String, String> values = params.getProperties(prefix, true); 
        Object bean = beanInfo.getBean(); 
        BeanUtils.populate(bean, values);
        return bean;
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
