/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;


import cbvmp.service.api.request.ExpireTimeRequest;
import cbvmp.service.api.response.ExpireTimeResponse;
import cbvmp.service.data.manager.ExpTimeModelManager;
import cbvmp.service.data.model.ExpTimeModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.list.TypeList;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
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
@Path("/expire")
public class ExpireTimeApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/time/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ExpireTimeResponse updateResponse(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, ExpireTimeRequest expRequest) {
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = ExpireTimeRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(expRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(expRequest));        
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
        
        Date expTime=null;

        ExpireTimeResponse response = new ExpireTimeResponse();
        // Authentication code
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(expRequest, ExpireTimeRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful",gson.toJson(logHash) , "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            } //NID check
            else if (!(expRequest.getDoctypeNo().equals(2) || expRequest.getDoctypeNo().equals(3))) {
                response.setErrorCode(ApplicationError.EXP010223);
                response.setErrorDescription(ApplicationError.EXP010223_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful",gson.toJson(logHash) , "0", "Expire time update for invalid document type");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                return response;
            } else if (!TypeList.typeFound(expRequest.getDoctypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return response;
            } 
            else if (!(Validation.isValidDate(expRequest.getExpTime()))) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

                //logger.error("Date format validation error");
                return response;
            }
            
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful",gson.toJson(logHash), "1", tokenInformation[2]);
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
            
            
            expTime=Validation.formatDate(expRequest.getExpTime(), 0);

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);

            ExpTimeModelManager expManager = new ExpTimeModelManager();

            ExpTimeModel expModel = expManager.updateExpTime(
                    schema,
                    expRequest.getDoctypeNo(),
                    expRequest.getDocId(),
                    expTime                    
            );

            if (expModel != null) {
                if (expModel.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Expire Time update attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Expire Time update attempt", "unsuccessful",gson.toJson(logHash) , "0", expModel.getErrDesc());
                    logger.error(generateRequestLogString);
                }

                //response.setIsError(otpModel.getError());
                response.setSimNo(expModel.getSimInfoNo());
                response.setIsError(expModel.getIsError());
                response.setErrorCode(expModel.getErrCode());
                response.setErrorDescription(expModel.getErrDesc());
//            response.setErrorCode(otpModelList.get(0).getV_ERR_CODE());
//            response.setErrorDescription(otpModelList.get(0).getV_ERR_DESC());
            } else {

                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Expire Time update attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);

                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);
            }
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Expire Time update attempt", "unsuccessful",gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return response;
    }

    @GET
    @Path("/time/update")
    public Response updateResponse(@Context HttpServletRequest request) {
        logger.info(request.getRemoteAddr());
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}