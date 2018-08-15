package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class UpdateProxyOperation extends Operation {

    public UpdateProxyOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Update proxy settings for service id " + inputMap.get(FormField.SERVICE_ID);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.patch(getEndpoint() + "/admin/api/services/{serviceId}/proxy.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .fields(inputMap).asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Changed";
    }
}
