package oikos.user;

import br.com.ibnetwork.guara.annotation.InputInfo;
import br.com.ibnetwork.guara.app.crud.BeanSupport;
import br.com.ibnetwork.xingu.validator.ann.ValidateEmail;
import br.com.ibnetwork.xingu.validator.ann.ValidateRequired;

public class Person
    extends BeanSupport
{
    @ValidateRequired
    private String name;
    
    @InputInfo(inputType="text", label="Email")
    @ValidateEmail
    private String email;
    
    @ValidateRequired
    @InputInfo(inputType="password", label="Senha", size="12")
    private String password;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
