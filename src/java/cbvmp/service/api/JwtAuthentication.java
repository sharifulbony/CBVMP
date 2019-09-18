/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.JwtRequest;
import cbvmp.service.api.request.User;
import cbvmp.service.api.response.JwtResponse;
import cbvmp.service.data.manager.CMPOModelManager;
import cbvmp.service.data.model.CMPOModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.log.SingletoneLogger;
import com.google.gson.Gson;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.lang.JoseException;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author tanbir
 */
@Path("/security")
public class JwtAuthentication {

//    static final Log4jLoggerAdapter JWT_LOGGER = (Log4jLoggerAdapter) LoggerFactory.getLogger(JwtAuthentication.class);
//    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");
    static final Log4jLoggerAdapter JWT_LOGGER = (Log4jLoggerAdapter) SingletoneLogger.getLogger("applicationLogger");
    static List<JsonWebKey> jwkList = null;

    private static final int EXP_TIME = 24 * 60;

    static {
//        JWT_LOGGER.info("Inside static initializer...");
        jwkList = new LinkedList<>();
        for (int kid = 1; kid <= 3; kid++) {
            JsonWebKey jwk = null;
            try {
                jwk = RsaJwkGenerator.generateJwk(2048);
//                JWT_LOGGER.debug("PUBLIC KEY (" + kid + "): " + jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
            } catch (JoseException e) {
                JWT_LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            jwk.setKeyId(String.valueOf(kid));
            jwkList.add(jwk);
        }

    }

    @Path("/status")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response returnVersion() {
        JWT_LOGGER.debug("Checking Status....");
        for (JsonWebKey jwk : jwkList) {
//            JWT_LOGGER.debug(jwk.toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE));
        }
        return Response.ok().entity("Status is ok!!!").build();
    }

    @POST
    @Path("/authenticate") //parameters: 
    @Produces(MediaType.APPLICATION_JSON)
    public JwtResponse authenticateCredentials(@Context HttpServletRequest request, JwtRequest auth)
            throws JsonGenerationException, JsonMappingException,
            IOException {

//        long start = System.nanoTime();
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request);
//        JWT_LOGGER.info(generateRequestLogString);

        JwtResponse jwtResponse = new JwtResponse();
//        JWT_LOGGER.info("Authenticating User Credentials...");

        if (auth.getUserId() == null) {
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.validationError("username"));
            JWT_LOGGER.error(generateRequestLogString);
//            JWT_LOGGER.error(ApplicationError.validationError("username"));

//            jwtResponse.setIsError(1);
            jwtResponse.setErrorCode(ApplicationError.EXP010201);
            jwtResponse.setErrorDescription(ApplicationError.validationError("user_id"));
            return jwtResponse;
        }

        if (auth.getPassword() == null) {
//            jwtResponse.setIsError(1);
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.validationError("password"));
            JWT_LOGGER.error(generateRequestLogString);
//            JWT_LOGGER.error(ApplicationError.validationError("password"));

            jwtResponse.setErrorCode(ApplicationError.EXP010201);
            jwtResponse.setErrorDescription(ApplicationError.validationError("password"));
            return jwtResponse;
        }

        CMPOModel user = validUser(auth.getUserId(), auth.getPassword());

        if (user == null) {

//            jwtResponse.setIsError(1);
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, auth.getUserId(), "Token generation attempt", "unsuccessful", " ", "1", ApplicationError.EXP010206_DESC);
            JWT_LOGGER.error(generateRequestLogString);
//            JWT_LOGGER.error(ApplicationError.EXP010206_DESC);
//            jwtResponse.setIsError(1);
            jwtResponse.setIsError(1);
            jwtResponse.setErrorCode(ApplicationError.EXP010206);
            jwtResponse.setErrorDescription(ApplicationError.EXP010206_DESC);

            return jwtResponse;

        } else if (user.isPasswdChangeRequired()) {
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, auth.getUserId(), "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.EXP010205_DESC);
            JWT_LOGGER.error(generateRequestLogString);
//            JWT_LOGGER.error(ApplicationError.EXP010205_DESC);
            jwtResponse.setIsError(1);
            jwtResponse.setErrorCode(ApplicationError.EXP010205);
            jwtResponse.setErrorDescription(ApplicationError.EXP010205_DESC);
            return jwtResponse;
        }

        RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);
        senderJwk.setKeyId("1");
