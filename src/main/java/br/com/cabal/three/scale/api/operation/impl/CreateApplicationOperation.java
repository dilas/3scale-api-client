package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class CreateApplicationOperation extends Operation {

    public CreateApplicationOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Creating application " + inputMap.get(FormField.NAME);
    }

    @Override
    protected Object executeRequest() {
        return Unirest.post(getEndpoint() + "/admin/api/accounts/{accountId}/applications.xml")
                .routeParam("accountId", inputMap.get(FormField.ACCOUNT_ID).toString())
                .fields(inputMap).asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Application Id: " + result().asInteger();
    }


    @Override
    protected String[] resultPath() {
        return new String[]{"//application/id/text()"};
    }
}
