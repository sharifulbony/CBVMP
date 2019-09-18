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
public class CorporateSimRegReregResponse extends GeneralResponse {

    @XmlElement(name = "sim_info_no")
    Integer simNo;

    @XmlElement(name = "is_error")
    Integer isError;

    @XmlElement(name = "msisdn")
    String msisdn;

    public CorporateSimRegReregResponse() {
        this.simNo = null;
        this.isError = null;
        this.msisdn = null;
    }

    public Integer getSimNo() {
        return simNo;
    }

    public void setSimNo(Integer simNo) {
        this.simNo = simNo;
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
