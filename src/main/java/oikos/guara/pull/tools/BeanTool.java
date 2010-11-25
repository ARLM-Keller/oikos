package oikos.guara.pull.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avalon.framework.configuration.Configurable;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;

import br.com.ibnetwork.guara.metadata.BeanInfo;
import br.com.ibnetwork.guara.pull.impl.ApplicationToolSupport;
import br.com.ibnetwork.xingu.container.Inject;
import br.com.ibnetwork.xingu.factory.Factory;
import br.com.ibnetwork.xingu.store.ObjectStore;
import br.com.ibnetwork.xingu.store.PersistentBean;
import br.com.ibnetwork.xingu.utils.ObjectUtils;
import br.com.ibnetwork.xingu.validator.BeanValidator;
import br.com.ibnetwork.xingu.validator.ValidatorContext;

public class BeanTool
    extends ApplicationToolSupport
    implements Configurable
{
	@Inject
	protected ObjectStore store;
	
	@Inject
	protected Factory factory;
	
	@Inject
	protected BeanValidator validator;
	
	private List<String> packageNames;
	
	private Map<String, BeanInfo> metaCache = new HashMap<String, BeanInfo>(20);;
	
	private Map<String, Class<? extends PersistentBean>> classCache = new HashMap<String, Class<? extends PersistentBean>>(20);

	public void configure(Configuration configuration)
    	throws ConfigurationException
    {
		Configuration[] conf = configuration.getChild("packages").getChildren("package");
		packageNames = new ArrayList<String>(conf.length);
		for (int i = 0; i < conf.length; i++)
        {
			String packageName = conf[i].getAttribute("name");
			packageNames.add(packageName);
        }
    }

	public Object getById(String className, long id)
		throws ClassNotFoundException
	{
		Class<? extends PersistentBean> clazz = getBeanClass(className);
		return store.getById(clazz, id);
	}

	public List<? extends PersistentBean> getAll(String className)
		throws ClassNotFoundException
	{
		Class<? extends PersistentBean> clazz = getBeanClass(className);
		return store.getAll(clazz);
	}
	
	public BeanInfo getBeanInfo(Object bean)
	{
		BeanInfo info = factory.create(BeanInfo.class, bean);
		return info;
	}

	public BeanInfo getBeanInfo(String className)
		throws ClassNotFoundException
	{
		BeanInfo metadata = metaCache.get(className);
		if(metadata == null)
		{
			Class<?> clazz = getBeanClass(className);
			metadata = (BeanInfo) factory.create(BeanInfo.class, new Object[]{clazz});
			metaCache.put(className, metadata);
		}
		return metadata;
	}
	
	public ValidatorContext validate(Object bean) 
		throws Exception
	{
    	ValidatorContext ctx = new ValidatorContext();
    	validator.validate(bean, ctx);
    	return ctx;
	}

	@SuppressWarnings("unchecked")
    private Class<? extends PersistentBean> getBeanClass(String className) 
		throws ClassNotFoundException
	{
	    Class<? extends PersistentBean> clazz = classCache.get(className);
		if(clazz != null)
		{
			return clazz;
		}
		clazz = (Class<? extends PersistentBean>) ObjectUtils.loadClass(className);
        classCache.put(className, clazz);
        return clazz;
	}
}