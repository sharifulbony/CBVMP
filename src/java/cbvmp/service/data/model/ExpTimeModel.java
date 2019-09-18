/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;

/**
 *
 * @author rahat
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_update_exp_time",
            query = "CALL UPDATE_EXP_TIME(?,:p1,:p2,:p3)",
            callable = true,
            resultClass = ExpTimeModel.class
    )

})

public class ExpTimeModel implements Serializable{
    @Id
    //@GeneratedValue
    @Column(name="V_IS_ERROR")
    private Integer isError;
    @Column(name="V_ERR_CODE")
    private String errCode;
    @Column(name="V_ERR_DESC")
    private String errDesc;
    @Column(name="V_SIM_INFO_NO")
    private Integer simInfoNo;

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

    public Integer getSimInfoNo() {
        return simInfoNo;
    }

    public void setSimInfoNo(Integer simInfoNo) {
        this.simInfoNo = simInfoNo;
    }
    
    
}
