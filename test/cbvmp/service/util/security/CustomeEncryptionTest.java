/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author tanbir
 */
public class CustomeEncryptionTest {
    
    public CustomeEncryptionTest() {
    }

    /**
     * Test of encryptPassword method, of class CustomeEncryption.
     */
    @Test
    @Ignore
    public void testEncryptPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
        System.out.println("encryptPassword");
        String password = "GrameenPhone";
        String expResult = "";
        String result = CustomeEncryption.encryptPassword(password);
        System.out.println(result);
    }

    /**
     * Test of validatePassword method, of class CustomeEncryption.
     */
    @Test
    public void testValidatePassword() throws Exception {
        System.out.println("validatePassword");
        String originalPassword = "GrameenPhone";
        String storedPassword = "1000:63fec8214ce3f8a24b241eadfe0866e0:cd6ce398ae2ec60bddbe1ac3b94161539a9771cfe78293568e3aabd8d4dea2b716068b0fb635a892db724fe9cc0f0580e0714022cd246df873c4b1fdeb13fd22";
        boolean expResult = true;
        boolean result = CustomeEncryption.validatePassword(originalPassword, storedPassword);
        assertEquals(expResult, result);
        
        
    }

    
    
    
}