//        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", "JWK (1) ===> " + senderJwk.toJson());
//            JWT_LOGGER.info(generateRequestLogString);
//        JWT_LOGGER.info("JWK (1) ===> " + senderJwk.toJson());

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("synesisit.com");
        claims.setExpirationTimeMinutesInTheFuture(EXP_TIME);
        claims.setGeneratedJwtId();
        claims.setIssuedAtToNow();
        claims.setNotBeforeMinutesInThePast(2);
        claims.setSubject(user.getSchemaCode());
        claims.setClaim("mno", user.getCmpoNo());

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKeyIdHeaderValue(senderJwk.getKeyId());
        jws.setKey(senderJwk.getPrivateKey());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
//        JWT_LOGGER.debug(senderJwk.getPrivateKey().toString());
        String jwt = null;
        try {
            jwt = jws.getCompactSerialization();
        } catch (JoseException e) {
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, auth.getUserId(), "Token generation attempt", "unsuccessful", " ", "0", e.getMessage());
            JWT_LOGGER.error(generateRequestLogString);
//            JWT_LOGGER.error(e.getMessage());
//            e.printStackTrace();
        }

        // JSON response object for client
//        JWT_LOGGER.info("Token authorized:" + jwt);
        try {
            Date expDate = new Date(claims.getExpirationTime().getValueInMillis());
            jwtResponse.setIsError(0);
            jwtResponse.setToken(jwt);

            generateRequestLogString = SingletoneLogger.generateRequestLogString(request,auth.getUserId(), "Token generation attempt", "successful", " ", "0", "Token authorized");
            JWT_LOGGER.info(generateRequestLogString);

            jwtResponse.setExpirationTime(expDate.toString());

        } catch (MalformedClaimException ex) {
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request,auth.getUserId(), "Token generation attempt", "unsuccessful", " ", "0", "Token Expiration date could not be set");
            JWT_LOGGER.warn(generateRequestLogString);
//            JWT_LOGGER.warn("Token Expiration date could not be set");
        }
        
