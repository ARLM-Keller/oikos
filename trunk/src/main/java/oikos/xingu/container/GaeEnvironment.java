package oikos.xingu.container;

import org.apache.commons.lang.SystemUtils;

import br.com.ibnetwork.xingu.container.impl.EnvironmentImpl;

public class GaeEnvironment
    extends EnvironmentImpl
{
    @Override
    public void initialize() 
        throws Exception
    {
        String userDir = SystemUtils.getUserDir().getAbsolutePath();
        put("user.dir", userDir);
        put("${app.root}", userDir);
        put("${pom.build.testOutputDirectory}", userDir);
    }
}
