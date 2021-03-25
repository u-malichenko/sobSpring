package ru.malichenko.sobSpring;

import org.springframework.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;


public class Request {
    private final String host;
    private final HttpMethod method;

    private Request(Builder builder){
        this.host = builder.host;
        this.method = builder.method;
        if (!builder.requestParams.isEmpty()){
            StringBuilder requestParams = new StringBuilder();
        }
        validate();
    }

    public void validate(){
        if (host == null){
            throw new IllegalStateException("Host cannot be null");
        }
        if (method == null){
            throw new IllegalStateException("Http method cannot be null");
        }
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private HttpMethod method;
        private String host;
        private Map<String,String> requestParams;

        public Builder(){
            this.requestParams = new HashMap<>();
        }

        public Builder addRequestParam(String key, String value){
            this.requestParams.put(key,value);
            return this;
        }

        public Builder setMethod(HttpMethod method){
            this.method = method;
            return this;
        }

        public Builder setHost(String host){
            this.host = host;
            return this;
        }

        public Request build() {
            return new Request(this);
        }
    }
}
