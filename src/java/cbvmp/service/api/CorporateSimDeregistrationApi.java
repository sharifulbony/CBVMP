/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.CorporateSimDeregistrationRequest;
import cbvmp.service.api.request.SIMCard;
import cbvmp.service.api.response.CorporateSimDeregistrationResponse;
import cbvmp.service.data.manager.CorporateNIDModelManager;
import cbvmp.service.data.manager.CorporateSimDeregistrationModelManager;
import cbvmp.service.data.manager.CorporateSimTransferModelManager;
import cbvmp.service.data.model.CorporateNIDModel;
import cbvmp.service.data.model.CorporateSimDeregistrationModel;
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
public class CorporateSimDeregistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @Path("/sim/deregistration")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<CorporateSimDeregistrationResponse> deregisterCorporateSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, CorporateSimDeregistrationRequest corporateSimDeregistrationRequest) {
        // store the request log
        HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = CorporateSimDeregistrationRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(corporateSimDeregistrationRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(corporateSimDeregistrationRequest));        
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
        CorporateSimDeregistrationResponse response = new CorporateSimDeregistrationResponse();
        List<CorporateSimDeregistrationResponse> responseList = new ArrayList();
        SIMCard[] simList = corporateSimDeregistrationRequest.getSimList();

        ArrayList<String> validationErrors = null;
        String ecId = null, ecTrn = null;
        Date ecTime = null;
        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(corporateSimDeregistrationRequest, CorporateSimDeregistrationRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);

                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                responseList.add(response);
                return responseList;
            } else if (simList.length > 100) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", "Bulk request too big ");
                logger.error(generateRequestLogString);
                response.setErrorCode(ApplicationError.EXP010210);
                response.setErrorDescription(ApplicationError.EXP010210_DESC);
                responseList.add(response);
                return responseList;

            }
            if (!TypeList.typeFound(corporateSimDeregistrationRequest.getDocTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);

                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);
                //logger.error("");
                responseList.add(response);
                return responseList;
            } 
            /*else if (corporateSimDeregistrationRequest.getDocTypeNo().equals(1) && (Validation.isNullFound(corporateSimDeregistrationRequest.getEcSessId()) || Validation.isNullFound(corporateSimDeregistrationRequest.getEcSessTime()) || Validation.isNullFound(corporateSimDeregistrationRequest.getEcTrn()))) {
                response.setErrorCode(ApplicationError.EXP010215);
                response.setErrorDescription(ApplicationError.EXP010215_DESC);

                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", " ", "0", "EC fields are required");
                logger.error(generateRequestLogString);
                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                responseList.add(response);
                return responseList;
            }*/ // date format validation
