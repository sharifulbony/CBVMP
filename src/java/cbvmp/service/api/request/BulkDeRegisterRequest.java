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
public class BulkDeRegisterRequest {

    @XmlElement(name = "batch_id", required = true)
    String batchId;

    @XmlElement(name = "SIM", required = true)
    SIMCard[] simList;

    public SIMCard[] getSimList() {
        return simList;
    }

    public void setSimList(SIMCard[] simList) {
        this.simList = simList;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

}
