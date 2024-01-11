package org.example;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private final RequestLine requestLine;
    private final List<String> headers;
    private String body;
    private  Map<String, List<String>> queryParams;

    public Request(RequestLine requestLine, List<String> headers) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.queryParams = parseQueryParams(requestLine.getPath());

    }
    private Map<String, List<String>> parseQueryParams(String path) {
        List<NameValuePair> params = URLEncodedUtils.parse(path, StandardCharsets.UTF_8);
        Map<String, List<String>> queryParamsMap = new HashMap<>();

        for (NameValuePair param : params) {
            String key = param.getName();
            String value = param.getValue();
            queryParamsMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
        }

        return queryParamsMap;
    }
    public Map<String, List<String>> getQueryParams() {
        return queryParams;
    }

    public Request(RequestLine requestLine, List<String> headers, String body) {
        this.requestLine = requestLine;
        this.headers = headers;
        this.body = body;
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