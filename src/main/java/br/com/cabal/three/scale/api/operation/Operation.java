package br.com.cabal.three.scale.api.operation;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import io.github.openunirest.http.HttpResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public abstract class Operation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Operation.class);

    private Object result;

    protected Map<String, Object> configMap;
    protected Map<String, Object> inputMap;

    protected abstract String startupMessage();
    protected abstract Object executeRequest();
    protected abstract String shutdownMessage();

    // default implementation
    protected String[] resultPath() {
        return null;
    }

    private String normalizeResponse(String body) {
        return StringUtils.deleteWhitespace(body.replaceAll("\\R", ""));
    }

    public Operation(Map<String, Object> configMap, Map<String, Object> inputMap) {
        this.configMap = configMap;
        this.inputMap = inputMap;
    }

    public final Operation execute() {
        String startupMessage = startupMessage();
        LOGGER.info("{}", startupMessage);

        configureAuthToken(inputMap, configMap);

        HttpResponse<String> response = (HttpResponse<String>) executeRequest();

        LOGGER.info("{}", normalizeResponse(response.getBody()));

        this.result = extractResponse(response);

        String shutdownMessage = shutdownMessage();
        LOGGER.info("{}", shutdownMessage);

        return this;
    }

    public OperationResult result() {
        return new OperationResult(this.result, resultPath());
    }

    protected String getEndpoint() {
        return String.valueOf(configMap.get(ConfigParameter.ENDPOINT));
    }

    protected String extractResponse(HttpResponse<String> response) {
        String value = null;

        if (response.getStatus() >= 200 && response.getStatus() <= 299) {
            value = response.getBody();
        } else {
            String errorMessage = "";

            if (response.getStatus() == 422) {
                errorMessage = extractErrorMessage(response.getBody());
            } else {
                errorMessage = response.getStatus() + " " + response.getStatusText();
            }

            throw new RuntimeException("Error calling 3scale API - Message: " + errorMessage);
        }

        LOGGER.debug("Response: {}", value);

        return value;
    }

    protected String extractErrorMessage(String xmlStr) {
        String errorMessage = null;

        XML xml = new XMLDocument(xmlStr);

        errorMessage = xml.xpath("//errors/error/text()").get(0);

        return errorMessage;
    }

    private void configureAuthToken(Map<String, Object> inputMap, Map<String, Object> configMap) {
        inputMap.put("access_token", configMap.get(ConfigParameter.TOKEN));
    }
}
