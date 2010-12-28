package oikos.guara.modules.actions;

import java.util.Map;

import oikos.register.Registration;
import oikos.register.RegistrationManager;
import oikos.user.Person;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.ibnetwork.guara.app.modules.GuaraModuleSupport;
import br.com.ibnetwork.guara.metadata.BeanInfo;
import br.com.ibnetwork.guara.parameters.Parameters;
import br.com.ibnetwork.guara.rundata.Outcome;
import br.com.ibnetwork.guara.rundata.RunData;
import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.factory.Factory;
import br.com.ibnetwork.xingu.lang.NotImplementedYet;
import br.com.ibnetwork.xingu.template.Context;
import br.com.ibnetwork.xingu.validator.ValidatorContext;
import br.com.ibnetwork.xingu.validator.ValidatorResult;
import br.com.ibnetwork.xingu.validator.validators.Validator;

public class RegistryControl
    extends GuaraModuleSupport
{
    @Inject
    private Factory factory;
    
    @Inject
    private RegistrationManager registrationManager;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public Outcome register(RunData data, Context ctx) 
        throws Exception
    {
        Parameters params = data.getParameters();
        BeanInfo beanInfo = createBean(params, Person.class.getName());
        
        //TODO: validate person
        Person person = (Person) beanInfo.getBean();
        String email = person.getEmail();
        Registration registration = registrationManager.byEmail(email);
        if(registration != null)
        {
            logger.info("Can't register '{}' again", email);
            throw new NotImplementedYet();
        }
        registration = registrationManager.register(person);
        return Outcome.UNKNOWN;
    }
    
    public Outcome store(RunData data, Context ctx) 
        throws Exception
    {
        Parameters params = data.getParameters();
        String formGroups = params.get("formGroups");
        String[] groups = formGroups.split(",");
        boolean valid = true;
        for (String group : groups)
        {
            BeanInfo beanInfo = createBean(params, group);
            boolean beanValid = validate(beanInfo);
            valid = valid && beanValid;
        }
        if(valid)
        {
            //TODO: store objects
            return Outcome.success(this, "store");
        }
        return Outcome.error(this, "store");
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
        Object bean = factory.create(beanClass);
        BeanInfo beanInfo = new BeanInfo(bean);
        String prefix = beanInfo.getFieldPrefix();
        Map<String, String> values = params.getProperties(prefix, true); 
        BeanUtils.populate(bean, values);
        return beanInfo;
    }
}
