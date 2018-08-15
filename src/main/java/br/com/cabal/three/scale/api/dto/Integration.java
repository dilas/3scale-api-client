package br.com.cabal.three.scale.api.dto;

public class Integration {
    private String backend;
    private Gateway gateway;

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = backend;
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setGateway(Gateway gateway) {
        this.gateway = gateway;
    }
}
