/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.CorporateSimTransferRequest;
import cbvmp.service.api.request.SIMCard;
import cbvmp.service.api.response.CorporateSimTransferResponse;
import cbvmp.service.data.manager.CorporateNIDModelManager;
import cbvmp.service.data.manager.CorporateSimTransferModelManager;
import cbvmp.service.data.model.CorporateNIDModel;
import cbvmp.service.data.model.CorporateSimTransferModel;
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
@Path("/corporate")
public class CorporateSimTransferApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/sim/transfer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CorporateSimTransferResponse> transferCorporateSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, CorporateSimTransferRequest corporateTransferRequest) {
        
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = CorporateSimTransferRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(corporateTransferRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(corporateTransferRequest));        
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
        SIMCard[] simList = corporateTransferRequest.getSimList();

        CorporateSimTransferResponse response = new CorporateSimTransferResponse();
        List<CorporateSimTransferResponse> responseList = new ArrayList();
        String ecId = null, ecTrn = null;
        Date ecTime = null, expTime = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(corporateTransferRequest, CorporateSimTransferRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                responseList.add(response);
                return responseList;
            } else if (simList.length > 100) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Bulk request too big ");
                logger.error(generateRequestLogString);
                response.setErrorCode(ApplicationError.EXP010210);
                response.setErrorDescription(ApplicationError.EXP010210_DESC);
                responseList.add(response);
                return responseList;
            }
            if (!(corporateTransferRequest.getDestDocTypeNo().equals(1) ||corporateTransferRequest.getDestDocTypeNo().equals(4) || corporateTransferRequest.getDestDocTypeNo().equals(5)) && (Validation.isNullFound(corporateTransferRequest.getDestExpTime()))) {
                response.setErrorCode(ApplicationError.EXP010216);
                response.setErrorDescription("required field 'dest_exp_time' is null");
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Expire Date field is required");
                logger.error(generateRequestLogString);

                //logger.error("Expire Date field is required");
                responseList.add(response);
                return responseList;
            } else if (!TypeList.typeFound(corporateTransferRequest.getDocTypeNo()) || !TypeList.typeFound(corporateTransferRequest.getDestDocTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                responseList.add(response);
                return responseList;
            } /*else if (corporateTransferRequest.getDestDocTypeNo().equals(1) && (Validation.isNullFound(corporateTransferRequest.getEcSessID()) || Validation.isNullFound(corporateTransferRequest.getEcSessTime()) || Validation.isNullFound(corporateTransferRequest.getEcTrn()))) {
                response.setErrorCode(ApplicationError.EXP010215);
                response.setErrorDescription("required field 'dest_ec_sess_id' or 'dest_ec_sess_time' or 'dest_ec_trn_id' is null");
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful"," " , "0", "EC fields are required");
                logger.error(generateRequestLogString);

                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                responseList.add(response);
                return responseList;
            }*/ // date format validation
            //            else if (!(Validation.isValidDate(corporateTransferRequest.getRegDate())) || (!corporateTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidDate(corporateTransferRequest.getDestExpTime()))) || (corporateTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidDate(corporateTransferRequest.getEcSessTime()))) || !(Validation.isValidDob(corporateTransferRequest.getDob()))) {
            else if (!(Validation.isValidDate(corporateTransferRequest.getRegDate())) || (!(corporateTransferRequest.getDestDocTypeNo().equals(1) ||corporateTransferRequest.getDestDocTypeNo().equals(4) || corporateTransferRequest.getDestDocTypeNo().equals(5)) && !(Validation.isValidDate(corporateTransferRequest.getDestExpTime()))) || !(Validation.isValidDob(corporateTransferRequest.getDob()))) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

                //logger.error("Date format validation error");
                responseList.add(response);
                return responseList;
            } //mssisdn validation
            /*else if (!(Validation.isValidMSISDN(corporateTransferRequest.getMsisdn()))) {
                response.setErrorCode(ApplicationError.EXP010208);
                response.setErrorDescription(ApplicationError.EXP010208_DESC);
                logger.error("MSISDN format not matched");
                responseList.add(response);                return responseList;
            }*///NID check
            else if (((corporateTransferRequest.getDestDocTypeNo().equals(1) && !(Validation.isValidNID(corporateTransferRequest.getDestDocId()))) || (corporateTransferRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(corporateTransferRequest.getDocID())))) || ((corporateTransferRequest.getDestDocTypeNo().equals(5) && !(Validation.isValidSmartNID(corporateTransferRequest.getDestDocId()))) || (corporateTransferRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(corporateTransferRequest.getDocID())))) ){
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                responseList.add(response);
                return responseList;
            } else if ((corporateTransferRequest.getDestDocTypeNo().equals(corporateTransferRequest.getDocTypeNo())) && (corporateTransferRequest.getDocID().equals(corporateTransferRequest.getDestDocId()))) {
                response.setErrorCode(ApplicationError.EXP010214);
                response.setErrorDescription(ApplicationError.EXP010214_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Same document ID used");
                logger.error(generateRequestLogString);

                //logger.error("Same NID can not be used");
                responseList.add(response);
                return responseList;
            } else if (!Validation.isEcVerified(corporateTransferRequest.getIsVerify())) {
                response.setErrorCode(ApplicationError.EXP010219);
                response.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);

                //logger.error("Invalid 'is_verified' entry");
                responseList.add(response);
                return responseList;

            } else if (!TypeList.checkValidForeign(corporateTransferRequest.getDestDocTypeNo(), corporateTransferRequest.getDestForeignFlag())) {
                response.setErrorCode(ApplicationError.EXP010213);
                response.setErrorDescription(ApplicationError.EXP010213_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid foriegn flag");
                logger.error(generateRequestLogString);

                //logger.error("Invalid foriegn flag");
                responseList.add(response);
                return responseList;
            }
            List<String> errors = new ArrayList<String>();

            for (int i = 0; i < simList.length; i++) {
                if (!(Validation.isValidMSISDN(simList[i].getMsisdn()))) {

                    String message = String.format("msisdn %s invalid format", simList[i].getMsisdn());
                    //errors.add(message);
                    response = new CorporateSimTransferResponse();
                    response.setErrorCode(ApplicationError.EXP010208);
                    response.setErrorDescription(message);
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", message);
                    logger.error(generateRequestLogString);

                    //logger.debug("error" + message);
                    responseList.add(response);

                }

            }

            //mssisdn validation
            if (responseList.isEmpty() == false) {
                return responseList;
            }
            if (corporateTransferRequest.getDestDocTypeNo().equals(1) || corporateTransferRequest.getDestDocTypeNo().equals(5)) {
                ecId = corporateTransferRequest.getEcSessID();
                ecTime = Validation.formatDate(corporateTransferRequest.getEcSessTime(), 0);
                ecTrn = corporateTransferRequest.getEcTrn();
                expTime = null;
                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;
                expTime = Validation.formatDate(corporateTransferRequest.getDestExpTime(), 0);
                //System.out.println("2" + expTime);
            }

// Authentication code
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);
                responseList.add(response);
                return responseList;
            }
            String[] tokenParts = tokenInformation[0].split(":");
            String schema = tokenParts[0];
            Integer cmpoNo = Integer.parseInt(tokenParts[1]);

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

            CorporateSimTransferModelManager corporateSimModelManager = new CorporateSimTransferModelManager();
            CorporateSimTransferModel corpSimTransferList = null;

            StringBuilder errorFound = new StringBuilder();
            CorporateNIDModel corporateModel = null;
            CorporateNIDModelManager corporateModelManager = new CorporateNIDModelManager();

            corporateModel = corporateModelManager.nidFound(schema, corporateTransferRequest.getDocID());

            if (corporateModel != null && corporateModel.getIsError() == 0) {
                for (int i = 0; i < simList.length; i++) {
                    corpSimTransferList = corporateSimModelManager.transferCorporateSim(
                            schema,
                            5,
                            corporateTransferRequest.getDocTypeNo(),
                            corporateTransferRequest.getDocID(),
                            corporateTransferRequest.getDestDocTypeNo(),
                            corporateTransferRequest.getDestDocId(),
                            corporateTransferRequest.getRetailID(),
                            simList[i].getMsisdn(),
                            Validation.formatDate(corporateTransferRequest.getDob(), 1),
                            simList[i].getTrnId(),
                            Validation.formatDate(corporateTransferRequest.getRegDate(), 0),
                            corporateTransferRequest.getIsVerify(),
                            ecId,
                            cmpoNo,
                            simList[i].getIMSI(),
                            corporateTransferRequest.getDestForeignFlag(),
                            ecTime,
                            expTime,
                            ecTrn
                    );
                    if (corpSimTransferList == null) {

                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                        logger.error(generateRequestLogString);
                        response.setIsError(1);
                        response.setErrorCode(ApplicationError.EXP010218);
                        response.setErrorDescription(ApplicationError.EXP010218_DESC);
                        break;

                    } else {
                        String message = String.format("%s", simList[i].getMsisdn());
                        response = new CorporateSimTransferResponse();
                        response.setSimInfoNo(corpSimTransferList.getSimInfoNo());
                        response.setMsisdn(message);
                        response.setIsError(corpSimTransferList.getIsError());
                        response.setErrorCode(corpSimTransferList.getErrCode());
                        response.setErrorDescription(corpSimTransferList.getErrDesc());
                        if (corpSimTransferList.getIsError() == 0) {
                            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim transfer attempt", "successful",gson.toJson(logHash), "0", " ");
                            logger.info(generateRequestLogString);
                        } else {
                            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "0", corpSimTransferList.getErrDesc());
                            logger.error(generateRequestLogString);
                        }
                        responseList.add(response);
                        //return responseList;
                    }

                }
            } else if (corporateModel == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim transfer attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);
                responseList.add(response);
            } else {
                response.setIsError(1);
                response.setErrorCode(corporateModel.getErrorCode());
                response.setErrorDescription(corporateModel.getErrorDesc());
                responseList.add(response);
            }

            if (!responseList.isEmpty()) {
                //response.setErrorDescription(errorFound.toString());
                //responseList.add(response);
                return responseList;
            }

            return responseList;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful",gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }

        return responseList;
    }

    @GET
    @Path("/sim/transfer")
    public Response registerSim(@Context HttpServletRequest request
    ) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim transfer attempt", "unsuccessful", new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
        logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