//            else if (!(Validation.isValidDate(corporateSimDeregistrationRequest.getDeRegDate())) || (corporateSimDeregistrationRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(corporateSimDeregistrationRequest.getEcSessTime())))) {
            else if (!(Validation.isValidDate(corporateSimDeregistrationRequest.getDeRegDate())) ) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

                //logger.error("Date format validation error");
                responseList.add(response);
                return responseList;
            } else if (!Validation.isEcVerified(corporateSimDeregistrationRequest.getIsVerified())) {
                response.setErrorCode(ApplicationError.EXP010219);
                response.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);

                //logger.error("Invalid 'is_verified' entry");
                responseList.add(response);
                return responseList;

            }//NID format check
            else if ((corporateSimDeregistrationRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(corporateSimDeregistrationRequest.getDocumentId())))  || (corporateSimDeregistrationRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(corporateSimDeregistrationRequest.getDocumentId()))) ) {
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                responseList.add(response);
                return responseList;
            }
            //StringBuilder errors = new StringBuilder();
            List<String> errors = new ArrayList<String>();
            
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt for test purpose", "start process", gson.toJson(logHash), "0", "total sim found"+simList.length);
            logger.debug(generateRequestLogString);            
            System.out.println("Corporate Sim deregistration attempt for test purpose"+" " + simList.length );

            for (int i = 0; i < simList.length; i++) {

                if (!(Validation.isValidMSISDN(simList[i].getMsisdn()))) {

                    String message = String.format("msisdn %s invalid format", simList[i].getMsisdn());
                    //errors.add(message);
                    response = new CorporateSimDeregistrationResponse();
                    response.setErrorCode(ApplicationError.EXP010208);
                    response.setErrorDescription(message);
                    //logger.debug("error" + message);
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0",message);
                    logger.error(generateRequestLogString);
                    responseList.add(response);

                }

            }
            //mssisdn validation
            if (responseList.isEmpty() == false) {
                return responseList;
            }
            //ec session optional
            if (corporateSimDeregistrationRequest.getDocTypeNo().equals(1) || corporateSimDeregistrationRequest.getDocTypeNo().equals(5)) {
                ecId = corporateSimDeregistrationRequest.getEcSessId();
                ecTime = Validation.formatDate(corporateSimDeregistrationRequest.getEcSessTime(), 0);
                ecTrn = corporateSimDeregistrationRequest.getEcTrn();

                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;

                //System.out.println("2" + expTime);
            }

            // Authendication 
            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
                logger.error(generateRequestLogString);

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
            CorporateSimDeregistrationModelManager corporateSimTransferModelManager = new CorporateSimDeregistrationModelManager();
            CorporateSimDeregistrationModel corporateSimDeregistrationModel = null;
            CorporateNIDModel corporateModel = null;
            CorporateNIDModelManager corporateModelManager = new CorporateNIDModelManager();

            corporateModel = corporateModelManager.nidFound(schema, corporateSimDeregistrationRequest.getDocumentId());

            if (corporateModel != null && corporateModel.getIsError() == 0) {
                for (int i = 0; i < simList.length; i++) {

                    corporateSimDeregistrationModel = corporateSimTransferModelManager.deregisterCorporateSim(
                            schema,
                            3,
                            corporateSimDeregistrationRequest.getDocTypeNo(),
                            corporateSimDeregistrationRequest.getDocumentId(),
                            corporateSimDeregistrationRequest.getRetailId(),
                            simList[i].getMsisdn(),
                            simList[i].getTrnId(),
                            Validation.formatDate(corporateSimDeregistrationRequest.getDeRegDate(), 0),
                            corporateSimDeregistrationRequest.getIsVerified(),
                            ecId,
                            cmpoNo,
                            ecTime,
                            ecTrn
                    );
                    if (corporateSimDeregistrationModel == null) {

                        generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "1", "Database Transaction error ");
                        logger.error(generateRequestLogString);
                        response.setIsError(1);
                        response.setErrorCode(ApplicationError.EXP010218);
                        response.setErrorDescription(ApplicationError.EXP010218_DESC);
                        responseList.add(response);
                        break;

                    } else {
                        String message = String.format("%s", simList[i].getMsisdn());
                        response = new CorporateSimDeregistrationResponse();
                        response.setSimInfoNo(corporateSimDeregistrationModel.getSimInfoNo());
                        response.setMsisdn(message);
                        response.setIsError(corporateSimDeregistrationModel.getIsError());
                        response.setErrorCode(corporateSimDeregistrationModel.getErrorCode());
                        response.setErrorDescription(corporateSimDeregistrationModel.getErrorDesc());
                        if (corporateSimDeregistrationModel.getIsError() == 0) {
                            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim deregistration attempt", "successful", gson.toJson(logHash), "0", " ");
                            logger.info(generateRequestLogString);
                        } else {
                            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim deregistration attempt", "unsuccessful",gson.toJson(logHash), "0", corporateSimDeregistrationModel.getErrorDesc());
                            logger.error(generateRequestLogString);
                        }
                        
                        

                        responseList.add(response);
                        //return responseList;
                    }

                }
            } else if (corporateModel == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
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
                
                System.out.println("Corporate Sim deregistration attempt for test purpose end"+" " + responseList.size() );
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt for test purpose", "end process", " ", "0", "total response found"+responseList.size());
                logger.debug(generateRequestLogString);
                return responseList;
            }
//            if (corporateSimDeregistrationModel != null) {
//                response.setSimInfoNo(corporateSimDeregistrationModel.getSimInfoNo());
//                response.setIsError(corporateSimDeregistrationModel.getIsError());
//                response.setErrorCode(corporateSimDeregistrationModel.getErrorCode());
//                response.setErrorDescription(corporateSimDeregistrationModel.getErrorDesc());
//
//            } else {
//                logger.error("Database Transaction error");
//                response.setErrorCode(corporateSimDeregistrationModel.getErrorCode());
//                response.setErrorDescription(corporateSimDeregistrationModel.getErrorDesc());
//            }
            return responseList;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful", gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
        
        
        
        return responseList;

    }

    @GET
    @Path("/sim/deregistration")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deregisterCorporateSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Corporate Sim deregistration attempt", "unsuccessful",new Gson().toJson(request).toString() , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();

    }

}
