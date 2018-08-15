package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class CreateApplicationPlanOperation extends Operation {

    public CreateApplicationPlanOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Creating application plan: " + inputMap.get(FormField.NAME);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.post(getEndpoint() + "/admin/api/services/{serviceId}/application_plans.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Application Plan Id " + result().asInteger();
    }


    @Override
    protected String[] resultPath() {
        return new String[]{"//plan/id/text()"};
    }
}
