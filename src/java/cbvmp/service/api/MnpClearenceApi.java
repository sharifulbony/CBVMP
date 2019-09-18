/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.MnpClearenceRequest;
import cbvmp.service.api.response.MnpClearenceResponse;
import cbvmp.service.data.manager.MnpClearenceModelManager;
import cbvmp.service.data.model.MnpClearenceModel;

import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.log.SingletoneLogger;
import cbvmp.service.util.security.Validation;
import com.google.gson.Gson;
import java.util.ArrayList;
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
public class MnpClearenceApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/clearence")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MnpClearenceResponse clearMNP(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, MnpClearenceRequest mnpRequest) {
        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);
        ArrayList<String> validationErrors = null;
        MnpClearenceResponse mnpResponse = new MnpClearenceResponse();

                    mnpResponse.setErrorDescription("This Service is not accessible now !!!");
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful",new Gson().toJson(mnpRequest).toString(), "1", " ");
                logger.error(generateRequestLogString);
                return mnpResponse;
                
                
                /* uncomment from below code to get mnp working  */
//        try {
//            // validate the request object
//            validationErrors = (ArrayList) ApplicationError.validateRequired(mnpRequest, MnpClearenceRequest.class);
//            if (!validationErrors.isEmpty()) {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful"," " , "0", validationErrors.get(1));
//                logger.error(generateRequestLogString);
//                mnpResponse.setErrorCode(validationErrors.get(0));
//                mnpResponse.setErrorDescription(validationErrors.get(1));
//                return mnpResponse;
//            } // date format validation
//            else if (!(Validation.isValidMSISDN(mnpRequest.getMsisdn()))) {
//                mnpResponse.setErrorCode(ApplicationError.EXP010208);
//                mnpResponse.setErrorDescription(ApplicationError.EXP010208_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful"," " , "0", "Invalid MSISDN format");
//                logger.error(generateRequestLogString);
//
//                //logger.error("MSISDN format not matched");
//                return mnpResponse;
//            }
////            else if (!(mnpRequest.get.equals(4))) {
////                mnpResponse.setErrorCode(ApplicationError.EXP010211);
////                mnpResponse.setErrorDescription(ApplicationError.EXP010211_DESC);
////                logger.error("Invalid purpose no");
////                return response;
////            }
//            //NID check
//
//            // Authentication code
//            String[] tokenInformation = authenticateToken(token);
//
//            if (tokenInformation[0] == null) {
//                mnpResponse.setIsError(1);
//                mnpResponse.setErrorCode(tokenInformation[1]);
//                mnpResponse.setErrorDescription(tokenInformation[2]);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful"," ", "1", tokenInformation[2]);
//                logger.error(generateRequestLogString);
//                return mnpResponse;
//            }
//            String[] tokenParts = tokenInformation[0].split(":");
//            //String schema = tokenParts[0];
//            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
//
//            //logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);
//            MnpClearenceModelManager mnpManager = new MnpClearenceModelManager();
//            MnpClearenceModel mnpList = mnpManager.mnpClear(
//                    "CBVMP_MNP",
//                    mnpRequest.getMsisdn()
//            );
//
//            if (mnpList != null) {
//                if (mnpList.getIsError() == 0) {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "successful", " ", "0", " ");
//                    logger.info(generateRequestLogString);
//                } else {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful"," ", "0",  mnpList.getErrDesc());
//                    logger.error(generateRequestLogString);
//                }
//
//                mnpResponse.setIsError(mnpList.getIsError());
//                mnpResponse.setCmpoName(mnpList.getCmpoName());
//                mnpResponse.setErrorCode(mnpList.getErrCode());
//                mnpResponse.setErrorDescription(mnpList.getErrDesc());
//
//            } else {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful", " ", "1", "Database Transaction error ");
//                logger.error(generateRequestLogString);
//                mnpResponse.setIsError(1);
//                mnpResponse.setErrorCode(ApplicationError.EXP010218);
//                mnpResponse.setErrorDescription(ApplicationError.EXP010218_DESC);
//
//            }
//
//            return mnpResponse;
//
//        } catch (IllegalArgumentException | IllegalAccessException ex) {
//            // set the universal error mnpResponse
//            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful"," " , "0", "Validation Didn't work; Exception:" + ex.getMessage());
//            logger.warn(generateRequestLogString);
//
//        }
//        return mnpResponse;
    }

    @GET
    @Path("/clearence")
    public Response deRegisterSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP clearence attempt", "unsuccessful",new Gson().toJson(request).toString() , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
}
