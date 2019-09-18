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
 * @author SIT
 */
@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_corporate_reg_rereg",
            query = "CALL CORPORATE_REG_REREG(?,:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:p15,:p16,:p17)",
            callable = true,
            resultClass = CorporateSimRegReregModel.class
    )

})
public class CorporateSimRegReregModel {

    @Id
    @Column(name = "R_V_SIM_INFO_NO")
    private Integer simNo;
    @Column(name = "V_IS_ERROR")
    private Integer isError;
    @Column(name = "V_ERR_CODE")
    private String errorCode;
    @Column(name = "V_ERR_DESC")
    private String errorDesc;

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
