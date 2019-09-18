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
public class SimReplacementRequest {

    @XmlElement(name = "doc_type_no", required = true)
    Integer docTypeNo;

    @XmlElement(name = "doc_id", required = true)
    String docID;

    @XmlElement(name = "retail_id", required = true)
    String retailID;

    @XmlElement(name = "msisdn", required = true)
    String msisdn;

    @XmlElement(name = "cmpo_trn_id", required = true)
    String cmpoTrnID;

    @XmlElement(name = "imsi", required = true)
    String imsi;

    @XmlElement(name = "replace_date", required = true)
    String replaceDate;

    public Integer getDocTypeNo() {
        return docTypeNo;
    }

    public void setDocTypeNo(Integer docTypeNo) {
        this.docTypeNo = docTypeNo;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getRetailID() {
        return retailID;
    }

    public void setRetailID(String retailID) {
        this.retailID = retailID;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCmpoTrnID() {
        return cmpoTrnID;
    }

    public void setCmpoTrnID(String cmpoTrnID) {
        this.cmpoTrnID = cmpoTrnID;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getReplaceDate() {
        return replaceDate;
    }

    public void setReplaceDate(String replaceDate) {
        this.replaceDate = replaceDate;
    }

}
