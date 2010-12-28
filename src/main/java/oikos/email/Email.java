package oikos.email;

import javax.mail.Address;

public interface Email
{
    Address getTo();
    
    Address getFrom();
    
    String getSubject();

    String getTemplate();
}
