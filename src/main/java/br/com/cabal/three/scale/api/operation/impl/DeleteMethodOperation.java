package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class DeleteMethodOperation extends Operation {

    public DeleteMethodOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Deleting method " + inputMap.get(FormField.ID);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.delete(getEndpoint() + "/admin/api/services/{serviceId}/metrics/{metricId}/methods/{id}.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .routeParam("metricId", inputMap.get(FormField.METRIC_ID).toString())
                .routeParam("id", inputMap.get(FormField.ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Deleted";
    }
}
