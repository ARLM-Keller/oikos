package oikos.email;


public class ConfirmEmail
    extends EmailSupport
    implements Email
{
    private final String code;

    public ConfirmEmail(String name, String email, String code)
    {
        super(name, email);
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    @Override
    public String getTemplate()
    {
        return "email/register/Confirm";
    }

    public String getName()
    {
        return name;
    }
    
    public String getEmail()
    {
        return email;
    }
}
