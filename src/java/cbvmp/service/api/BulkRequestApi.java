/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api;

import cbvmp.service.api.request.BulkRegistrationRequest;
import cbvmp.service.api.response.BulkRegistrationResponse;
import cbvmp.service.util.error.ApplicationError;
import cbvmp.service.util.log.SingletoneLogger;
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
 * @author tanbir
 */

@Path("/bulk")
public class BulkRequestApi extends BaseServiceApi{
    
    Log4jLoggerAdapter logger = SingletoneLogger.getLogger(BulkRequestApi.class);
    // request ip address, requested url,request params 
    @POST
    @Path("/registration")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public BulkRegistrationResponse corporateRegistration(@Context HttpServletRequest request,@HeaderParam("Authorization") String token, BulkRegistrationRequest blkRequest){
        SingletoneLogger.generateRequestLogString(request);
        BulkRegistrationResponse blkResponse = new BulkRegistrationResponse();
        
        if(blkRequest.getSimList().length >=4){
            blkResponse.setErrorCode("EXP010203");
            blkResponse.setErrorDescription("Bulk request too big");
            return blkResponse;
        }
        return blkResponse;
        
    }
    @GET
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response corporateRegistration(@Context HttpServletRequest request){
        String generateRequestLogString = SingletoneLogger.generateRequestLogString(request, " ", "Bulk request attempt", "unsuccessful"," " , "1", ApplicationError.RQEXP404_DESC);
            logger.error(generateRequestLogString);
        return Response.status(ApplicationError.RQEXP404).entity(ApplicationError.RQEXP404_DESC).build();
    }
    
    
    
}
