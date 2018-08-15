package br.com.cabal.three.scale.api;

import br.com.cabal.three.scale.api.operation.FormField;
import br.com.cabal.three.scale.api.operation.OperationFactory;

import java.util.HashMap;
import java.util.Map;

public class ApiManagerClient {
    private OperationFactory factory;

    public ApiManagerClient(String endpoint, String token) {
        this.factory = OperationFactory.getInstance(endpoint, token);
    }

    public Map<Integer, Object> createService(String serviceName) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.NAME, serviceName);
        inputMap.put(FormField.DEPLOYMENT_OPTION, "hosted");
        inputMap.put(FormField.BACKEND_VERSION, "1");

        return factory.createService(inputMap).execute().result().asMap();
    }

    public void deleteService(Integer serviceId) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.ID, serviceId);

        factory.deleteService(inputMap).execute();
    }

    public Integer createApplicationPlan(Integer serviceId, String applicationPlanName) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.NAME, applicationPlanName);

        return factory.createApplicationPlan(inputMap).execute().result().asInteger();
    }

    public void deleteApplicationPlan(Integer serviceId, Integer applicationPlanId) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.APPLICATION_PLAN_ID, applicationPlanId);

        factory.deleteApplicationPlan(inputMap).execute();
    }

    public Integer createApplication(Integer accountId, Integer planId, String name, String description) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.ACCOUNT_ID, accountId);
        inputMap.put(FormField.PLAN_ID, planId);
        inputMap.put(FormField.NAME, name);
        inputMap.put(FormField.DESCRIPTION, description);

        return factory.createApplication(inputMap).execute().result().asInteger();
    }

    public void deleteApplication(Integer accountId, Integer applicationId) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.ACCOUNT_ID, accountId);
        inputMap.put(FormField.ID, applicationId);

        factory.deleteApplication(inputMap).execute();
    }

    public Integer createMethod(Integer serviceId, Integer metricId, String name) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.METRIC_ID, metricId);
        inputMap.put(FormField.FRIENDLY_NAME, name);

        return factory.createMethod(inputMap).execute().result().asInteger();
    }

    public void deleteMethod(Integer serviceId, Integer metricId, Integer methodId) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.METRIC_ID, metricId);
        inputMap.put(FormField.ID, methodId);

        factory.deleteMethod(inputMap).execute();
    }

    public Integer createMappingRule(Integer serviceId, Integer metricId, String method, String pattern) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.METRIC_ID, metricId);
        inputMap.put(FormField.METHOD, method);
        inputMap.put(FormField.PATTERN, pattern);
        inputMap.put(FormField.DELTA, "1");

        return factory.createMappingRule(inputMap).execute().result().asInteger();
    }

    public void deleteMappingRule(Integer serviceId, Integer mappingRuleId) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.ID, mappingRuleId);

        factory.deleteMappingRule(inputMap).execute();
    }

    public void updateProxy(Integer serviceId, String backend, String gatewayStaging, String gatewayProduction) {
        Map<String, Object> inputMap = new HashMap<>();
        inputMap.put(FormField.SERVICE_ID, serviceId);
        inputMap.put(FormField.BACKEND_ENDPOINT, backend);
        inputMap.put(FormField.GW_STAGING_ENDPOINT, gatewayStaging);
        inputMap.put(FormField.GW_PRODUCTION_ENDPOINT, gatewayProduction);

        factory.updateProxy(inputMap).execute();
    }
}
