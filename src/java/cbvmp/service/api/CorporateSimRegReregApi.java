/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.BulkRegistrationRequest;
import cbvmp.service.api.request.CorporateSimRegReregRequest;
import cbvmp.service.api.request.SIMCard;
import cbvmp.service.api.response.CorporateSimRegReregResponse;
import cbvmp.service.data.manager.CorporateNIDModelManager;
import cbvmp.service.data.manager.CorporateSimRegReregModelManager;
import cbvmp.service.data.model.CorporateNIDModel;
import cbvmp.service.data.model.CorporateSimRegReregModel;
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
 * @author SIT
 */
@Path("/corporate")
public class CorporateSimRegReregApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/sim/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CorporateSimRegReregResponse> registerCorporateSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, CorporateSimRegReregRequest regRequest) {
        
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = CorporateSimRegReregRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(regRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(regRequest));        
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
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);

        String ipAddress = request.getRemoteAddr();
        ArrayList<String> validationErrors = null;

        CorporateSimRegReregResponse regResponse = new CorporateSimRegReregResponse();
        List<CorporateSimRegReregResponse> responseList = new ArrayList();
        SIMCard[] simList = regRequest.getSimList();
        String ecId = null, ecTrn = null;
        Date ecTime = null, expTime = null;
        //BulkRegistrationRequest blkRequest = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(regRequest, CorporateSimRegReregRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                regResponse.setErrorCode(validationErrors.get(0));
                regResponse.setErrorDescription(validationErrors.get(1));
                responseList.add(regResponse);
                return responseList;
            } else if (simList.length > 100) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Bulk request too big ");
                logger.error(generateRequestLogString);
                regResponse.setErrorCode(ApplicationError.EXP010210);
                regResponse.setErrorDescription(ApplicationError.EXP010210_DESC);
                responseList.add(regResponse);
                return responseList;
            }

            if (!(regRequest.getDocTypeNo().equals(1) || regRequest.getDocTypeNo().equals(4) || regRequest.getDocTypeNo().equals(5)) && (Validation.isNullFound(regRequest.getExpTime()))) {
                regResponse.setErrorCode(ApplicationError.EXP010216);
                regResponse.setErrorDescription(ApplicationError.EXP010216_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Expire Date field is required");
                logger.error(generateRequestLogString);
                //logger.error("Expire Date field is required");
                responseList.add(regResponse);
                return responseList;
            } else if (!TypeList.typeFound(regRequest.getDocTypeNo())) {
                regResponse.setErrorCode(ApplicationError.EXP010217);
                regResponse.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                responseList.add(regResponse);
                return responseList;
            } // date format validation
            /*else if (regRequest.getDocTypeNo().equals(1) && (Validation.isNullFound(regRequest.getEcSessId()) || Validation.isNullFound(regRequest.getEcSessTime()) || Validation.isNullFound(regRequest.getEcTrn()))) {
                regResponse.setErrorCode(ApplicationError.EXP010215);
                regResponse.setErrorDescription(ApplicationError.EXP010215_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", " ", "0", "EC fields are required");
                logger.error(generateRequestLogString);

                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                responseList.add(regResponse);
                return responseList;
            }*/// date format validation
//            else if (!(Validation.isValidDate(regRequest.getRegDate())) || (regRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(regRequest.getEcSessTime()))) || (!regRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(regRequest.getExpTime()))) || !(Validation.isValidDob(regRequest.getDob()))) {
            else if (!(Validation.isValidDate(regRequest.getRegDate())) || (!(regRequest.getDocTypeNo().equals(1) ||regRequest.getDocTypeNo().equals(4) || regRequest.getDocTypeNo().equals(5)) && !(Validation.isValidDate(regRequest.getExpTime()))) || !(Validation.isValidDob(regRequest.getDob()))) {
                regResponse.setErrorCode(ApplicationError.EXP010207);
                regResponse.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

//logger.error("Date format validation error");
                responseList.add(regResponse);
                return responseList;
            } //NID check
            else if ((regRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(regRequest.getDocumentId()))) || (regRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(regRequest.getDocumentId()))) ) {
                regResponse.setErrorCode(ApplicationError.EXP010209);
                regResponse.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                responseList.add(regResponse);
                return responseList;
            } else if (!(regRequest.getPurposeNo().equals(1) || regRequest.getPurposeNo().equals(2))) {
                regResponse.setErrorCode(ApplicationError.EXP010211);
                regResponse.setErrorDescription(ApplicationError.EXP010211_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid purpose no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid purpose no");
                responseList.add(regResponse);
                return responseList;
            } else if (!TypeList.checkValidForeign(regRequest.getDocTypeNo(), regRequest.getForeignFlag())) {
                regResponse.setErrorCode(ApplicationError.EXP010213);
                regResponse.setErrorDescription(ApplicationError.EXP010213_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid foreign flag");
                logger.error(generateRequestLogString);

                //logger.error("Invalid foriegn flag");
                responseList.add(regResponse);
                return responseList;
            } else if (!Validation.isEcVerified(regRequest.getIsVerified())) {
                regResponse.setErrorCode(ApplicationError.EXP010219);
                regResponse.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);

                //logger.error("Invalid 'is_verified' entry");
                responseList.add(regResponse);
                return responseList;

            }
            //ec session optional
            if (regRequest.getDocTypeNo().equals(1) || regRequest.getDocTypeNo().equals(5) ) {
                ecId = regRequest.getEcSessId();
                ecTime = Validation.formatDate(regRequest.getEcSessTime(), 0);
                ecTrn = regRequest.getEcTrn();
                expTime = null;
                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;
                expTime = Validation.formatDate(regRequest.getExpTime(), 0);
                //System.out.println("2" + expTime);
            }

            List<String> errors = new ArrayList<String>();

            for (int i = 0; i < simList.length; i++) {
                if (!(Validation.isValidMSISDN(simList[i].getMsisdn()))) {

                    String message = String.format("msisdn %s invalid format", simList[i].getMsisdn());
                    //errors.add(message);
                    regResponse = new CorporateSimRegReregResponse();
                    regResponse.setErrorCode(ApplicationError.EXP010208);
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", message);
                    logger.error(generateRequestLogString);

                    regResponse.setErrorDescription(message);
                    //logger.debug("error" + message);
                    responseList.add(regResponse);

                }

            }

            //mssisdn validation
            if (responseList.isEmpty() == false) {
                return responseList;
            }

            // Authentication code
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                regResponse.setIsError(1);
                regResponse.setErrorCode(tokenInformation[1]);
                regResponse.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                responseList.add(regResponse);
                return responseList;
            }

            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);

