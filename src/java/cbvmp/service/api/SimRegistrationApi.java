/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.SimRegistrationRequest;
import cbvmp.service.api.response.SimRegistrationResponse;
import cbvmp.service.data.manager.SimRegistrationModelManager;
import cbvmp.service.data.model.SimRegistrationModel;
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
@Path("/sim")
public class SimRegistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SimRegistrationResponse registerSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, SimRegistrationRequest simRegRequest) {

//        long start = System.nanoTime();


HashMap<String, Object> logHash = new HashMap();       
      
        Field[] fields = SimRegistrationRequest.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.get(simRegRequest) ;
                XmlElement annotation = field.getAnnotation(XmlElement.class);
                logHash.put(annotation.name(), field.get(simRegRequest));        
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

        SimRegistrationResponse response = new SimRegistrationResponse();
        ArrayList<String> validationErrors = null;
        String ecId = null, ecTrn = null;
        Date ecTime = null, expTime = null;
        // Authentication code
        try {
            validationErrors = (ArrayList) ApplicationError.validateRequired(simRegRequest, SimRegistrationRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            }

            if (!(simRegRequest.getDocTypeNo().equals(1)|| simRegRequest.getDocTypeNo().equals(4) || simRegRequest.getDocTypeNo().equals(5)) && (Validation.isNullFound(simRegRequest.getExpDate()))) {
                response.setErrorCode(ApplicationError.EXP010216);
                response.setErrorDescription(ApplicationError.EXP010216_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Expire Date field is required");
                logger.error(generateRequestLogString);

                //logger.error("Expire Date field is required");
                return response;
            } else if (!TypeList.typeFound(simRegRequest.getDocTypeNo())) {
                response.setErrorCode(ApplicationError.EXP010217);
                response.setErrorDescription(ApplicationError.EXP010217_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid document type no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid document type no");
                return response;
            } // date format validation
            /*else if (simRegRequest.getDocTypeNo().equals(1) && (Validation.isNullFound(simRegRequest.getEcSessID()) || Validation.isNullFound(simRegRequest.getEcSessTime()) || Validation.isNullFound(simRegRequest.getEcTrnId()))) {
                response.setErrorCode(ApplicationError.EXP010215);
                response.setErrorDescription(ApplicationError.EXP010215_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful"," " , "0","EC fields are required");
                logger.error(generateRequestLogString);

                //logger.error("EC session ID or Session Time or Transaction ID field is required");
                return response;
            }*/ 
//            else if (!(Validation.isValidDate(simRegRequest.getRegDate())) || (simRegRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(simRegRequest.getEcSessTime()))) || (!simRegRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(simRegRequest.getExpDate()))) || !(Validation.isValidDob(simRegRequest.getDob()))) {
            else if (!(Validation.isValidDate(simRegRequest.getRegDate()))  || (!(simRegRequest.getDocTypeNo().equals(1) ||simRegRequest.getDocTypeNo().equals(4) || simRegRequest.getDocTypeNo().equals(5)) && !(Validation.isValidDate(simRegRequest.getExpDate()))) || !(Validation.isValidDob(simRegRequest.getDob()))) {
                response.setErrorCode(ApplicationError.EXP010207);
                response.setErrorDescription(ApplicationError.EXP010207_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid date format");
                logger.error(generateRequestLogString);

                //logger.error("Date format validation error");
                return response;
            } //mssisdn validation
            else if (!(Validation.isValidMSISDN(simRegRequest.getMsisdn()))) {
                response.setErrorCode(ApplicationError.EXP010208);
                response.setErrorDescription(ApplicationError.EXP010208_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid MSISDN format");
                logger.error(generateRequestLogString);

                //logger.error("MSISDN format not matched");
                return response;
            } //NID check
            else if ((simRegRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(simRegRequest.getDocID()))) || (simRegRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(simRegRequest.getDocID())))) {
                response.setErrorCode(ApplicationError.EXP010209);
                response.setErrorDescription(ApplicationError.EXP010209_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid NID format");
                logger.error(generateRequestLogString);

                //logger.error("NID format validation error");
                return response;
            } 
           
            else if (!TypeList.checkValidForeign(simRegRequest.getDocTypeNo(), simRegRequest.getForeignFlag())) {
                response.setErrorCode(ApplicationError.EXP010213);
                response.setErrorDescription(ApplicationError.EXP010213_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful",gson.toJson(logHash), "0", "Invalid foriegn flag");
                logger.error(generateRequestLogString);

                //logger.error("Invalid foriegn flag");
                return response;
            } else if (!(simRegRequest.getPurposeNo().equals(1) || simRegRequest.getPurposeNo().equals(2))) {
                response.setErrorCode(ApplicationError.EXP010211);
                response.setErrorDescription(ApplicationError.EXP010211_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid purpose no");
                logger.error(generateRequestLogString);

                //logger.error("Invalid purpose no");
                return response;
            } else if (!Validation.isEcVerified(simRegRequest.getIsVerify())) {
                response.setErrorCode(ApplicationError.EXP010219);
                response.setErrorDescription(ApplicationError.EXP010219_DESC);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Invalid 'is_verified' entry");
                logger.error(generateRequestLogString);

                //logger.error("Invalid 'is_verified' entry");
                return response;

            }
            //ec session optional
            if (simRegRequest.getDocTypeNo().equals(1) || simRegRequest.getDocTypeNo().equals(5)) {
                ecId = simRegRequest.getEcSessID();
                ecTime = Validation.formatDate(simRegRequest.getEcSessTime(), 0);
                ecTrn = simRegRequest.getEcTrnId();
                expTime = null;
                //System.out.println("1" + expTime);
            } else {
                ecId = null;
                ecTime = null;
                ecTrn = null;
                expTime = Validation.formatDate(simRegRequest.getExpDate(), 0);
                //System.out.println("2" + expTime);
            }

            String[] tokenInformation = authenticateToken(token);

            if (tokenInformation[0] == null) {
                response.setIsError(1);
                response.setErrorCode(tokenInformation[1]);
                response.setErrorDescription(tokenInformation[2]);
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "1", tokenInformation[2]);
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

            /*if (simRegRequest.getDocTypeNo().equals(1) && simRegRequest.getOtpVal() == null) {
                response.setErrorCode(ApplicationError.EXP010201);
                response.setErrorDescription(ApplicationError.validationError("OTP value"));
                logger.info("OTP value Not Found Error");
                return response;
            }*/
            SimRegistrationModelManager simRegModelManager = new SimRegistrationModelManager();
            SimRegistrationModel simRegModelList = simRegModelManager.simRegRereg(
                    schema,
                    simRegRequest.getPurposeNo(),
                    simRegRequest.getDocTypeNo(),
                    simRegRequest.getDocID(),
                    simRegRequest.getRetailID(),
                    simRegRequest.getMsisdn(),
                    Validation.formatDate(simRegRequest.getDob(), 1),
                    simRegRequest.getCmpoTrnID(),
                    Validation.formatDate(simRegRequest.getRegDate(), 0),
                    simRegRequest.getIsVerify(),
                    ecId,
                    0,
                    cmpoNo,
                    simRegRequest.getImsi(),
                    simRegRequest.getOtpNo(),
                    simRegRequest.getForeignFlag(),
                    expTime,
                    ecTime,
                    ecTrn
            );

            if (!(simRegModelList == null)) {
                if (simRegModelList.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim registration attempt", "successful", gson.toJson(logHash), "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", simRegModelList.getErrDesc());
                    logger.error(generateRequestLogString);
                }

                response.setSimNo(simRegModelList.getSimInfoNo());
                response.setIsError(simRegModelList.getIsError());
                response.setErrorCode(simRegModelList.getErrCode());
                response.setErrorDescription(simRegModelList.getErrDesc());

            } else {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, schema, "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "1", "Database Transaction error ");
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);

            }
//             long end = System.nanoTime();
//        System.out.println("elapsed in sim reg: "+ (end-start)/1000);
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", gson.toJson(logHash), "0", "Validation Didn't work; Exception:" + ex.getMessage());
            logger.warn(generateRequestLogString);

        }
//        long end = System.nanoTime();
//        System.out.println("elapsed in sim reg: "+ (end-start)/1000);
        return response;
    }

    @GET
    @Path("/registration")
    public Response registerSim(@Context HttpServletRequest request) {
//        logger.info(request.getRemoteAddr());
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Sim registration attempt", "unsuccessful", new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
        logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
