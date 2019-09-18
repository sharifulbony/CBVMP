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
 *
 */
@XmlRootElement
public class SimDeregistrationRequest {

    @XmlElement(name = "doc_type_no", required = true)
    Integer docTypeNo;

    @XmlElement(name = "doc_id", required = true)
    String documentId;

    @XmlElement(name = "retail_id", required = true)
    String retailId;

    @XmlElement(name = "msisdn", required = true)
    String msisdn;

    @XmlElement(name = "cmpo_trn_id", required = true)
    String cmpoTrnId;

    @XmlElement(name = "de_reg_date", required = true)
    String deRegDate;

    @XmlElement(name = "is_verified", required = true)
    Integer isVerified;

    @XmlElement(name = "ec_sess_id")
    String ecSessId;

    @XmlElement(name = "ec_sess_time")
    String ecSessTime;

    @XmlElement(name = "ec_trn_id")
    String ecTrnId;

    @XmlElement(name = "otp_no", required = true)
    Integer otpNo;

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

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCmpoTrnId() {
        return cmpoTrnId;
    }

    public void setCmpoTrnId(String cmpoTrnId) {
        this.cmpoTrnId = cmpoTrnId;
    }

    public String getDeRegDate() {
        return deRegDate;
    }

    public void setDeRegDate(String deRegDate) {
        this.deRegDate = deRegDate;
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

    public Integer getOtpNo() {
        return otpNo;
    }

    public void setOtpNo(Integer otpNo) {
        this.otpNo = otpNo;
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
