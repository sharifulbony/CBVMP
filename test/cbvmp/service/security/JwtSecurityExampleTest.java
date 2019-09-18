/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.security;

import cbvmp.service.api.JwtSecurityExample;
import cbvmp.service.api.response.ApiName;
import cbvmp.service.api.response.GeneralResponse;
import cbvmp.service.api.request.User;
import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author tanbir
 */
public class JwtSecurityExampleTest {
    
    public JwtSecurityExampleTest() {
    }
    /**
     * Test of returnVersion method, of class JwtSecurityExample.
     */
    @Test
    @Ignore
    public void testReturnVersion() {
//        System.out.println("returnVersion");
//        JwtSecurityExample instance = new JwtSecurityExample();
//        String expResult = "";
//        String result = instance.returnVersion();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of authenticateCredentials method, of class JwtSecurityExample.
     */
    @Test
    public void testAuthenticateCredentials() throws Exception {
        System.out.println("authenticateCredentials");
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        
        User user = new User("Sagar","Tanbir");
        WebTarget webTarget = client.target(getBaseURI());
        User gr = webTarget.path("rest").path("security").path("post")
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .post(Entity.entity(user, MediaType.APPLICATION_JSON),User.class);
        System.out.println(gr.getName());
    }
    /**
     * Test of findItemById method, of class JwtSecurityExample.
     */
    

    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8084/CBVMPService").build();

    }

}
