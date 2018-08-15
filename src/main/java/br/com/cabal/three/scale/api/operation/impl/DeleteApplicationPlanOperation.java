package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class DeleteApplicationPlanOperation extends Operation {

    public DeleteApplicationPlanOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Deleting application plan " + inputMap.get(FormField.APPLICATION_PLAN_ID);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.delete(getEndpoint() + "/admin/api/services/{serviceId}/application_plans/{applicationPlanId}.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .routeParam("applicationPlanId", inputMap.get(FormField.APPLICATION_PLAN_ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Deleted";
    }
}
