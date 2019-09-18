/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rahat
 */

@XmlRootElement
public class SimRegistrationResponse extends GeneralResponse {

    @XmlElement(name = "is_error")
    Integer isError;
    
    @XmlElement(name = "sim_info_no")
    Integer simNo;
    

    public SimRegistrationResponse() {
        this.simNo=null;
        this.isError = null;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public Integer getSimNo() {
        return simNo;
    }

    public void setSimNo(Integer simNo) {
        this.simNo = simNo;
    }
    
    
}
