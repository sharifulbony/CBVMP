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
 *
 *
 */
@XmlRootElement
public class SimRegistrationRequest {

    @XmlElement(name = "purpose_no", required = true)
    Integer purposeNo;
    @XmlElement(name = "doc_type_no", required = true)
    Integer docTypeNo;
    @XmlElement(name = "doc_id", required = true)
    String docID;
    @XmlElement(name = "retail_id", required = true)
    String retailID;
    @XmlElement(name = "msisdn", required = true)
    String msisdn;
    @XmlElement(name = "dob", required = true)
    String dob;
    @XmlElement(name = "cmpo_trn_id", required = true)
    String cmpoTrnID;
    @XmlElement(name = "reg_date", required = true)
    String regDate;
    @XmlElement(name = "is_verified", required = true)
    Integer isVerify;
    @XmlElement(name = "ec_sess_id")
    String ecSessID;
    @XmlElement(name = "ec_sess_time")
    String ecSessTime;
    @XmlElement(name = "ec_trn_id")
    String ecTrnId;
    @XmlElement(name = "imsi", required = true)
    String imsi;
    @XmlElement(name = "otp_no", required = true)
    Integer otpNo;
    @XmlElement(name = "foreigner_flag", required = true)
    Integer foreignFlag;
    @XmlElement(name = "exp_time")
    String expDate;

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCmpoTrnID() {
        return cmpoTrnID;
    }

    public void setCmpoTrnID(String cmpoTrnID) {
        this.cmpoTrnID = cmpoTrnID;
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

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
    }

    public Integer getForeignFlag() {
        return foreignFlag;
    }

    public void setForeignFlag(Integer foreignFlag) {
        this.foreignFlag = foreignFlag;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getEcSessTime() {
        return ecSessTime;
    }

    public void setEcSessTime(String ecSessTime) {
        this.ecSessTime = ecSessTime;
    }

    public String getEcTrnId() {
        return ecTrnId;
    }

    public void setEcTrnId(String ecTrnId) {
        this.ecTrnId = ecTrnId;
    }

}
