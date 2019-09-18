/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tanbir
 */
@XmlRootElement
public class SimTransferRequest {

    @XmlElement(name = "src_doc_type_no", required = true)
    Integer srcDocTypeNo;
    @XmlElement(name = "src_doc_id", required = true)
    String srcDocTypeID;
    @XmlElement(name = "dest_doc_type_no", required = true)
    Integer destDocTypeNo;
    @XmlElement(name = "dest_doc_id", required = true)
    String destDocTypeID;
    @XmlElement(name = "retail_id", required = true)
    String retailId;
    @XmlElement(name = "msisdn", required = true)
    String msisdn;
    @XmlElement(name = "dest_dob", required = true)
    String destDob;//date type
    @XmlElement(name = "cmpo_trn_id", required = true)
    String cmpoTrnId;
    @XmlElement(name = "transfer_date", required = true)
    String regDate; //date type
    @XmlElement(name = "dest_is_verified", required = true)
    Integer isVerified;
    @XmlElement(name = "dest_ec_sess_id")
    String ecSessionID;
    @XmlElement(name = "dest_ec_sess_time")
    String ecSessTime;
    @XmlElement(name = "dest_ec_trn_id")
    String ecTrn;
    @XmlElement(name = "dest_imsi", required = true)
    String destIMSI;
    @XmlElement(name = "otp_no", required = true)
    Integer otpNo;
    @XmlElement(name = "dest_foreign_flag", required = true)
    Integer destForeignFlag;
    @XmlElement(name = "dest_exp_time")
    String destExpTime;

    public Integer getSrcDocTypeNo() {
        return srcDocTypeNo;
    }

    public void setSrcDocTypeNo(Integer srcDocTypeNo) {
        this.srcDocTypeNo = srcDocTypeNo;
    }

    public String getSrcDocTypeID() {
        return srcDocTypeID;
    }

    public void setSrcDocTypeID(String srcDocTypeID) {
        this.srcDocTypeID = srcDocTypeID;
    }

    public Integer getDestDocTypeNo() {
        return destDocTypeNo;
    }

    public void setDestDocTypeNo(Integer destDocTypeNo) {
        this.destDocTypeNo = destDocTypeNo;
    }

    public String getDestDocTypeID() {
        return destDocTypeID;
    }

    public void setDestDocTypeID(String destDocTypeID) {
        this.destDocTypeID = destDocTypeID;
    }

    public String getRetailId() {
        return retailId;
    }

    public void setRetailId(String retailId) {
        this.retailId = retailId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getDestDob() {
        return destDob;
    }

    public void setDestDob(String destDob) {
        this.destDob = destDob;
    }

    public String getCmpoTrnId() {
        return cmpoTrnId;
    }

    public void setCmpoTrnId(String cmpoTrnId) {
        this.cmpoTrnId = cmpoTrnId;
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

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getEcSessionID() {
        return ecSessionID;
    }

    public void setEcSessionID(String ecSessionID) {
        this.ecSessionID = ecSessionID;
    }

    public String getDestIMSI() {
        return destIMSI;
    }

    public void setDestIMSI(String destIMSI) {
        this.destIMSI = destIMSI;
    }

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
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
