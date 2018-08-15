package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class DeleteApplicationOperation extends Operation {

    public DeleteApplicationOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Deleting application " + inputMap.get(FormField.ID);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.delete(getEndpoint() + "/admin/api/accounts/{accountId}/applications/{applicationId}.xml")
                .routeParam("accountId", inputMap.get(FormField.ACCOUNT_ID).toString())
                .routeParam("applicationId", inputMap.get(FormField.ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Deleted";
    }
}
