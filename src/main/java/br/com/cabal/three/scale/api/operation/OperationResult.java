package br.com.cabal.three.scale.api.operation;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;

import java.util.HashMap;
import java.util.Map;

public class OperationResult {
    private Object result;
    private String[] xpath;

    public OperationResult(Object result, String[] xpath) {
        this.result = result;
        this.xpath = xpath;
    }

    public Map<Integer, Object> asMap() {
        Map<Integer, Object> resultMap = new HashMap<>();

        int key = 1;

        for (String path: xpath) {
            Integer value = getIntegerValueFromXML(result.toString(), path);

            resultMap.put(new Integer(key), value);

            key++;
        }

        return resultMap;
    }

    public Integer asInteger() {
        Integer value = null;

        if (xpath.length > 0) {
            value = getIntegerValueFromXML(result.toString(), xpath[0]);
        }

        return value;
    }

    private Integer getIntegerValueFromXML(String xmlStr, String xpath) {
        XML xml = new XMLDocument(xmlStr);

        Integer value = null;
        String stringValue = xml.xpath(xpath).get(0);

        if (stringValue != null) {
            value = new Integer(stringValue);
        }

        return value;
    }
}
