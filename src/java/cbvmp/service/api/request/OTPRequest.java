
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
 * @author tanbir
 */
@XmlRootElement
public class OTPRequest {
    @XmlElement(name = "doc_type_no", required = true)
    Integer typeNo;
    @XmlElement(name = "doc_id", required = true)
    String userNID;
    @XmlElement(name = "purpose_no", required = true)
    Integer purposeNo;
    
    @XmlElement(name = "sim_catagory", required = true)
    Integer simCatagory;

    public Integer getTypeNo() {
        return typeNo;
    }

    public Integer getSimCatagory() {
        return simCatagory;
    }

    public void setSimCatagory(Integer simCatagory) {
        this.simCatagory = simCatagory;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }    
    
    public String getUserNID() {
        return userNID;
    }

    public void setUserNID(String userNID) {
        this.userNID = userNID;
    }

    public Integer getPurposeNo() {
        return purposeNo;
    }

    public void setPurposeNo(Integer purposeNo) {
        this.purposeNo = purposeNo;
    }

}
