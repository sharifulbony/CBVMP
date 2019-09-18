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
            name = "sp_mnp_reg",
            query = "CALL MNP_REG(?,:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9,:p10,:p11,:p12,:p13,:p14,:p15,:p16,:p17)",
            callable = true,
            resultClass = MnpRegistrationModel.class
    )

})

public class MnpRegistrationModel {
    

    @Id
    //@GeneratedValue
    @Column(name="V_IS_ERROR")
    private Integer isError;
    @Column(name="V_ERR_CODE")
    private String errCode;
    @Column(name="V_ERR_DESC")
    private String errDesc;
    @Column(name="R_V_MNP_NO")
    private Integer mnpNo;
    

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

    public Integer getMnpNo() {
        return mnpNo;
    }

    public void setMnpNo(Integer mnpNo) {
        this.mnpNo = mnpNo;
    }

            

    
}
