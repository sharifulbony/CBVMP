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
 * @author SIT
 */
@Entity
@NamedNativeQueries({
  
            @NamedNativeQuery(
                    name = "sp_corporate_sim_deregistration",
                    query = "CALL CORPORATE_SIM_DEREG(?,:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12)",
                    callable = true,
                    resultClass = CorporateSimDeregistrationModel.class
            )

}      
)
public class CorporateSimDeregistrationModel implements Serializable {
    @Id
    @Column(name = "SIM_INFO_NO")
    Integer simInfoNo;
    @Column(name = "V_IS_ERROR")
    Integer isError;
    @Column(name = "V_ERR_CODE")
    String errorCode;
    @Column(name = "V_ERR_DESC")
    String errorDesc;

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

    
}
