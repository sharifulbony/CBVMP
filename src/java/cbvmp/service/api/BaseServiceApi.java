/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import static cbvmp.service.api.JwtAuthentication.jwkList;
import cbvmp.service.util.error.ApplicationError;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

/**
 *
 * @author tanbir
 */
public class BaseServiceApi {

    protected String[] authenticateToken(String token) {
        String[] tokenInformation = new String[3];

        if (token == null) {
            tokenInformation[0] = null;
            tokenInformation[1] = ApplicationError.EXP010202;
            tokenInformation[2] = ApplicationError.EXP010202_DESC;
            return tokenInformation;
        }

        JsonWebKeySet jwks = new JsonWebKeySet(jwkList);
        JsonWebKey jwk = jwks.findJsonWebKey("1", null, null, null);

        // Validate Token's authenticity and check claims
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time   
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("synesisit.com") // whom the JWT needs to have been issued by
                .setVerificationKey(jwk.getKey()) //verify the signature with the public key
                .build();
        JwtClaims jwtClaims = null;
        String schema = null;
        Long cmpoNo = null;
        try {
            jwtClaims = jwtConsumer.processToClaims(token);
        } catch (InvalidJwtException e) {
            tokenInformation[0] = null;
            tokenInformation[1] = ApplicationError.EXP010203;
            tokenInformation[2] = ApplicationError.EXP010203_DESC;
            return tokenInformation;

        }

        try {
            schema = jwtClaims.getSubject();
            cmpoNo = (Long) jwtClaims.getClaimValue("mno");
        } catch (MalformedClaimException ex) {
            tokenInformation[0] = null;
            tokenInformation[1] = ApplicationError.EXP010204;
            tokenInformation[2] = ApplicationError.EXP010204_DESC;
            return tokenInformation;
        }
        //tokenInformation[0] = schema;
        tokenInformation[0] = schema + ":" + cmpoNo;
        tokenInformation[1] = null;
        tokenInformation[2] = null;

        return tokenInformation;
    }

}
