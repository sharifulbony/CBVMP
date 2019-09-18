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
 */

@XmlRootElement
public class OTPExtentionRequest {

    @XmlElement(name = "otp_no", required = true)
    Integer otpNo;
    @XmlElement(name = "doc_type_no", required = true)
    Integer typeNo;
    @XmlElement(name = "doc_id", required = true)
    String userNID;
    @XmlElement(name = "purpose_no", required = true)
    Integer purposeNo;

    public Integer getTypeNo() {
        return typeNo;
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

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }

}
