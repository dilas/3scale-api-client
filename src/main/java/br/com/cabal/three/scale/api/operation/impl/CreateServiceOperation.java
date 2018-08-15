package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class CreateServiceOperation extends Operation {

    public CreateServiceOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return "Creating service " + inputMap.get(FormField.NAME);
    }

    @Override
    protected String shutdownMessage() {
        return "Service Id: " + result().asInteger();
    }

    @Override
    protected String[] resultPath() {
        return new String[]{"//service/id/text()", "//service/metrics/metric/id/text()"};
    }

    @Override
    protected Object executeRequest() {
        return Unirest.post(getEndpoint() + "/admin/api/services.xml").fields(inputMap).asString();
    }
}
