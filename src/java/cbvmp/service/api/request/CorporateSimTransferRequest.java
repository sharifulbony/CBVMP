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

public class CorporateSimTransferRequest {

    @XmlElement(name = "src_doc_type_no", required = true)
    Integer docTypeNo;
    @XmlElement(name = "src_doc_id", required = true)
    String docID;
    @XmlElement(name = "dest_doc_type_no", required = true)
    Integer destDocTypeNo;
    @XmlElement(name = "dest_doc_id", required = true)
    String destDocId;
    @XmlElement(name = "retail_id", required = true)
    String retailID;
    @XmlElement(name = "SIM", required = true)
    SIMCard[] simList;
    @XmlElement(name = "dest_dob", required = true)
    String dob;
    @XmlElement(name = "transfer_date", required = true)
    String regDate;
    @XmlElement(name = "dest_is_verified", required = true)
    Integer isVerify;
    @XmlElement(name = "dest_ec_sess_id")
    String ecSessID;
    @XmlElement(name = "dest_ec_sess_time")
    String ecSessTime;
    @XmlElement(name = "dest_ec_trn_id")
    String ecTrn;
    @XmlElement(name = "dest_foreign_flag", required = true)
    Integer destForeignFlag;
    @XmlElement(name = "dest_exp_time")
    String destExpTime;

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

    public String getDestDocId() {
        return destDocId;
    }

    public void setDestDocId(String destDocId) {
        this.destDocId = destDocId;
    }

    public Integer getDestDocTypeNo() {
        return destDocTypeNo;
    }

    public void setDestDocTypeNo(Integer destDocTypeNo) {
        this.destDocTypeNo = destDocTypeNo;
    }

    public String getRetailID() {
        return retailID;
    }

    public void setRetailID(String retailID) {
        this.retailID = retailID;
    }

    public SIMCard[] getSimList() {
        return simList;
    }

    public void setSimList(SIMCard[] simList) {
        this.simList = simList;
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

    public Integer getIsVerify() {
        return isVerify;
    }

    public void setIsVerify(Integer isVerify) {
        this.isVerify = isVerify;
    }

    public String getEcSessID() {
        return ecSessID;
    }

    public void setEcSessID(String ecSessID) {
        this.ecSessID = ecSessID;
    }

    public Integer getDestForeignFlag() {
        return destForeignFlag;
    }

    public void setDestForeignFlag(Integer destForeignFlag) {
        this.destForeignFlag = destForeignFlag;
    }

    public String getDestExpTime() {
        return destExpTime;
    }

    public void setDestExpTime(String destExpTime) {
        this.destExpTime = destExpTime;
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
