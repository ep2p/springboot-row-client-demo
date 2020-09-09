package lab.idioglossia.client.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.idioglossia.row.client.RestTemplateRowHttpClient;
import lab.idioglossia.row.client.RowHttpClient;
import lab.idioglossia.row.client.RowHttpClientHolder;
import lab.idioglossia.row.client.ws.HandshakeHeadersProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class Config {

    //adds handshake headers as server demo needs them
    @Bean("handshakeHeadersProvider")
    public HandshakeHeadersProvider handshakeHeadersProvider(){
        return new HandshakeHeadersProvider() {
            @Override
            public Map<String, List<String>> getHeaders() {
                Map<String, List<String>> headers = new HashMap<>();
                headers.put("X-Auth-Token", Collections.singletonList("adminToken"));
                return headers;
            }
        };
    }


    //send http request when ws fails
    @Bean("rowHttpClientHolder")
    public RowHttpClientHolder rowHttpClientHolder(){
        return new RowHttpClientHolder() {
            @Override
            public RowHttpClient getRowHttpClient() {
                return new RestTemplateRowHttpClient("http://localhost:8080", new RestTemplate(), new ObjectMapper());
            }
        };
    }

}
