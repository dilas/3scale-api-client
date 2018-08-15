package br.com.cabal.three.scale.api;

import br.com.cabal.three.scale.api.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.builder.MultilineRecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class MainRunner {
    public static void main(String[] args) {
//        System.setProperty("org.apache.commons.logging.Log","org.apache.commons.logging.impl.SimpleLog");
//        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
//        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");
//        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "ERROR");

        if (args.length < 2) {
            System.out.println("Required arguments: <environment> <config file>");
            System.out.println("Exiting");
            System.exit(-1);
        }

        new MainRunner().run(args[0], args[1]);
    }

    private void run(String environment, String configFileStr) {
        File configFile = loadConfigFile(configFileStr);

        System.out.println("Environment: " + environment);
        System.out.println("Config file: " + configFileStr);

        ServiceMetadata serviceMetadata = loadServiceMetadata(configFile);

        publish(environment, serviceMetadata);
    }

    private File loadConfigFile(String configFileStr) {
        File configFile = new File(configFileStr);

        if (!configFile.exists()) {
            System.out.println("Config file not found: " + configFileStr);
            System.exit(-1);
        }

        return configFile;
    }


    private ServiceMetadata loadServiceMetadata(File configFile) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ServiceMetadata serviceMetadata = null;

        try {
            serviceMetadata = mapper.readValue(configFile, ServiceMetadata.class);

            System.out.println("");
            System.out.println(ReflectionToStringBuilder.toString(serviceMetadata, new MultilineRecursiveToStringStyle()));
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return serviceMetadata;
    }

    private void publish(String environment, ServiceMetadata meta) {
        System.out.println("Publishing service: " + meta.getService());

        Properties props = new Properties();

        try {
            props.load(getClass().getResourceAsStream("/env-" + environment + ".properties"));
        } catch (IOException e) {
            System.out.println("Failed to load environment properties for " + environment);
        }

        ApiManagerClient apimApiManagerClient = new ApiManagerClient(props.getProperty("apim.url"), props.getProperty("apim.publisher.token"));

        for (Organization org : meta.getOrganizations()) {
            Integer accountId = org.getId();

            Map<Integer, Object> serviceCreationResult = null;

            try {
                serviceCreationResult = apimApiManagerClient.createService(meta.getService());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Integer serviceId = (Integer) serviceCreationResult.get(1);
            Integer hitsMetricId = (Integer) serviceCreationResult.get(2);

            Integration integration = meta.getIntegration();
            Gateway gw = integration.getGateway();

            try {
                apimApiManagerClient.updateProxy(serviceId, integration.getBackend(), gw.getStaging(), gw.getProduction());
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Integer> applicationPlansId = new ArrayList<>();

            for (String plan : meta.getApplication().getPlans()) {
                try {
                    applicationPlansId.add(apimApiManagerClient.createApplicationPlan(serviceId, plan));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Application app = meta.getApplication();

            Integer applicationId = null;

            try {
                applicationId = apimApiManagerClient.createApplication(accountId, applicationPlansId.get(0), app.getName(), app.getDescription());
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Integer> methodsId = new ArrayList<>();
            List<Integer> mappingRulesId = new ArrayList<>();

            int mappings = 0;

            for (Rule rule : meta.getMapping().getRules()) {
                try {
                    Integer methodId = apimApiManagerClient.createMethod(serviceId, hitsMetricId, rule.getName());
                    methodsId.add(methodId);

                    Integer mappingRuleId = apimApiManagerClient.createMappingRule(serviceId, methodId, rule.getMethod(), rule.getPattern());
                    mappingRulesId.add(mappingRuleId);

                    mappings += 1;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            ///////////////
            // clean up
            ///////////////

            /*
            for (int i = 0; i < mappings; i++) {
                try {
                    apimApiManagerClient.deleteMappingRule(serviceId, mappingRulesId.get(i));
                    apimApiManagerClient.deleteMethod(serviceId, hitsMetricId, methodsId.get(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                apimApiManagerClient.deleteApplication(accountId, applicationId);
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (Integer applicationPlanId : applicationPlansId) {
                try {
                    apimApiManagerClient.deleteApplicationPlan(serviceId, applicationPlanId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                apimApiManagerClient.deleteService(serviceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            */
        }
    }
}
