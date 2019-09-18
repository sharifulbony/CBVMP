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
public class MnpRegistrationResponse extends GeneralResponse {

    @XmlElement(name = "is_error")
    Integer isError;
    
    @XmlElement(name = "mnp_sim_no")
    Integer mnpNo;
    

    public MnpRegistrationResponse() {
        this.mnpNo=null;
        this.isError = null;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public Integer getMnpNo() {
        return mnpNo;
    }

    public void setMnpNo(Integer mnpNo) {
        this.mnpNo = mnpNo;
    }

    
}
