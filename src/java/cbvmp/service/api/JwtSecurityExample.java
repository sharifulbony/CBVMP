/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import static cbvmp.service.api.JwtAuthentication.jwkList;
import cbvmp.service.api.request.OTPRequest;
import cbvmp.service.api.request.User;
import cbvmp.service.api.response.OTPResponse;
import cbvmp.service.data.manager.OTPModelManager;
import cbvmp.service.data.model.OTPModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
@Path("/securityprev")
public class JwtSecurityExample {

    @Path("/finditembyid")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findItemById(@QueryParam("token") String token,
            @QueryParam("itemid") String item_id)
            throws JsonGenerationException,
            JsonMappingException, IOException, InvalidJwtException {

        //Item item = null;
        String item = null;

        if (token == null) {

            return Response.status(Status.FORBIDDEN.getStatusCode())
                    .entity("Access Denied for this functionality !!!").build();
        }

        JsonWebKeySet jwks = new JsonWebKeySet(jwkList);
        JsonWebKey jwk = jwks.findJsonWebKey("1", null, null, null);

        // Validate Token's authenticity and check claims
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setExpectedIssuer("synesisit.com")
                .setVerificationKey(jwk.getKey())
                .build();
        JwtClaims jwtClaims = null;
        String cmpo = null;
        try {
            //  Validate the JWT and process it to the Claims
            jwtClaims = jwtConsumer.processToClaims(token);

        } catch (InvalidJwtException e) {

            return Response.status(Status.FORBIDDEN.getStatusCode())
                    .entity("Access token expired!!!").build();
        }
        try {
            cmpo = jwtClaims.getSubject();
        } catch (MalformedClaimException ex) {
            java.util.logging.Logger.getLogger(JwtSecurityExample.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(200).entity(cmpo).build();
    }

    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OTPResponse[] test(@HeaderParam("Authorization") String token, OTPRequest otpRequest) throws IOException {
        OTPModelManager otpModelManager = new OTPModelManager();
        /*OTPModel otpModelList = otpModelManager.generateOneTimePassword(
                "cmpoGP",
                otpRequest.getUserNID(),
                otpRequest.getPurposeNo(),
                otpRequest.getCmpoNo(),
                "172.16.24.135"
        );*/
        OTPResponse[] response = new OTPResponse[10];
//        for (int i = 0; i < otpModelList.size(); i++) {
//            response[i] = new OTPResponse();
//            response[i].setOtp(otpModelList.get(i).getV_OTP_NO());
//            response[i].setOtpValue(otpModelList.get(i).getV_OTP_VAL());
//            response[i].setExpirationTime(otpModelList.get(i).getV_EXP_TIME());
////            response[i].setErrorCode(otpModelList.get(i).getV_ERR_CODE());
////            response[i].setErrorDescription(otpModelList.get(i).getV_ERR_DESC());
//            response[i].setErrorCode("EXP010010");
//            response[i].setErrorDescription("Testing Error");
//        }
        return response;
    }

    @Path("/post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public User testPost(@HeaderParam("Authorization") String token, User user) throws IOException {
        return new User(token + ":" + user.getName(), token + ":" + user.getPassword());

    }

    private User validUser(String u, String p) {
        return new User(u, p);
    }

}
