/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.SimRegistrationRequest;
import cbvmp.service.api.request.SimReplacementRequest;
import cbvmp.service.api.response.SimRegistrationResponse;
import cbvmp.service.api.response.SimReplacementResponse;
import cbvmp.service.data.manager.SimReplacementModelManager;
import cbvmp.service.data.model.SimReplacementModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.list.TypeList;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
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
@Path("/sim")
public class SimReplacementApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/replacement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SimReplacementResponse registerSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, SimReplacementRequest simReplaceRequest) {

        
    HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = SimReplacementRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(simReplaceRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(simReplaceRequest));        
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
        
        
        
        
        
        
        String generateRequestLogString = null;
        String ipAddress = request.getRemoteAddr();
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);

        SimReplacementResponse response = new SimReplacementResponse();
        ArrayList<String> validationErrors = null;
        // Authentication code
        try {
            validationErrors = (ArrayList) ApplicationError.validateRequired(simReplaceRequest, SimReplacementRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash) , "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            }
            if (!TypeList.typeFound(simReplaceRequest.getDocTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return response;
            } // date format validation
            else if (!(Validation.isValidDate(simReplaceRequest.getReplaceDate()))) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);
                //logger.error("Date format validation error");
                return response;
            } //mssisdn validation
            else if (!(Validation.isValidMSISDN(simReplaceRequest.getMsisdn()))) {
                response.setErrorCode(ApplicationError.EXP010208);
                response.setErrorDescription(ApplicationError.EXP010208_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid MSISDN format");
                logger.error(generateRequestLogString);
                //logger.error("MSISDN format not matched");
                return response;
            } //NID check
            else if ((simReplaceRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(simReplaceRequest.getDocID()))) || (simReplaceRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(simReplaceRequest.getDocID()))) ){
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash) , "0", "Invalid NID format");
                logger.error(generateRequestLogString);
                //logger.error("NID format validation error");
                return response;
            }

            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash), "1", tokenInformation[2]);
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

            SimReplacementModelManager simReplaceModelManager = new SimReplacementModelManager();
            SimReplacementModel simReplaceModelList = simReplaceModelManager.replaceSim(
                    schema,
                    6,
                    simReplaceRequest.getDocTypeNo(),
                    simReplaceRequest.getDocID(),
                    simReplaceRequest.getRetailID(),
                    simReplaceRequest.getMsisdn(),
                    simReplaceRequest.getCmpoTrnID(),
                    cmpoNo,
                    simReplaceRequest.getImsi(),
                    Validation.formatDate(simReplaceRequest.getReplaceDate(), 0)
            );

            if (!(simReplaceModelList == null)) {
                if (simReplaceModelList.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim replacement attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim replacement attempt", "unsuccessful",gson.toJson(logHash), "0",  simReplaceModelList.getErrDesc());
                    logger.error(generateRequestLogString);
                }
                
                response.setSimInfoNo(simReplaceModelList.getSimInfoNo());
                response.setIsError(simReplaceModelList.getIsError());
                response.setErrorCode(simReplaceModelList.getErrCode());
                response.setErrorDescription(simReplaceModelList.getErrDesc());

            } else {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim replacement attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);

            }
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",gson.toJson(logHash) , "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return response;
    }

    @GET
    @Path("/replacement")
    public Response registerSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim replacement attempt", "unsuccessful",new Gson().toJson(request).toString() , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
