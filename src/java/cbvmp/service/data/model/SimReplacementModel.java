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
//@Table(name = "")
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_sim_replacement",
            query = "CALL SIM_REPLACEMENT(?,:p1,:p2,:p3,:p4,:p5,:p6,:p7,:p8,:p9)",
            callable = true,
            resultClass = SimReplacementModel.class
    )

})
public class SimReplacementModel {
    

    @Id
    //@GeneratedValue
    @Column(name="V_IS_ERROR")
    private Integer isError;
    @Column(name="V_ERR_CODE")
    private String errCode;
    @Column(name="V_ERR_DESC")
    private String errDesc;
    @Column(name="SIM_INFO_NO")
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
