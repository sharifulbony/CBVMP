/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;
import cbvmp.service.api.request.OTPExtentionRequest;
import cbvmp.service.api.response.OTPExtentionResponse;
import cbvmp.service.data.manager.OTPExtentionModelManager;
import cbvmp.service.data.model.OTPExtentionModel;
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
public class OTPExtentionApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/extension")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public OTPExtentionResponse extendResponse(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, OTPExtentionRequest otpRequest) {
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = OTPExtentionRequest.class.getDeclaredFields();
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
       
        String generateRequestLogString =SingletoneLogger.generateRequestLogString(request);
        logger.info(generateRequestLogString);
        ArrayList<String> validationErrors = null;

        OTPExtentionResponse response = new OTPExtentionResponse();
        // Authentication code
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(otpRequest, OTPExtentionRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            } //NID check
            else if (otpRequest.getTypeNo().equals(1) && !(Validation.isValidNID(otpRequest.getUserNID()))) {
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                return response;
            } else if (!TypeList.typeFound(otpRequest.getTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return response;
            } else if (!TypeList.purposeFound(otpRequest.getPurposeNo())) {
                response.setErrorCode(ApplicationError.EXP010211);
                response.setErrorDescription(ApplicationError.EXP010211_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid purpose type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid purpose type no");
                return response;
            }
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash) , "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                return response;
            }

            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
            
             // allowed ip connection check
            if(!TypeList.isValidIp(schema, ipAddress))
            {
                response.setErrorCode(ApplicationError.EXP010204);
                response.setErrorDescription(ApplicationError.EXP010204_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp generation attempt", "unsuccessful",gson.toJson(logHash) , "1","Unauthorized IP access by"+schema);
                logger.error(generateRequestLogString);
                return response;
            }

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);

            OTPExtentionModelManager otpModelManager = new OTPExtentionModelManager();

            OTPExtentionModel otpModel = otpModelManager.extendOTP(
                    schema,
                    otpRequest.getOtpNo(),
                    otpRequest.getTypeNo(),
                    otpRequest.getUserNID(),
                    otpRequest.getPurposeNo()
            );

            if (otpModel != null) {
                if (otpModel.getError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Otp extention attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Otp extention attempt", "unsuccessful",gson.toJson(logHash), "0", otpModel.getErrorDesc());
                    logger.error(generateRequestLogString);
                }

                //response.setIsError(otpModel.getError());
                response.setOtpNo(otpModel.getOtpNo());
                response.setExpirationTime(otpModel.getExpireTime());
                response.setErrorCode(otpModel.getErrorCode());
                response.setErrorDescription(otpModel.getErrorDesc());
//            response.setErrorCode(otpModelList.get(0).getV_ERR_CODE());
//            response.setErrorDescription(otpModelList.get(0).getV_ERR_DESC());
            } else {

                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Otp extention attempt", "unsuccessful",gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);

                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);
            }
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp extention attempt", "unsuccessful",gson.toJson(logHash) , "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return response;
    }

    @GET
    @Path("/extension")
    public Response generateOtp(@Context HttpServletRequest request) {
        logger.info(request.getRemoteAddr());
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}

