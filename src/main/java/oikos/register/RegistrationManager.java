package oikos.register;

import oikos.user.Person;

public interface RegistrationManager
{
    Registration register(Person person);
    
    Registration byCode(String code);
    
    Registration byEmail(String email);
}
