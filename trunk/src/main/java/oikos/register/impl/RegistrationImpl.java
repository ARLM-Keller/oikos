package oikos.register.impl;

import java.util.Date;

import br.com.ibnetwork.guara.app.crud.BeanSupport;

import oikos.register.Registration;

public class RegistrationImpl
    extends BeanSupport
    implements Registration
{

    private String email;
    
    private String code;

    private long created;
    
    private transient Date createdAsDate;
    
    private long confirmed;
    
    private transient Date confirmedAsDate;

    public RegistrationImpl(String email, String code)
    {
        this.email = email;
        this.code = code;
        this.created = System.currentTimeMillis();
    }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public Date getCreationDate()
    {
        if(createdAsDate == null)
        {
            createdAsDate = new Date(created);
        }
        return createdAsDate;
    }

    @Override
    public Date getConfirmationDate()
    {
        if(confirmedAsDate == null && confirmed > 0)
        {
            confirmedAsDate = new Date(confirmed);
        }
        return confirmedAsDate;
    }

    @Override
    public void confirm()
    {
        confirmed = System.currentTimeMillis();
    }
}
