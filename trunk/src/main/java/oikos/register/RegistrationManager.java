package oikos.register;


public interface RegistrationManager
{
    Registration register(String name, String email)
        throws Exception;
    
    Registration byCode(String code);
    
    Registration byEmail(String email);

    void store(Registration registration)
        throws Exception;
}
