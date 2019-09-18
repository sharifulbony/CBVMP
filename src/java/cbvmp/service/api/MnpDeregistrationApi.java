/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.MnpDeregistrationRequest;
import cbvmp.service.api.response.MnpDeregistrationResponse;
import cbvmp.service.data.manager.MnpDeregistrationModelManager;
import cbvmp.service.data.model.MnpDeregistrationModel;
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
public class MnpDeregistrationApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger("applicationLogger");

    @POST
    @Path("/deregistration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MnpDeregistrationResponse deRegisterSim(@Context HttpServletRequest request, @HeaderParam("Authorization") String token, MnpDeregistrationRequest deregRequest) {
        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);
        ArrayList<String> validationErrors = null;
        MnpDeregistrationResponse deRegResponse = new MnpDeregistrationResponse();

           deRegResponse.setErrorDescription("This Service is not accessible now !!!");
           
           generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful",new Gson().toJson(deregRequest).toString(), "1", " ");
                logger.error(generateRequestLogString);
                return deRegResponse;
//        try {
//            // validate the request object
//            validationErrors = (ArrayList) ApplicationError.validateRequired(deregRequest, MnpDeregistrationRequest.class);
//            if (!validationErrors.isEmpty()) {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "0", validationErrors.get(1));
//                logger.error(generateRequestLogString);
//                deRegResponse.setErrorCode(validationErrors.get(0));
//                deRegResponse.setErrorDescription(validationErrors.get(1));
//                return deRegResponse;
//            } // date format validation
//            else if (!(Validation.isValidMSISDN(deregRequest.getMsisdn()))) {
//                deRegResponse.setErrorCode(ApplicationError.EXP010208);
//                deRegResponse.setErrorDescription(ApplicationError.EXP010208_DESC);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "0", "Invalid MSISDN format");
//                logger.error(generateRequestLogString);
//                //logger.error("MSISDN format not matched");
//                return deRegResponse;
//            }
////            else if (!(deregRequest.get.equals(4))) {
////                deRegResponse.setErrorCode(ApplicationError.EXP010211);
////                deRegResponse.setErrorDescription(ApplicationError.EXP010211_DESC);
////                logger.error("Invalid purpose no");
////                return response;
////            }
//            //NID check
//
//            // Authentication code
//            String[] tokenInformation = authenticateToken(token);
//
//            if (tokenInformation[0] == null) {
//                deRegResponse.setIsError(1);
//                deRegResponse.setErrorCode(tokenInformation[1]);
//                deRegResponse.setErrorDescription(tokenInformation[2]);
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "1", tokenInformation[2]);
//                logger.error(generateRequestLogString);
//                return deRegResponse;
//            }
//            String[] tokenParts = tokenInformation[0].split(":");
//            String schema = tokenParts[0];
//            Integer cmpoNo = Integer.parseInt(tokenParts[1]);
//
//            logger.debug("schema:" + schema + ",cmpoNo:" + cmpoNo);
//
//            MnpDeregistrationModelManager mnpDeregManager = new MnpDeregistrationModelManager();
//            MnpDeregistrationModel mnpDeregList = mnpDeregManager.mnpDeRegistration(
//                    schema,
//                    4,
//                    deregRequest.getMsisdn()
//            );
//
//            if (mnpDeregList != null) {
//                if (mnpDeregList.getIsError() == 0) {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "successful", " ", "0", " ");
//                    logger.info(generateRequestLogString);
//                } else {
//                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "0", mnpDeregList.getErrDesc());
//                    logger.error(generateRequestLogString);
//                }
//
//                deRegResponse.setIsError(mnpDeregList.getIsError());
//                deRegResponse.setMnpNo(mnpDeregList.getMnpNo());
//                deRegResponse.setErrorCode(mnpDeregList.getErrCode());
//                deRegResponse.setErrorDescription(mnpDeregList.getErrDesc());
//
//            } else {
//                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "1", "Database Transaction error ");
//                logger.error(generateRequestLogString);
//                deRegResponse.setIsError(1);
//                deRegResponse.setErrorCode(ApplicationError.EXP010218);
//                deRegResponse.setErrorDescription(ApplicationError.EXP010218_DESC);
//
//            }
//
//            return deRegResponse;
//
//        } catch (IllegalArgumentException | IllegalAccessException ex) {
//            // set the universal error deRegResponse
//            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful", " ", "0", "Validation Didn't work; Exception:" + ex.getMessage());
//            logger.warn(generateRequestLogString);
//
//        }
//        return deRegResponse;
    }

    @GET
    @Path("/deregistration")
    public Response deRegisterSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "MNP deregistration attempt", "unsuccessful",new Gson().toJson(request).toString() , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
}
