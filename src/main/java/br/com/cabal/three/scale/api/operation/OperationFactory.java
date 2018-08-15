package br.com.cabal.three.scale.api.operation;

import br.com.cabal.three.scale.api.operation.impl.*;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {
    private String endpoint;
    private String token;

    public static OperationFactory instance;

    private Map<String, Object> createConfigMap() {
        Map<String, Object> configMap = new HashMap<>();

        configMap.put(ConfigParameter.ENDPOINT, endpoint);
        configMap.put(ConfigParameter.TOKEN, token);

        return configMap;
    }

    private OperationFactory(String endpoint, String token) {
        this.endpoint = endpoint;
        this.token = token;
    }

    public static OperationFactory getInstance(String endpoint, String token) {
        if (instance == null) {
            instance = new OperationFactory(endpoint, token);
        }

        return instance;
    }

    public CreateApplicationOperation createApplication(Map<String, Object> inputMap) {
        return new CreateApplicationOperation(createConfigMap(), inputMap);
    }

    public CreateServiceOperation createService(Map<String, Object> inputMap) {
        return new CreateServiceOperation(createConfigMap(), inputMap);
    }

    public CreateApplicationPlanOperation createApplicationPlan(Map<String, Object> inputMap) {
        return new CreateApplicationPlanOperation(createConfigMap(), inputMap);
    }

    public DeleteServiceOperation deleteService(Map<String, Object> inputMap) {
        return new DeleteServiceOperation(createConfigMap(), inputMap);
    }

    public DeleteApplicationPlanOperation deleteApplicationPlan(Map<String, Object> inputMap) {
        return new DeleteApplicationPlanOperation(createConfigMap(), inputMap);
    }

    public DeleteApplicationOperation deleteApplication(Map<String, Object> inputMap) {
        return new DeleteApplicationOperation(createConfigMap(), inputMap);
    }

    public CreateMethodOperation createMethod(Map<String, Object> inputMap) {
        return new CreateMethodOperation(createConfigMap(), inputMap);
    }

    public DeleteMethodOperation deleteMethod(Map<String, Object> inputMap) {
        return new DeleteMethodOperation(createConfigMap(), inputMap);
    }

    public CreateMappingRuleOperation createMappingRule(Map<String, Object> inputMap) {
        return new CreateMappingRuleOperation(createConfigMap(), inputMap);
    }

    public DeleteMappingRuleOperation deleteMappingRule(Map<String, Object> inputMap) {
        return new DeleteMappingRuleOperation(createConfigMap(), inputMap);
    }

    public UpdateProxyOperation updateProxy(Map<String, Object> inputMap) {
        return new UpdateProxyOperation(createConfigMap(), inputMap);
    }
}
