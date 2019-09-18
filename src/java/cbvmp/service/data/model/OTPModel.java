/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;

//import java.io.Serializable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 *
 * @author rahat
 */
/**
 *
 * @author rahat
 */
//@SecondaryTable(name = "products")
@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_generate_otp",
            query = "CALL GENERATE_OTP(?,:p1,:p2,:p3,:p4,:p5)",
            callable = true,
            resultClass = OTPModel.class
    )

})

public class OTPModel implements Serializable {

    @Id
    @Column(name = "V_OTP_NO")
    Integer otpNo;
    @Column(name = "V_EXP_TIME")
    String expireTime;
    @Column(name = "V_ERR_CODE")
    String errorCode;
    @Column(name = "V_ERR_DESC")
    String errorDesc;
    @Column(name = "V_IS_ERROR")
    Integer Error;

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Integer getError() {
        return Error;
    }

    public void setError(Integer Error) {
        this.Error = Error;
    }

}
