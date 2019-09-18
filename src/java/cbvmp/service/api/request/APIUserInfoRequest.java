/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rahat
 *
 *
 *
 */
@XmlRootElement

public class APIUserInfoRequest {

   
    @XmlElement(name = "api_user_id",required=true)
    String userID;

    @XmlElement(name = "api_old_pass",required=true)
    String oldPass;   
    
    @XmlElement(name = "api_user_pass",required=true)
    String userPass;


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    
}
