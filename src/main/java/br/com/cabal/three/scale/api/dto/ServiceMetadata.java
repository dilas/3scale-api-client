package br.com.cabal.three.scale.api.dto;

public class ServiceMetadata {
    private Organization[] organizations;
    private Application application;
    private String service;
    private Mapping mapping;
    private Integration integration;

    public Organization[] getOrganizations() {
        return organizations;
    }

    public void setOrganizations(Organization[] organizations) {
        this.organizations = organizations;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Mapping getMapping() {
        return mapping;
    }

    public void setMapping(Mapping mapping) {
        this.mapping = mapping;
    }

    public Integration getIntegration() {
        return integration;
    }

    public void setIntegration(Integration integration) {
        this.integration = integration;
    }
}
