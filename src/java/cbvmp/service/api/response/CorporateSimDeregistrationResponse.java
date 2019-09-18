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
 * @author SIT
 */
@XmlRootElement
public class CorporateSimDeregistrationResponse extends GeneralResponse {

    @XmlElement(name = "sim_info_no")
    Integer simInfoNo;
    @XmlElement(name = "is_error")
    Integer isError;
    @XmlElement(name = "msisdn")
    String msisdn;
    
    public CorporateSimDeregistrationResponse()
    {
        this.simInfoNo = null;
        this.isError = null;
        this.msisdn = null;
    }

    public Integer getSimInfoNo() {
        return simInfoNo;
    }

    public void setSimInfoNo(Integer simInfoNo) {
        this.simInfoNo = simInfoNo;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

}
