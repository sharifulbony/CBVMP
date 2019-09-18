/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.OTPCancelRequest;
import cbvmp.service.api.response.OTPCancelResponse;
import cbvmp.service.data.manager.OTPCancelModelManager;
import cbvmp.service.data.model.OTPCancelModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.list.TypeList;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author rahat
 */
@Path("/otp")
public class OTPCancelApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/cancel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OTPCancelResponse cancelResponse(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, OTPCancelRequest otpRequest) {
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = OTPCancelRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(otpRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(otpRequest));        
//              
            }
//
            catch (IllegalArgumentException ex ) {
                Logger.getLogger(GenerateOneTimePassword.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(GenerateOneTimePassword.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        Gson gson = new GsonBuilder().serializeNulls().create();
        System.out.println("full request...........:"+gson.toJson(logHash));
//        Gson gson = new Gson();       

// 1. Java object to JSON, and save into a file       

// 2. Java object to JSON, and assign to a String
//        String jsonInString = gson.toJson(otpRequest);
        
        
        
        
        String ipAddress = request.getRemoteAddr();

        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);
        ArrayList<String> validationErrors = null;

        OTPCancelResponse response = new OTPCancelResponse();
        // Authentication code
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(otpRequest, OTPCancelRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful",gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            } //NID check
            else if ((otpRequest.getTypeNo().equals(1) && !(Validation.isValidNID(otpRequest.getUserNID()))) || (otpRequest.getTypeNo().equals(5) && !(Validation.isValidSmartNID(otpRequest.getUserNID())))){
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                return response;
            } else if (!TypeList.typeFound(otpRequest.getTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return response;
            }
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                return response;
            }

            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
            
             // allowed ip connection check
//            if(!TypeList.isValidIp(schema, ipAddress))
//            {
//                response.setErrorCode(ApplicationError.EXP010204);
//                response.setErrorDescription(ApplicationError.EXP010204_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp generation attempt", "unsuccessful"," " , "1","Unauthorized IP access by"+schema);
//                logger.error(generateRequestLogString);
//                return response;
//            }

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);

            OTPCancelModelManager otpModelManager = new OTPCancelModelManager();

            OTPCancelModel otpModel = otpModelManager.cancelOTP(
                    schema,
                    otpRequest.getOtpNo(),                    
                    otpRequest.getUserNID(),
                    otpRequest.getTypeNo()
            );

            if (otpModel != null) {
                if (otpModel.getError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Otp cancel attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Otp cancel attempt", "unsuccessful", gson.toJson(logHash), "0", otpModel.getErrorDesc());
                    logger.error(generateRequestLogString);
                }

                //response.setIsError(otpModel.getError());  
                response.setIsError(otpModel.getError());
                response.setErrorCode(otpModel.getErrorCode());
                response.setErrorDescription(otpModel.getErrorDesc());
//            response.setErrorCode(otpModelList.get(0).getV_ERR_CODE());
//            response.setErrorDescription(otpModelList.get(0).getV_ERR_DESC());
            } else {

                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful",gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);

                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);
            }
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful", gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return response;
    }

    @GET
    @Path("/cancel")
    public Response generateOtp(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp cancel attempt", "unsuccessful",new Gson().toJson(request).toString() , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
