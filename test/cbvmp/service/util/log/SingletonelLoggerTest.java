/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.util.log;

import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
public class SingletonelLoggerTest {
    
    public SingletonelLoggerTest() {
    }

    /**
     * Test of getLogger method, of class SingletonelLogger.
     */
    @Test
    public void testGetLogger() {
        System.out.println("Testing for singletonlogger inplementation");
        Class clazz = this.getClass();
        Log4jLoggerAdapter expResult = null;
        Log4jLoggerAdapter result = SingletoneLogger.getLogger(clazz);
        result.debug("Testing singletone logger");
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
