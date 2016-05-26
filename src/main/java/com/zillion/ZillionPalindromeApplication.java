package com.zillion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ZillionPalindromeApplication {

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(ZillionPalindromeApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    /**
     * Sync RestTemplate
     * This template is used for all the configuration (http message converters, errors handler...).
     */

    @Bean
    public CloseableHttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(Integer.parseInt(environment.getRequiredProperty("httpclient.max.total.connections")));
        connectionManager.setDefaultMaxPerRoute(Integer.parseInt(environment.getRequiredProperty("httpclient.max.connections.per.route")));
        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(environment.getRequiredProperty("nasa.webservice.url"))), 20);
        RequestConfig config = RequestConfig.custom().setConnectTimeout(Integer.parseInt(environment.getRequiredProperty("httpclient.read.timeout.milliseconds"))).build();
        CloseableHttpClient defaultHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
        return defaultHttpClient;
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }


    /**
     *   Message converters are used to marshall and unmarshall Java Objects to and from JSON, XML, etc – over HTTP
     *   By default, the following HttpMessageConverters instances are pre-enabled:
     *
     *   ByteArrayHttpMessageConverter – converts byte arrays
     *   StringHttpMessageConverter – converts Strings
     *   ResourceHttpMessageConverter – converts org.springframework.core.io.Resource for any type of octet stream
     *   SourceHttpMessageConverter – converts javax.xml.transform.Source
     *   FormHttpMessageConverter – converts form data to/from a MultiValueMap<String, String>.
     *   Jaxb2RootElementHttpMessageConverter – converts Java objects to/from XML (added only if JAXB2 is present on the classpath)
     *   MappingJackson2HttpMessageConverter – converts JSON (added only if Jackson 2 is present on the classpath)**
     *
     *   MappingJacksonHttpMessageConverter – converts JSON (added only if Jackson is present on the classpath)
     *   AtomFeedHttpMessageConverter – converts Atom feeds (added only if Rome is present on the classpath)
     *   RssChannelHttpMessageConverter – converts RSS feeds (added only if Rome is present on the classpath)
     *
     */

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());

        /*
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof FormHttpMessageConverter) {
                System.out.println(">>>>>>>>> GOT FormHttpMessageConverter <<<<<<<<<<<<<<<");
            }
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setObjectMapper(objectMapper());
            }
        }
        */
        return restTemplate;
    }

}
