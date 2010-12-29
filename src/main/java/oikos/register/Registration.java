package oikos.register;

import java.util.Date;

import br.com.ibnetwork.xingu.store.PersistentBean;

public interface Registration
    extends PersistentBean
{
    String getCode();
    
    String getEmail();
    
    Date getCreationDate();
    
    Date getConfirmationDate();
    
    void confirm();

    boolean isConfirmed();
}
