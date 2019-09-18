/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.SimDeregistrationRequest;
import cbvmp.service.api.response.SimDeregistrationResponse;
import cbvmp.service.data.manager.SimDeregistrationModelManager;
import cbvmp.service.data.model.SimDeregistrationModel;
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
 * @author SIT
 */
@Path("/sim")
public class SimDeregistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/deregistration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SimDeregistrationResponse deRegisterSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, SimDeregistrationRequest deregRequest) {
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = SimDeregistrationRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(deregRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(deregRequest));        
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
        ArrayList<String> validationErrors = null;
        SimDeregistrationResponse deRegResponse = new SimDeregistrationResponse();

        String ecId = null, ecTrn = null;
        Date ecTime = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(deregRequest, SimDeregistrationRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                deRegResponse.setErrorCode(validationErrors.get(0));
                deRegResponse.setErrorDescription(validationErrors.get(1));
                return deRegResponse;
            }

            if (!TypeList.typeFound(deregRequest.getDocTypeNo())) {
                deRegResponse.setErrorCode(ApplicationError.EXP010217);
                deRegResponse.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",gson.toJson(logHash)  , "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return deRegResponse;
            } // date format validation
            /*else if (deregRequest.getDocTypeNo().equals(1) && (Validation.isNullFound(deregRequest.getEcSessId()) || Validation.isNullFound(deregRequest.getEcSessTime()) || Validation.isNullFound(deregRequest.getEcTrnId()))) {
                deRegResponse.setErrorCode(ApplicationError.EXP010215);
                deRegResponse.setErrorDescription(ApplicationError.EXP010215_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful", " ", "0", "EC fields are required");
                logger.error(generateRequestLogString);

                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                return deRegResponse;
            }*/
            //            else if (!(Validation.isValidDate(deregRequest.getDeRegDate())) || ( deregRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(deregRequest.getEcSessTime()))) ) {
            else if (!(Validation.isValidDate(deregRequest.getDeRegDate())) ) {
                deRegResponse.setErrorCode(ApplicationError.EXP010207);
                deRegResponse.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

                //logger.error("Date format validation error");
                return deRegResponse;
            } //mssisdn validation
            else if (!(Validation.isValidMSISDN(deregRequest.getMsisdn()))) {
                deRegResponse.setErrorCode(ApplicationError.EXP010208);
                deRegResponse.setErrorDescription(ApplicationError.EXP010208_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",  gson.toJson(logHash), "0", "Invalid MSISDN format");
                logger.error(generateRequestLogString);

                //logger.error("MSISDN format not matched");
                return deRegResponse;
            } //NID check
            else if ((deregRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(deregRequest.getDocumentId()))) || (deregRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(deregRequest.getDocumentId())))) {
                deRegResponse.setErrorCode(ApplicationError.EXP010209);
                deRegResponse.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",  gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                return deRegResponse;
            } else if (!Validation.isEcVerified(deregRequest.getIsVerified())) {
                deRegResponse.setErrorCode(ApplicationError.EXP010219);
                deRegResponse.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",  gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);

                //logger.error("Invalid 'is_verified' entry");
                return deRegResponse;
            }
            //OTP check

            //ec session optional
            if (deregRequest.getDocTypeNo().equals(1) || deregRequest.getDocTypeNo().equals(5) ) {
                ecId = deregRequest.getEcSessId();
                ecTime = Validation.formatDate(deregRequest.getEcSessTime(), 0);
                ecTrn = deregRequest.getEcTrnId();

                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;

                //System.out.println("2" + expTime);
            }
            // Authentication code
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                deRegResponse.setIsError(1);
                deRegResponse.setErrorCode(tokenInformation[1]);
                deRegResponse.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",  gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                return deRegResponse;
            }

            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
            
             // allowed ip connection check
//            if(!TypeList.isValidIp(schema, ipAddress))
//            {
//                deRegResponse.setErrorCode(ApplicationError.EXP010204);
//                deRegResponse.setErrorDescription(ApplicationError.EXP010204_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp generation attempt", "unsuccessful"," " , "1","Unauthorized IP access by"+schema);
//                logger.error(generateRequestLogString);
//                return deRegResponse;
//            }

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);

            SimDeregistrationModelManager simDeregManager = new SimDeregistrationModelManager();
            SimDeregistrationModel simDeregList = simDeregManager.deRegisterSim(
                    schema,
                    3,
                    deregRequest.getDocTypeNo(),
                    deregRequest.getDocumentId(),
                    deregRequest.getRetailId(),
                    deregRequest.getMsisdn(),
                    deregRequest.getCmpoTrnId(),
                    Validation.formatDate(deregRequest.getDeRegDate(), 0),
                    deregRequest.getIsVerified(),
                    ecId,
                    cmpoNo,
                    deregRequest.getOtpNo(),
                    ecTime,
                    ecTrn
            );

            if (simDeregList != null) {

                if (simDeregList.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim deregistration attempt", "successful",  gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim deregistration attempt", "unsuccessful",  gson.toJson(logHash), "0", simDeregList.getErrorDesc());
                    logger.error(generateRequestLogString);
                }

                deRegResponse.setIsError(simDeregList.getIsError());
                deRegResponse.setSimInfoNo(simDeregList.getSimInfoNo());
                deRegResponse.setErrorCode(simDeregList.getErrorCode());
                deRegResponse.setErrorDescription(simDeregList.getErrorDesc());

            } else {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);
                deRegResponse.setIsError(1);
                deRegResponse.setErrorCode(ApplicationError.EXP010218);
                deRegResponse.setErrorDescription(ApplicationError.EXP010218_DESC);

            }

            return deRegResponse;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error deRegResponse
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return deRegResponse;
    }

    @GET
    @Path("/deregistration")
    public Response deRegisterSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim deregistration attempt", "unsuccessful",new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
        logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
}
