/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.SimTransferRequest;
import cbvmp.service.api.response.GeneralResponse;
import cbvmp.service.api.response.SimTransferResponse;
import cbvmp.service.data.manager.SimTransferModelManager;
import cbvmp.service.data.model.SimTransferModel;
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
 * @author tanbir
 */
@Path("/sim")
public class SimTransferApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @Path("/transfer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SimTransferResponse transferSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, SimTransferRequest simTransferRequest) {
        // store the request log
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = SimTransferRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(simTransferRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(simTransferRequest));        
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
        SimTransferResponse response = new SimTransferResponse();
        String ecId = null, ecTrn = null;
        Date ecTime = null, expTime = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(simTransferRequest, SimTransferRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            }

            if (!(simTransferRequest.getDestDocTypeNo().equals(1)|| simTransferRequest.getDestDocTypeNo().equals(4) || simTransferRequest.getDestDocTypeNo().equals(5)) && (Validation.isNullFound(simTransferRequest.getDestExpTime()))) {
                response.setErrorCode(ApplicationError.EXP010216);
                response.setErrorDescription("required field 'dest_exp_time' is null");
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Expire Date field is required");
                logger.error(generateRequestLogString);
                //logger.error("Expire Date field is required");
                return response;
            } else if (!TypeList.typeFound(simTransferRequest.getSrcDocTypeNo()) || !TypeList.typeFound(simTransferRequest.getDestDocTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);
                //logger.error("Invalid document type no");
                return response;
            } /*else if (simTransferRequest.getDestDocTypeNo().equals(1) && (Validation.isNullFound(simTransferRequest.getEcSessionID()) || Validation.isNullFound(simTransferRequest.getEcSessTime()) || Validation.isNullFound(simTransferRequest.getEcTrn()))) {
                response.setErrorCode(ApplicationError.EXP010215);
                response.setErrorDescription("required field 'dest_ec_sess_id' or 'dest_ec_sess_time' or 'dest_ec_trn_id' is null");
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful"," ", "0",  "EC fields are required");
                logger.error(generateRequestLogString);
                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                return response;
            }*/ // date format validation
            //            else if (!(Validation.isValidDate(simTransferRequest.getRegDate())) || (!simTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidDate(simTransferRequest.getDestExpTime()))) || (simTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidDate(simTransferRequest.getEcSessTime()))) || !(Validation.isValidDob(simTransferRequest.getDestDob()))) {
            else if (!(Validation.isValidDate(simTransferRequest.getRegDate())) || (!(simTransferRequest.getDestDocTypeNo().equals(1)||simTransferRequest.getDestDocTypeNo().equals(4)|| simTransferRequest.getDestDocTypeNo().equals(5)) && !(Validation.isValidDate(simTransferRequest.getDestExpTime()))) || !(Validation.isValidDob(simTransferRequest.getDestDob()))) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);
                //logger.error("Date format validation error");
                return response;
            } //mssisdn validation
            else if (!(Validation.isValidMSISDN(simTransferRequest.getMsisdn()))) {
                response.setErrorCode(ApplicationError.EXP010208);
                response.setErrorDescription(ApplicationError.EXP010208_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid MSISDN format");
                logger.error(generateRequestLogString);
                //logger.error("MSISDN format not matched");
                return response;
            } //NID check
            else if (((simTransferRequest.getSrcDocTypeNo().equals(1) && !(Validation.isValidNID(simTransferRequest.getSrcDocTypeID()))) || (simTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidNID(simTransferRequest.getDestDocTypeID())))) || ((simTransferRequest.getSrcDocTypeNo().equals(5) && !(Validation.isValidSmartNID(simTransferRequest.getSrcDocTypeID()))) || (simTransferRequest.getDestDocTypeNo().equals(5) && !(Validation.isValidSmartNID(simTransferRequest.getDestDocTypeID())))) ) {
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);
                logger.error("NID format validation error");
                return response;
            } else if ((simTransferRequest.getDestDocTypeNo().equals(simTransferRequest.getSrcDocTypeNo())) && (simTransferRequest.getSrcDocTypeID().equals(simTransferRequest.getDestDocTypeID()))) {
                response.setErrorCode(ApplicationError.EXP010214);
                response.setErrorDescription(ApplicationError.EXP010214_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Same document ID can not be used");
                logger.error(generateRequestLogString);
                //logger.error("Same NID can not be used");
                return response;
            } //OTP check
            else if (!TypeList.checkValidForeign(simTransferRequest.getDestDocTypeNo(), simTransferRequest.getDestForeignFlag())) {
                response.setErrorCode(ApplicationError.EXP010213);
                response.setErrorDescription(ApplicationError.EXP010213_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid foriegn flag");
                logger.error(generateRequestLogString);
                //logger.error("Invalid foriegn flag");
                return response;
            } else if (!Validation.isEcVerified(simTransferRequest.getIsVerified())) {
                response.setErrorCode(ApplicationError.EXP010219);
                response.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);
                //logger.error("Invalid 'is_verified' entry");
                return response;
            }

            //ec session optional
            if (simTransferRequest.getDestDocTypeNo().equals(1) ||  simTransferRequest.getDestDocTypeNo().equals(5) ) {
                ecId = simTransferRequest.getEcSessionID();
                ecTime = Validation.formatDate(simTransferRequest.getEcSessTime(), 0);
                ecTrn = simTransferRequest.getEcTrn();
                expTime = null;
                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;
                expTime = Validation.formatDate(simTransferRequest.getDestExpTime(), 0);
                //System.out.println("2" + expTime);
            }

            // Authendication 
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                return response;
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
//                return response;
//            }


            SimTransferModelManager simTransferModelManager = new SimTransferModelManager();
            SimTransferModel simTransferModel = simTransferModelManager.transferSim(
                    schema,
                    5,
                    simTransferRequest.getSrcDocTypeNo(),
                    simTransferRequest.getSrcDocTypeID(),
                    simTransferRequest.getDestDocTypeNo(),
                    simTransferRequest.getDestDocTypeID(),
                    simTransferRequest.getRetailId(),
                    simTransferRequest.getMsisdn(),
                    Validation.formatDate(simTransferRequest.getDestDob(), 1),//date type
                    simTransferRequest.getCmpoTrnId(),
                    Validation.formatDate(simTransferRequest.getRegDate(), 0), //date type
                    simTransferRequest.getIsVerified(),
                    ecId,
                    cmpoNo,
                    simTransferRequest.getDestIMSI(),
                    simTransferRequest.getOtpNo(),
                    simTransferRequest.getDestForeignFlag(),
                    expTime,
                    ecTime,
                    ecTrn
            );

            if (!(simTransferModel == null)) {
                if (simTransferModel.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim transfer attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", simTransferModel.getErrorDesc());
                    logger.error(generateRequestLogString);
                }

                response.setSimInfoNo(simTransferModel.getSimInfoNo());
                response.setIsError(simTransferModel.getIsError());
                response.setErrorCode(simTransferModel.getErrorCode());
                response.setErrorDescription(simTransferModel.getErrorDesc());

            } else {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010208);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);

            }

            /*if (simTransferModel.getIsError() != null) {
                
                response.setSimInfoNo(simTransferModel.getSimInfoNo());

            } else {
                logger.error(simTransferModel.getErrorDesc());
                response.setSimInfoNo(simTransferModel.getSimInfoNo());
                response.setIsError(simTransferModel.getIsError());
                response.setErrorCode(simTransferModel.getErrorCode());
                response.setErrorDescription(simTransferModel.getErrorDesc());
            }*/
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        return response;

    }

    @GET
    @Path("/transfer")
    @Produces(MediaType.TEXT_PLAIN)
    public Response transferSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim transfer attempt", "unsuccessful", new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
        logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();

    }

}
