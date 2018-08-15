package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class DeleteMappingRuleOperation extends Operation {

    public DeleteMappingRuleOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Deleting mapping rule " + inputMap.get(FormField.ID);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.delete(getEndpoint() + "/admin/api/services/{serviceId}/proxy/mapping_rules/{id}.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .routeParam("id", inputMap.get(FormField.ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Deleted";
    }
}
