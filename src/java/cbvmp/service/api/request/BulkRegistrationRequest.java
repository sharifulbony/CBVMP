/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.request;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tanbir
 */

@XmlRootElement
public class BulkRegistrationRequest {
    @XmlElement(name="NID")
    String nid;
    @XmlElement(name="SIM")
    SIMCard[] simList;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public SIMCard[] getSimList() {
        return simList;
    }

    public void setSimList(SIMCard[] simList) {
        this.simList = simList;
    }
    
    
}