//        long end = System.nanoTime();
//        System.out.println("elapsed : " + (end-start)/1000);
        
        return jwtResponse;
    }

    @GET
    @Path("/authenticate")
    public Response authenticateCredentialsGet(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful"," " , "1", ApplicationError.RQEXP404_DESC);
            JWT_LOGGER.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
//    @GET
//    @Path("/execution") //parameters: 
//    @Produces(MediaType.APPLICATION_JSON)
//    public JwtResponse executeCredentials(@QueryParam("username") String username,
//            @QueryParam("password") String password)
//            throws JsonGenerationException, JsonMappingException,
//            IOException {
//
////        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request);
////        JWT_LOGGER.info(generateRequestLogString);
//
//        JwtResponse jwtResponse = new JwtResponse();
////        JWT_LOGGER.info("Authenticating User Credentials...");
//
//        System.out.println(username+" "+ password);
//        if (username  == null) {
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.validationError("username"));
////            JWT_LOGGER.error(generateRequestLogString);
////            JWT_LOGGER.error(ApplicationError.validationError("username"));
//
////            jwtResponse.setIsError(1);
//            jwtResponse.setErrorCode(ApplicationError.EXP010201);
//            jwtResponse.setErrorDescription(ApplicationError.validationError("user_id"));
//            return jwtResponse;
//        }
//
//        if (password == null) {
////            jwtResponse.setIsError(1);
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.validationError("password"));
////            JWT_LOGGER.error(generateRequestLogString);
////            JWT_LOGGER.error(ApplicationError.validationError("password"));
//
//            jwtResponse.setErrorCode(ApplicationError.EXP010201);
//            jwtResponse.setErrorDescription(ApplicationError.validationError("password"));
//            return jwtResponse;
//        }
//
//        CMPOModel user = validUser("CMPO_TEST", "159api@#");
//
//        if (user == null) {
//
////            jwtResponse.setIsError(1);
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "1", ApplicationError.EXP010206_DESC);
////            JWT_LOGGER.error(generateRequestLogString);
////            JWT_LOGGER.error(ApplicationError.EXP010206_DESC);
////            jwtResponse.setIsError(1);
//            jwtResponse.setIsError(1);
//            jwtResponse.setErrorCode(ApplicationError.EXP010206);
//            jwtResponse.setErrorDescription(ApplicationError.EXP010206_DESC);
//
//            return jwtResponse;
//
//        } else if (user.isPasswdChangeRequired()) {
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", ApplicationError.EXP010205_DESC);
////            JWT_LOGGER.error(generateRequestLogString);
////            JWT_LOGGER.error(ApplicationError.EXP010205_DESC);
//            jwtResponse.setIsError(1);
//            jwtResponse.setErrorCode(ApplicationError.EXP010205);
//            jwtResponse.setErrorDescription(ApplicationError.EXP010205_DESC);
//            return jwtResponse;
//        }
//
//        RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);
//        senderJwk.setKeyId("1");
////        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", "JWK (1) ===> " + senderJwk.toJson());
////            JWT_LOGGER.info(generateRequestLogString);
////        JWT_LOGGER.info("JWK (1) ===> " + senderJwk.toJson());
//
//        // Create the Claims, which will be the content of the JWT
//        JwtClaims claims = new JwtClaims();
//        claims.setIssuer("synesisit.com");
//        claims.setExpirationTimeMinutesInTheFuture(EXP_TIME);
//        claims.setGeneratedJwtId();
//        claims.setIssuedAtToNow();
//        claims.setNotBeforeMinutesInThePast(2);
//        claims.setSubject(user.getSchemaCode());
//        claims.setClaim("mno", user.getCmpoNo());
//
//        JsonWebSignature jws = new JsonWebSignature();
//        jws.setPayload(claims.toJson());
//        jws.setKeyIdHeaderValue(senderJwk.getKeyId());
//        jws.setKey(senderJwk.getPrivateKey());
//        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
////        JWT_LOGGER.debug(senderJwk.getPrivateKey().toString());
//        String jwt = null;
//        try {
//            jwt = jws.getCompactSerialization();
//        } catch (JoseException e) {
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", e.getMessage());
////            JWT_LOGGER.error(generateRequestLogString);
////            JWT_LOGGER.error(e.getMessage());
////            e.printStackTrace();
//        }
//
//        // JSON response object for client
////        JWT_LOGGER.info("Token authorized:" + jwt);
//        try {
//            Date expDate = new Date(claims.getExpirationTime().getValueInMillis());
//            jwtResponse.setIsError(0);
//            jwtResponse.setToken(jwt);
//
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "successful", " ", "0", "Token authorized");
////            JWT_LOGGER.info(generateRequestLogString);
//
//            jwtResponse.setExpirationTime(expDate.toString());
//
//        } catch (MalformedClaimException ex) {
////            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Token generation attempt", "unsuccessful", " ", "0", "Token Expiration date could not be set");
////            JWT_LOGGER.warn(generateRequestLogString);
////            JWT_LOGGER.warn("Token Expiration date could not be set");
//        }
//        return jwtResponse;
//    }

    private CMPOModel validUser(String u, String p) {
        CMPOModelManager cmpoModelManager = new CMPOModelManager();
        CMPOModel cmpoModel = cmpoModelManager.verifyCMPOUserNamePassword("CBVMP_MASTER", u, p);
        if (cmpoModel != null) {
            //JWT_LOGGER.info(cmpoModel.getPassword() + ":" + cmpoModel.getSchemaCode() + ":" + cmpoModel.isPasswdChangeRequired());
        }
        return cmpoModel;
    }
    
    
}
