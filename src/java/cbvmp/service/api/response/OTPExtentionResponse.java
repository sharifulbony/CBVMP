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
public class OTPExtentionResponse extends GeneralResponse {

//    @XmlElement(name = "is_error")
//    Integer isError;
    @XmlElement(name = "otp_no")
    Integer otpNo;
    @XmlElement(name = "exp_time")
    String expirationTime;

    public OTPExtentionResponse() {
        this.otpNo = null;
        this.expirationTime = null;
    }

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

//    public Integer getIsError() {
//        return isError;
//    }
//
//    public void setIsError(Integer isError) {
//        this.isError = isError;
//    }
    

}
