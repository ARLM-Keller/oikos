package oikos.email;

import java.io.UnsupportedEncodingException;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

public abstract class EmailSupport
    implements Email
{
    protected final String email;
    
    protected final String name;

    public EmailSupport(String name, String email)
    {
        this.name = name;
        this.email = email;
    }

    @Override
    public String getSubject()
    {
        return "[Oikos] Confirme seu email cadastrado";
    }

    @Override
    public Address getTo()
    {
        return toAddress(name, email);
    }

    @Override
    public Address getFrom()
    {
        return toAddress("Oikos Team", "oikos@oikos.com.br");
    }

    private Address toAddress(String name, String address)
    {
        InternetAddress result = null;
        try
        {
            result = new InternetAddress(address, name);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}
