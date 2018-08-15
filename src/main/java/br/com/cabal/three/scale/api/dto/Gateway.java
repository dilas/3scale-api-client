package br.com.cabal.three.scale.api.dto;

public class Gateway {
    private String staging;
    private String production;

    public String getStaging() {
        return staging;
    }

    public void setStaging(String staging) {
        this.staging = staging;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }
}
