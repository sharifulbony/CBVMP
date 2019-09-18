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
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_change_password_req",
            query = "CALL SEC_API_CHANGE_PASS(?,:p1,:p2,:p3,:p4)",
            callable = true,
            resultClass = APIUserInfoModel.class
    )

})

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class APIUserInfoModel {

    @Id
    //@GeneratedValue

    @Column(name = "V_IS_ERROR")
    Integer isError;
    @Column(name = "V_ERR_CODE")
    String errCode;
    @Column(name = "V_ERR_DESC")
    String errDesc;

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
    
}
