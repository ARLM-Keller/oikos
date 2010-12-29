package oikos;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

public class RegistrationTest
    extends SeleniumTestSupport
{
    @Test
    public void testUserRegistration()
        throws Exception
    {
        browser.open("/Index");
        waitPage();
        
        browser.click("link=Cadastro");
        waitPage();
        
        //Start
        String email = "leandro.saad+"+RandomStringUtils.randomAlphanumeric(4)+"@gmail.com";
        browser.type("id=person.email", email);
        browser.click("name=btnSend");
        waitPage();
        
        //Wait for confirmation code
        browser.click("id=linkWithconfirmationCode");
        browser.waitForPageToLoad(TIME_OUT);
        
        //Wizard Step 1
        browser.type("id=person.name", "Leandro Rodrigo Saad Cruz");
        browser.type("id=person.password", "ezequiel33");
        browser.type("id=passwordConfirmation", "ezequiel33");
        browser.click("name=btnSend");
        waitPage();
        
        System.out.println("DONE");
    }
}
