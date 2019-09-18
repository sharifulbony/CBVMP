/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.APIUserInfoRequest;
import cbvmp.service.api.request.OTPRequest;
import cbvmp.service.api.response.APIUserInfoResponse;
import cbvmp.service.api.response.OTPResponse;
import cbvmp.service.data.manager.APIUserInfoModelManager;
import cbvmp.service.data.manager.CMPOModelManager;
import cbvmp.service.data.manager.OTPModelManager;
import cbvmp.service.data.model.APIUserInfoModel;
import cbvmp.service.data.model.CMPOModel;
import cbvmp.service.data.model.OTPModel;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.log.SingletoneLogger;
import java.util.ArrayList;
import java.util.List;
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
@Path("/change")
public class ChangePasswordApi extends BaseServiceApi {

    Log4jLoggerAdapter logger = SingletoneLogger.getLogger(ChangePasswordApi.class);

    @POST
    @Path("/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public APIUserInfoResponse changePassword(@Context HttpServletRequest request, APIUserInfoRequest apiuserinfoRequest) {
        String generateRequestLogString = null;
//        SingletoneLogger.generateRequestLogString(request);
//        logger.info(generateRequestLogString);
        ArrayList<String> validationErrors = null;

        APIUserInfoResponse response = new APIUserInfoResponse();

        try {
            // validate the request object
            validationErrors = (ArrayList) ApplicationError.validateRequired(apiuserinfoRequest, APIUserInfoRequest.class);
            if (!validationErrors.isEmpty()) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "0", validationErrors.get(1));
                logger.error(generateRequestLogString);
                //logger.error(validationErrors.get(1));
                response.setErrorCode(validationErrors.get(0));
                response.setErrorDescription(validationErrors.get(1));
                return response;
            } else if (apiuserinfoRequest.getOldPass().equals(apiuserinfoRequest.getUserPass()) || apiuserinfoRequest.getUserPass().equals(apiuserinfoRequest.getUserID())) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "0", "Trying to set invalid password");
                logger.error(generateRequestLogString);
                response.setErrorCode(ApplicationError.EXP010220);
                response.setErrorDescription(ApplicationError.EXP010220_DESC);
                return response;
            } else if (apiuserinfoRequest.getUserPass().length() < 8) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "0", "Trying to set invalid password");
                logger.error(generateRequestLogString);
                response.setErrorCode(ApplicationError.EXP010221);
                response.setErrorDescription(ApplicationError.EXP010221_DESC);
                return response;
            }

            APIUserInfoModelManager apiuserinfoModelManager = new APIUserInfoModelManager();
            CMPOModelManager cmpoModelManager = new CMPOModelManager();
            APIUserInfoModel apiUserModelList = null;
            CMPOModel cmpoModel = cmpoModelManager.verifyChangePasswordRequest(apiuserinfoRequest.getUserID());

            if (cmpoModel == null) {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "1", "User not found ");
                logger.error(generateRequestLogString);
                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010206);
                response.setErrorDescription(ApplicationError.EXP010206_DESC);
                return response;
            } else {
                cmpoModel = cmpoModelManager.verifyCMPOUserNamePassword("CBVMP_MASTER", apiuserinfoRequest.getUserID(), apiuserinfoRequest.getOldPass());

                if (cmpoModel == null) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "1", "Credential mismatch ");
                    logger.error(generateRequestLogString);
                    response.setIsError(1);
                    response.setErrorCode(ApplicationError.EXP010206);
                    response.setErrorDescription(ApplicationError.EXP010206_DESC);
                    return response;
                } else if (cmpoModel != null && cmpoModel.isPasswdChangeRequired()) {
                    apiUserModelList = apiuserinfoModelManager.changePassword(
                            cmpoModel.getCmpoNo(),
                            apiuserinfoRequest.getUserID(),
                            apiuserinfoRequest.getOldPass(),
                            apiuserinfoRequest.getUserPass()
                    );

                    //System.out.println("entered");
                } else if (cmpoModel != null && !(cmpoModel.isPasswdChangeRequired())) {

                    //logger.debug("Old password:" + cmpoModel.getPassword());
                    apiUserModelList = apiuserinfoModelManager.changePassword(
                            cmpoModel.getCmpoNo(),
                            apiuserinfoRequest.getUserID(),
                            apiuserinfoRequest.getOldPass(),
                            apiuserinfoRequest.getUserPass()
                    );
                }
            }
            if (!(apiUserModelList == null)) {
                response.setIsError(apiUserModelList.getIsError());
                response.setErrorCode(apiUserModelList.getErrCode());
                response.setErrorDescription(apiUserModelList.getErrDesc());
                if (apiUserModelList.getIsError() == 0) {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "successful", " ", "0", " ");
                    logger.info(generateRequestLogString);
                } else {
                    generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", " ", "0", apiUserModelList.getErrDesc());
                    logger.error(generateRequestLogString);
                }

            } else {
                generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change password attempt", "unsuccessful", " ", "1", "Database Transaction error");
                logger.error(generateRequestLogString);

                response.setIsError(1);
                response.setErrorCode(ApplicationError.EXP010218);
                response.setErrorDescription(ApplicationError.EXP010218_DESC);

            }
            return response;

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            // set the universal error response
            generateRequestLogString = SingletoneLogger.generateRequestLogString(request, apiuserinfoRequest.getUserID(), "Change Password attempt", "unsuccessful", "Validation Didn't work; Exception:" + ex.getMessage(), "0", " ");
            logger.warn(generateRequestLogString);

        }
        return response;
    }

    @GET
    @Path("/password")
    public Response registerSim(@Context HttpServletRequest request) {
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Change Password attempt", "unsuccessful"," " , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }

}
