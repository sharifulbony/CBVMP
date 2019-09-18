/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.api.request;

import cbvmp.service.api.response.GeneralResponse;
import javax.ws.rs.ext.ContextResolver;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 *
 * @author tanbir
 * 
 * Class not necessary currently
 */
//public class JacksonConfig implements ContextResolver<ObjectMapper>{
//    private ObjectMapper objectMapper;
//
//    public JacksonConfig() {
//        objectMapper = new ObjectMapper();
//        SimpleModule module = new SimpleModule("MyModule", new Version(1, 0, 0, null));
//        module.addSerializer(GeneralResponse.class, new ResponseSerializer());
//        objectMapper.registerModule(module);
//    }
//
//    public ObjectMapper getContext(Class<?> objectType) {
//        return objectMapper;
//    }
//}

public class JacksonConfig{


}
