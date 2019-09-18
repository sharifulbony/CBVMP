/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.MnpRegistrationRequest;
import cbvmp.service.api.response.MnpRegistrationResponse;
import cbvmp.service.data.manager.MnpRegistrationModelManager;
import cbvmp.service.data.model.MnpRegistrationModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.list.TypeList;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.Validation;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
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
import org.slf4j.impl.Log4jLoggerAdapter;

/**
 *
 * @author rahat
 */
@Path("/mnp")
public class MnpRegistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MnpRegistrationResponse registarSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, MnpRegistrationRequest mnpRegRequest) {

        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);

        MnpRegistrationResponse response = new MnpRegistrationResponse();
        
//        response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription("This Service is not accessible now !!!");
                 generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful",new Gson().toJson(mnpRegRequest).toString(), "1", " ");
                logger.error(generateRequestLogString);
                return response;
                
                
                /* uncomment from below code to get mnp working  */
        
//        ArrayList<String> validationErrors = null;
//        String ecId = null, ecTrn = null;
//        Date ecTime = null, expTime = null;
//        // Authentication code
//        try {
//            validationErrors = (ArrayList) ApplicationError.validateRequired(mnpRegRequest, MnpRegistrationRequest.class);
//            if (!validationErrors.isEmpty()) {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", validationErrors.get(1));
//                logger.error(generateRequestLogString);
//                response.setErrorCode(validationErrors.get(0));
//                response.setErrorDescription(validationErrors.get(1));
//                return response;
//            }
//            if (!(mnpRegRequest.getDocTypeNo().equals(1) || mnpRegRequest.getDocTypeNo().equals(5)) && (Validation.isNullFound(mnpRegRequest.getExpDate()))) {
//                response.setErrorCode(ApplicationError.EXP010216);
//                response.setErrorDescription(ApplicationError.EXP010216_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Expire Date field is required");
//                logger.error(generateRequestLogString);
//
//                //logger.error("Expire Date field is required");
//                return response;
//            } else if (!TypeList.typeFound(mnpRegRequest.getDocTypeNo())) {
//                response.setErrorCode(ApplicationError.EXP010217);
//                response.setErrorDescription(ApplicationError.EXP010217_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid document type no");
//                logger.error(generateRequestLogString);
//
//                //logger.error("Invalid document type no");
//                return response;
//            } // date format validation
//            else if (mnpRegRequest.getDocTypeNo().equals(1) && (Validation.isNullFound(mnpRegRequest.getEcSessID()) || Validation.isNullFound(mnpRegRequest.getEcSessTime()) || Validation.isNullFound(mnpRegRequest.getEcTrn()))) {
//                response.setErrorCode(ApplicationError.EXP010215);
//                response.setErrorDescription(ApplicationError.EXP010215_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "EC fields are required");
//                logger.error(generateRequestLogString);
//
//                //logger.error("EC session ID or Session Time or Transaction ID field is required");
//                return response;
//            } else if (!(Validation.isValidDate(mnpRegRequest.getRegDate())) || ((mnpRegRequest.getDocTypeNo().equals(1) || mnpRegRequest.getDocTypeNo().equals(5) ) && !(Validation.isValidDate(mnpRegRequest.getEcSessTime()))) || (!mnpRegRequest.getDocTypeNo().equals(1) && !(Validation.isValidDate(mnpRegRequest.getExpDate()))) || !(Validation.isValidDob(mnpRegRequest.getDob()))) {
//                response.setErrorCode(ApplicationError.EXP010207);
//                response.setErrorDescription(ApplicationError.EXP010207_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid date format");
//                logger.error(generateRequestLogString);
//
//                //logger.error("Date format validation error");
//                return response;
//            } //mssisdn validation
//            else if (!(Validation.isValidMSISDN(mnpRegRequest.getMsisdn()))) {
//                response.setErrorCode(ApplicationError.EXP010208);
//                response.setErrorDescription(ApplicationError.EXP010208_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid MSISDN format");
//                logger.error(generateRequestLogString);
//
//                //logger.error("MSISDN format not matched");
//                return response;
//            } //NID check
//            else if ((mnpRegRequest.getDocTypeNo().equals(1) && !(Validation.isValidNID(mnpRegRequest.getDocID()))) || (mnpRegRequest.getDocTypeNo().equals(5) && !(Validation.isValidSmartNID(mnpRegRequest.getDocID()))) ){
//                response.setErrorCode(ApplicationError.EXP010209);
//                response.setErrorDescription(ApplicationError.EXP010209_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid NID format");
//                logger.error(generateRequestLogString);
//
//                //logger.error("NID format validation error");
//                return response;
//            } else if (!TypeList.checkValidForeign(mnpRegRequest.getDocTypeNo(), mnpRegRequest.getForeignFlag())) {
//                response.setErrorCode(ApplicationError.EXP010213);
//                response.setErrorDescription(ApplicationError.EXP010213_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid foriegn flag");
//                logger.error(generateRequestLogString);
//
//                //logger.error("Invalid foriegn flag");
//                return response;
//            } else if (!Validation.isEcVerified(mnpRegRequest.getIsVerify())) {
//                response.setErrorCode(ApplicationError.EXP010219);
//                response.setErrorDescription(ApplicationError.EXP010219_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Invalid 'is_verified' entry");
//                logger.error(generateRequestLogString);
//
//                //logger.error("Invalid 'is_verified' entry");
//                return response;
//
//            }
//            //ec session optional
//            if (mnpRegRequest.getDocTypeNo().equals(1) || mnpRegRequest.getDocTypeNo().equals(5)) {
//                ecId = mnpRegRequest.getEcSessID();
//                ecTime = Validation.formatDate(mnpRegRequest.getEcSessTime(), 0);
//                ecTrn = mnpRegRequest.getEcTrn();
//                expTime = null;
//                //System.out.println("1" + expTime);
//            } else {
//                ecId = null;
//                ecTime = null;
//                ecTrn = null;
//                expTime = Validation.formatDate(mnpRegRequest.getExpDate(), 0);
//                //System.out.println("2" + expTime);
//            }
//
//            String[] tokenInformation = authenticateToken(token);
//
//            if (tokenInformation[0] == null) {
//                response.setIsError(1);
//                response.setErrorCode(tokenInformation[1]);
//                response.setErrorDescription(tokenInformation[2]);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "1", tokenInformation[2]);
//                logger.error(generateRequestLogString);
//                return response;
//            }
//
//            String[] tokenParts = tokenInformation[0].split(":");
//            String schema = tokenParts[0];
//            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
//
//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);
//
//            /*if (mnpRegRequest.getDocTypeNo().equals(1) && mnpRegRequest.getOtpVal() == null) {
//                response.setErrorCode(ApplicationError.EXP010201);
//                response.setErrorDescription(ApplicationError.validationError("OTP value"));
//                logger.info("OTP value Not Found Error");
//                return response;
//            }*/
//            MnpRegistrationModelManager mnpRegModelManager = new MnpRegistrationModelManager();
//            MnpRegistrationModel mnpRegModelList = mnpRegModelManager.mnpRegistration(
//                    schema,
//                    7,
//                    mnpRegRequest.getDocTypeNo(),
//                    mnpRegRequest.getDocID(),
//                    mnpRegRequest.getRetailID(),
//                    mnpRegRequest.getMsisdn(),
//                    Validation.formatDate(mnpRegRequest.getDob(), 1),
//                    mnpRegRequest.getCmpoTrnID(),
//                    Validation.formatDate(mnpRegRequest.getRegDate(), 0),
//                    mnpRegRequest.getIsVerify(),
//                    ecId,
//                    mnpRegRequest.getIsCorp(),
//                    cmpoNo,
//                    mnpRegRequest.getImsi(),
//                    mnpRegRequest.getForeignFlag(),
//                    expTime,
//                    ecTime,
//                    ecTrn
//            );
//
//            if (!(mnpRegModelList == null)) {
//
//                if (mnpRegModelList.getIsError() == 0) {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "successful", " ", "0", " ");
//                    logger.info(generateRequestLogString);
//                } else {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", mnpRegModelList.getErrDesc());
//                    logger.error(generateRequestLogString);
//                }
//                response.setMnpNo(mnpRegModelList.getMnpNo());
//                response.setIsError(mnpRegModelList.getIsError());
//                response.setErrorCode(mnpRegModelList.getErrCode());
//                response.setErrorDescription(mnpRegModelList.getErrDesc());
//
//            } else {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful", " ", "1", "Database Transaction error ");
//                logger.error(generateRequestLogString);
//                response.setIsError(1);
//                response.setErrorCode(ApplicationError.EXP010218);
//                response.setErrorDescription(ApplicationError.EXP010218_DESC);
//
//            }
//            return response;
//
//        } catch (IllegalArgumentException | IllegalAccessException ex) {
//            // set the universal error response
//            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful"," " , "0", "Validation Didn't work; Exception:" + ex.getMessage());
//            logger.warn(generateRequestLogString);
//
//        }
//        return response;
    }

    @GET
    @Path("/registration")
    public Response registerSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP registration attempt", "unsuccessful",new Gson().toJson(request).toString(), "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
