package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private final RequestLine requestLine;
    private final List<String> headers;
    private String body;

    public Request(RequestLine requestLine, List<String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
    }

    public Request(RequestLine requestLine, List<String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
    }

    public String getQueryParam(String name) {
        String queryString = getQueryString();
        if (queryString != null && queryString.length() > 0) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2 && keyValue[0].equals(name)) {
                    return keyValue[1];
                }
            }
        }
        return null;
    }

    public Map<String, List<String>> getQueryParams() {
        Map<String, List<String>> queryParamsMap = new HashMap<>();
        String queryString = getQueryString();
        if (queryString != null && queryString.length() > 0) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    queryParamsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
                }
            }
        }
        return queryParamsMap;
    }

    private String getQueryString() {
        for (String header : headers) {
            if (header.startsWith("GET") || header.startsWith("POST")) {
                int questionMarkIndex = header.indexOf("?");
                if (questionMarkIndex != -1) {
                    return header.substring(questionMarkIndex + 1);
                }
            }
        }
        return null;
    }

    public RequestLine getRequestLine() {
        return requestLine;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestLine=" + requestLine +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}