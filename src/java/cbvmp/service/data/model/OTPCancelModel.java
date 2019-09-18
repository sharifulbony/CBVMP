/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;
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


@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_cancel_otp",
            query = "CALL CANCEL_OTP(?,:p1,:p2,:p3)",
            callable = true,
            resultClass = OTPCancelModel.class
    )

})

public class OTPCancelModel implements Serializable {

    @Id    
    @Column(name = "V_IS_ERROR")
    Integer Error;
    @Column(name = "V_ERR_CODE")
    String errorCode;
    @Column(name = "V_ERR_DESC")
    String errorDesc;
   

   

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
