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
 * @author tanbir
 */

@XmlRootElement
public class SimTransferResponse extends GeneralResponse{
    
    @XmlElement(name="sim_info_no")
    Integer simInfoNo;
    
    @XmlElement(name="is_error")
    Integer isError;

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
    
    
    
}
