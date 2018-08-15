package br.com.cabal.three.scale.api.dto;

public class Application {
    private String name;
    private String description;
    private String[] plans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getPlans() {
        return plans;
    }

    public void setPlans(String[] plans) {
        this.plans = plans;
    }
}
