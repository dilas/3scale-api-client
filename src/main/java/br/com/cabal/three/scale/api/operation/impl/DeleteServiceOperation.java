package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class DeleteServiceOperation extends Operation {

    public DeleteServiceOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Deleting service " + inputMap.get(FormField.ID);
    }

    @Override
    protected String shutdownMessage() {
        return "Deleted";
    }

    @Override
    protected Object executeRequest() {
        return Unirest.delete(getEndpoint() + "/admin/api/services/{serviceId}.xml")
                .routeParam("serviceId", inputMap.get(FormField.ID).toString())
                .fields(inputMap)
                .asString();
    }
}
