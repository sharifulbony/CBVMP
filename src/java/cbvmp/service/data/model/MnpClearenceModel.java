/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 *
 * @author rahat
 */
@Entity
//@Table(name = "")
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_mnp_clearence",
            query = "CALL MNP_CLEARANCE(?,:p1)",
            callable = true,
            resultClass = MnpClearenceModel.class
    )

})

public class MnpClearenceModel {

    @Id
    //@GeneratedValue
    @Column(name = "V_IS_ERROR")
    private Integer isError;
    @Column(name = "V_ERR_CODE")
    private String errCode;
    @Column(name = "V_ERR_DESC")
    private String errDesc;
    @Column(name = "V_CMPO_NAME")
    private String cmpoName;

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getCmpoName() {
        return cmpoName;
    }

    public void setCmpoName(String cmpoName) {
        this.cmpoName = cmpoName;
    }

}
