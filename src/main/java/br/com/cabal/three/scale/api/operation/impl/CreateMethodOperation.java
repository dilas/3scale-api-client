package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class CreateMethodOperation extends Operation {

    public CreateMethodOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Creating method " + inputMap.get(FormField.FRIENDLY_NAME);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.post(getEndpoint() + "/admin/api/services/{serviceId}/metrics/{metricId}/methods.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .routeParam("metricId", inputMap.get(FormField.METRIC_ID).toString())
                .fields(inputMap).asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Method Id: " + result().asInteger();
    }

    @Override
    protected String[] resultPath() {
        return new String[]{"//method/id/text()"};
    }
}
