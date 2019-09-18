/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.manager;

import cbvmp.service.data.model.CMPOModel;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static jdk.nashorn.tools.ShellFunctions.input;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tanbir
 */
public class CMPOModelManagerTest {

    public CMPOModelManagerTest() {
    }

    /**
     * Test of verifyCMPOUserNamePassword method, of class CMPOModelManager.
     */
    @Test
    public void testVerifyCMPOUserNamePassword() throws NoSuchAlgorithmException {
        String input = "GRAMEENPHONE";
        String md5 = null;
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(input.getBytes(), 0, input.length());
        md5 = new BigInteger(1, digest.digest()).toString(16);

    }

}
