package oikos.om;

import br.com.ibnetwork.guara.annotation.InputInfo;
import br.com.ibnetwork.guara.app.crud.BeanSupport;

public class Church
    extends BeanSupport
{
    private String name;

    private int membersCount;
    
    @InputInfo(label = "Comentários", inputType="textarea")
    private String comments;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getMembersCount()
    {
        return membersCount;
    }

    public void setMembersCount(int membersCount)
    {
        this.membersCount = membersCount;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }
 
    
}
