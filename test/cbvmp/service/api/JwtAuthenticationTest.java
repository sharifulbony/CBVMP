/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;





import javax.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
public class JwtAuthenticationTest {
    
    public JwtAuthenticationTest() {
    }

    /**
     * Test of returnVersion method, of class JwtAuthentication.
     */
    @Test
    @Ignore
    public void testReturnVersion() {
        System.out.println("returnVersion");
        JwtAuthentication instance = new JwtAuthentication();
        String expResult = "";
        Response result = instance.returnVersion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of authenticateCredentials method, of class JwtAuthentication.
     */
    @Test
    public void testAuthenticateCredentials() throws Exception {
        System.out.println("authenticateCredentials");
        Log4jLoggerAdapter log = (Log4jLoggerAdapter) LoggerFactory.getLogger(this.getClass());
        log.trace("Test trace");
        log.warn("Test trace");
        log.error("Test trace");
    }
    
}
