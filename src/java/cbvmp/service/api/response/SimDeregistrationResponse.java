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

public class SimDeregistrationResponse extends GeneralResponse {

    @XmlElement(name = "is_error")
    Integer isError;

    @XmlElement(name = "sim_info_no")
    Integer simInfoNo;

    public SimDeregistrationResponse() {
        this.isError = null;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public Integer getSimInfoNo() {
        return simInfoNo;
    }

    public void setSimInfoNo(Integer simInfoNo) {
        this.simInfoNo = simInfoNo;
    }

}
