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
 * @author SIT
 *
 */
@XmlRootElement
public class CorporateSimRegReregRequest {

    @XmlElement(name = "purpose_no", required = true)
    Integer purposeNo;

    @XmlElement(name = "doc_type_no", required = true)
    Integer docTypeNo;

    @XmlElement(name = "doc_id", required = true)
    String documentId;

    @XmlElement(name = "retail_id", required = true)
    String retailId;

    @XmlElement(name = "SIM", required = true)
    SIMCard[] simList;

    @XmlElement(name = "dob", required = true)
    String dob;

    @XmlElement(name = "reg_date", required = true)
    String regDate;

    @XmlElement(name = "is_verified", required = true)
    Integer isVerified;

    @XmlElement(name = "ec_sess_id")
    String ecSessId;

    @XmlElement(name = "ec_sess_time")
    String ecSessTime;

    @XmlElement(name = "ec_trn_id")
    String ecTrn;

    @XmlElement(name = "foreigner_flag", required = true)
    Integer foreignFlag;

    @XmlElement(name = "exp_time")
    String expTime;

    public Integer getPurposeNo() {
        return purposeNo;
    }

    public void setPurposeNo(Integer purposeNo) {
        this.purposeNo = purposeNo;
    }

    public Integer getDocTypeNo() {
        return docTypeNo;
    }

    public void setDocTypeNo(Integer docTypeNo) {
        this.docTypeNo = docTypeNo;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getRetailId() {
        return retailId;
    }

    public void setRetailId(String retailId) {
        this.retailId = retailId;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerifired(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getEcSessId() {
        return ecSessId;
    }

    public void setEcSessId(String ecSessId) {
        this.ecSessId = ecSessId;
    }

    public Integer getForeignFlag() {
        return foreignFlag;
    }

    public void setForeignFlag(Integer foreignFlag) {
        this.foreignFlag = foreignFlag;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public SIMCard[] getSimList() {
        return simList;
    }

    public void setSimList(SIMCard[] simList) {
        this.simList = simList;
    }

    public String getEcSessTime() {
        return ecSessTime;
    }

    public void setEcSessTime(String ecSessTime) {
        this.ecSessTime = ecSessTime;
    }

    public String getEcTrn() {
        return ecTrn;
    }

    public void setEcTrn(String ecTrn) {
        this.ecTrn = ecTrn;
    }

}
