/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

/**
 *
 * @author tanbir
 */
@Entity
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "sp_verify_user",
            query = "CALL SEC_API_USER_SALT(?,:p1)",
            callable = true,
            resultClass = CMPOModel.class
    )

})

public class CMPOModel implements Serializable {

    @Id
    @Column(name = "CMPO_NO")
    private Integer cmpoNo;
    @Column(name = "API_USER_PASS")
    private String password;
    @Column(name = "IS_PASS_CHANGE_REQ")
    private boolean passwdChangeRequired;
    @Column(name = "SCHEMA_CODE")
    private String schemaCode;

    public Integer getCmpoNo() {
        return cmpoNo;
    }

    public void setCmpoNo(Integer cmpoNo) {
        this.cmpoNo = cmpoNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswdChangeRequired() {
        return passwdChangeRequired;
    }

    public void setIsPasswdChangeRequired(boolean isPasswdChangeRequired) {
        this.passwdChangeRequired = isPasswdChangeRequired;
    }

    public String getSchemaCode() {
        return schemaCode;
    }

    public void setSchemaCode(String schemaCode) {
        this.schemaCode = schemaCode;
    }

}
