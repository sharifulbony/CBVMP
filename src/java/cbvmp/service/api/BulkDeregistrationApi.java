/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.BulkDeRegisterRequest;
import cbvmp.service.api.request.SIMCard;
import cbvmp.service.api.response.BulkDeRegisterResponse;
import cbvmp.service.data.manager.BulkDeregistrationModelManager;
import cbvmp.service.data.model.BulkDeregistrationModel;
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
@Path("/bulk")
public class BulkDeregistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @Path("/deregistration")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<BulkDeRegisterResponse> deregisterBulk(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, BulkDeRegisterRequest bulkRequest) {
        // store the request log

        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = BulkDeRegisterRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(bulkRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(bulkRequest));        
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
//        String jsonInString = gson.toJson(bulkRequest);
        
        
        
        String ipAddress = request.getRemoteAddr();
        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);
        BulkDeRegisterResponse response = new BulkDeRegisterResponse();
        List<BulkDeRegisterResponse> responseList = new ArrayList();
        SIMCard[] simList = bulkRequest.getSimList();

        ArrayList<String> validationErrors = null;
        String ecId = null;
        Date ecTime = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(bulkRequest, BulkDeRegisterRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful",gson.toJson(logHash) , "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                //logger.error(validationErrors.get(1));
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                responseList.add(response);
                return responseList;
            } else if (simList.length > 100) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Bulk request too big ");
                logger.error(generateRequestLogString);
                //logger.error("Bulk request too big");
                response.setErrorCode(ApplicationError.EXP010210);
                response.setErrorDescription(ApplicationError.EXP010210_DESC);
                responseList.add(response);
                return responseList;

            }

            //StringBuilder errors = new StringBuilder();
            List<String> errors = new ArrayList<String>();

            for (int i = 0; i < simList.length; i++) {

                if (!(Validation.isValidMSISDN(simList[i].getMsisdn()))) {

                    String message = String.format("msisdn %s invalid format", simList[i].getMsisdn());
                    //errors.add(message);
                    response = new BulkDeRegisterResponse();
                    response.setErrorCode(ApplicationError.EXP010208);
                    response.setErrorDescription(message);
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful",gson.toJson(logHash) , "0", message);
                    logger.error(generateRequestLogString);

                    //logger.debug("error" + message);
                    responseList.add(response);

                }

            }
            //mssisdn validation
            if (responseList.isEmpty() == false) {
                return responseList;
            }

            // Authendication 
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful",gson.toJson(logHash) , "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                //logger.error(tokenInformation[2]);
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                responseList.add(response);
                return responseList;
            }

            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
//        String schema ="CMPO_GP";
//        Integer cmpoNo =1;

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);
    // allowed ip connection check
//            if(!TypeList.isValidIp(schema, ipAddress))
//            {
//                response.setErrorCode(ApplicationError.EXP010204);
//                response.setErrorDescription(ApplicationError.EXP010204_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp generation attempt", "unsuccessful"," " , "1","Unauthorized IP access by"+schema);
//                logger.error(generateRequestLogString);
//                responseList.add(response);
//                return responseList;              
//            }

            StringBuilder errorFound = new StringBuilder();
            BulkDeregistrationModelManager bulkModelManager = new BulkDeregistrationModelManager();
            BulkDeregistrationModel bulkModel = null;

            for (int i = 0; i < simList.length; i++) {

                bulkModel = bulkModelManager.bulkDereg(
                        schema,
                        cmpoNo,
                        simList[i].getMsisdn(),
                        bulkRequest.getBatchId()
                );
                if (bulkModel == null) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Bulk deregistration attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                    logger.error(generateRequestLogString);

                    //logger.error("Database Transaction error");
                    response.setIsError(1);
                    response.setErrorCode(ApplicationError.EXP010218);
                    response.setErrorDescription(ApplicationError.EXP010218_DESC);
                    responseList.add(response);
                    break;

                } else {
                    String message = String.format("%s", simList[i].getMsisdn());
                    response = new BulkDeRegisterResponse();
                    response.setSimInfoNo(bulkModel.getSimInfoNo());
                    response.setMsisdn(message);
                    response.setIsError(bulkModel.getIsError());
                    response.setErrorCode(bulkModel.getErrorCode());
                    response.setErrorDescription(bulkModel.getErrorDesc());
                    if (bulkModel.getIsError() == 0) {
                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Bulk deregistration attempt", "successful", gson.toJson(logHash), "0", " ");
                        logger.info(generateRequestLogString);
                    } else {
                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Bulk deregistration attempt", "unsuccessful",gson.toJson(logHash) , "0", bulkModel.getErrorDesc());
                        logger.error(generateRequestLogString);
                    }

                    //logger.error(bulkModel.getErrorDesc());
                    responseList.add(response);
                    //return responseList;
                }

            }

            if (!responseList.isEmpty()) {
                //response.setErrorDescription(errorFound.toString());
                //responseList.add(response);
                return responseList;
            }

            return responseList;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);
            //logger.warn("Validation Didn't work; Exception:" + ex.getMessage());

        }

        return responseList;

    }

    @GET
    @Path("/deregistration")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deregisterCorporateSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk deregistration attempt", "unsuccessful",new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();

    }

}
