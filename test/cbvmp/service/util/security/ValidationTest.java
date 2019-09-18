/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.security;

import junit.framework.TestCase;

/**
 *
 * @author rahat
 */
public class ValidationTest extends TestCase {
    
    public ValidationTest(String testName) {
        super(testName);
    }
   

    /**
     * Test of isValidDate method, of class Validation.
     */
    public void testIsValidDate() {
        System.out.println("isValidDate");
        String inDate = "";
        boolean expResult = false;
        boolean result = Validation.isValidDate(inDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidDob method, of class Validation.
     */
    public void testIsValidDob() {
        
        String inDate = "10/07/201";
        boolean expResult = true;
        boolean result = Validation.isValidDob(inDate);
        System.out.println(result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of isValidNID method, of class Validation.
     */
    public void testIsValidNID() {
        System.out.println("isValidNID");
        String nid = "";
        boolean expResult = false;
        boolean result = Validation.isValidNID(nid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isValidMSISDN method, of class Validation.
     */
    public void testIsValidMSISDN() {
        System.out.println("isValidMSISDN");
        String msisdn = "";
        boolean expResult = false;
        boolean result = Validation.isValidMSISDN(msisdn);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