//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);

      // allowed ip connection check
//            if(!TypeList.isValidIp(schema, ipAddress))
//            {
//                regResponse.setErrorCode(ApplicationError.EXP010204);
//                regResponse.setErrorDescription(ApplicationError.EXP010204_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Otp generation attempt", "unsuccessful"," " , "1","Unauthorized IP access by"+schema);
//                logger.error(generateRequestLogString);
//                responseList.add(regResponse);
//                return responseList;
//            }

            CorporateSimRegReregModelManager corporateRegManager = new CorporateSimRegReregModelManager();
            CorporateSimRegReregModel corporateSimRegList = null;

            //StringBuilder errorFound = new StringBuilder();

            /*CorporateNIDModel corporateModel = null;
            CorporateNIDModelManager corporateModelManager = new CorporateNIDModelManager();

            corporateModel = corporateModelManager.nidFound(schema, regRequest.getDocumentId());

            if (corporateModel != null && corporateModel.getIsError() == 0) {*/
            for (int i = 0; i < simList.length; i++) {

                corporateSimRegList = corporateRegManager.registrationCoporateSim(
                        schema,
                        regRequest.getPurposeNo(),
                        regRequest.getDocTypeNo(),
                        regRequest.getDocumentId(),
                        regRequest.getRetailId(),
                        simList[i].getMsisdn(),
                        Validation.formatDate(regRequest.getDob(), 1),
                        simList[i].getTrnId(),
                        Validation.formatDate(regRequest.getRegDate(), 0),
                        regRequest.getIsVerified(),
                        ecId,
                        1,
                        cmpoNo,
                        simList[i].getIMSI(),
                        regRequest.getForeignFlag(),
                        expTime,
                        ecTime,
                        ecTrn
                );

                if (corporateSimRegList == null) {

                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                    logger.error(generateRequestLogString);
                    regResponse.setIsError(1);
                    regResponse.setErrorCode(ApplicationError.EXP010218);
                    regResponse.setErrorDescription(ApplicationError.EXP010218_DESC);
                    break;

                } else {
                    String message = String.format("%s", simList[i].getMsisdn());
                    regResponse = new CorporateSimRegReregResponse();
                    regResponse.setSimNo(corporateSimRegList.getSimNo());
                    regResponse.setMsisdn(message);
                    regResponse.setIsError(corporateSimRegList.getIsError());
                    regResponse.setErrorCode(corporateSimRegList.getErrorCode());
                    regResponse.setErrorDescription(corporateSimRegList.getErrorDesc());
                    if (corporateSimRegList.getIsError() == 0) {
                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim registration attempt", "successful", gson.toJson(logHash), "0", " ");
                        logger.info(generateRequestLogString);
                    } else {
                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", corporateSimRegList.getErrorDesc());
                        logger.error(generateRequestLogString);
                    }
                    responseList.add(regResponse);
                    //return responseList;
                }
                //else {
//                        String message = String.format("'%s' ", simList[i].getMsisdn());
//                        regResponse = new CorporateSimRegReregResponse();
//                        regResponse.setIsError(corporateSimRegList.getIsError());
//                        regResponse.setErrorDescription(message);
//                        responseList.add(regResponse);
//                    }
                /*else if (corporateSimRegList.getIsError() == 1) {

                    String message = String.format("msisdn '%s' ", simList[i].getMsisdn() + " " + corporateSimRegList.getErrorDesc());
                    errorFound.append(message);
                    logger.error(errorFound.toString());

                }*/

            }
            /* } else if (corporateModel == null) {
                logger.error("Database Transaction error");
                regResponse.setErrorCode(corporateModel.getErrorCode());
                regResponse.setErrorDescription(corporateModel.getErrorDesc());
                responseList.add(regResponse);
            } else {
                regResponse.setErrorCode(corporateModel.getErrorCode());
                regResponse.setErrorDescription(corporateModel.getErrorDesc());
                responseList.add(regResponse);
            }*/
 /*if (corporateSimRegList.getIsError() == 1) {
                regResponse.setErrorCode(corporateSimRegList.getErrorCode());
                regResponse.setErrorDescription(corporateSimRegList.getErrorDesc());
                logger.error(corporateSimRegList.getErrorDesc());
                responseList.add(regResponse);
                return responseList;
            } else {
                regResponse.setIsError(corporateSimRegList.getIsError());
                responseList.add(regResponse);
            }*/
            if (!responseList.isEmpty()) {
                //regResponse.setErrorDescription(errorFound.toString());
                //responseList.add(regResponse);
                return responseList;
            }
//            } else {
//                regResponse.setIsError(corporateSimRegList.getIsError());
//                responseList.add(regResponse);
//            }
            return responseList;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error regResponse
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }

        return responseList;

    }

    @GET
    @Path("/sim/registration")
    public Response registerCorporateSim(@Context HttpServletRequest request
    ) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim registration attempt", "unsuccessful",new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
}
