/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rahat
 */

@XmlRootElement
public class ExpireTimeRequest {
    @XmlElement(name = "doc_type_no", required = true)
    Integer doctypeNo;
    @XmlElement(name = "doc_id", required = true)
    String docId;
    @XmlElement(name = "exp_time", required = true)
    String expTime;

    public Integer getDoctypeNo() {
        return doctypeNo;
    }

    public void setDoctypeNo(Integer doctypeNo) {
        this.doctypeNo = doctypeNo;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }
    
    

}