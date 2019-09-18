/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;
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
            name = "sp_bulk_deregistration",
            query = "CALL BULK_DEREG(?,:p1,:p2,:p3)",
            callable = true,
            resultClass = BulkDeregistrationModel.class
    )

})

public class BulkDeregistrationModel {

    @Id
    @Column(name = "V_IS_ERROR")
    private Integer isError;
    @Column(name = "V_ERR_CODE")
    private String errorCode;
    @Column(name = "SIM_INFO_NO")
    private Integer simInfoNo;
    @Column(name = "V_ERR_DESC")
    private String errorDesc;

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

    public Integer getSimInfoNo() {
        return simInfoNo;
    }

    public void setSimInfoNo(Integer simInfoNo) {
        this.simInfoNo = simInfoNo;
    }

   

}
