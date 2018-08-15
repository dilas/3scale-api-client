package br.com.cabal.three.scale.api.operation.impl;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.Operation;
import io.github.openunirest.http.Unirest;

import java.util.Map;

public class CreateMappingRuleOperation extends Operation {

    public CreateMappingRuleOperation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        super(configMap, inputMap);
    }

    @Override
    protected String startupMessage() {
        return String.format("Creating mapping rule: %s %s", inputMap.get(FormField.METHOD), inputMap.get(FormField.PATTERN));
    }

    @Override
    protected Object executeRequest() {
        return Unirest.post(getEndpoint() + "/admin/api/services/{serviceId}/proxy/mapping_rules.xml")
                .routeParam("serviceId", inputMap.get(FormField.SERVICE_ID).toString())
                .fields(inputMap)
                .asString();
    }

    @Override
    protected String shutdownMessage() {
        return "Mapping Rule Id " + result().asInteger();
    }

    @Override
    protected String[] resultPath() {
        return new String[]{"//mapping_rule/id/text()"};
    }
}
