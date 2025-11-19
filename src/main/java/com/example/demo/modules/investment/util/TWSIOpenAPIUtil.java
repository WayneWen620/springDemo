package com.example.demo.modules.investment.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


public class TWSIOpenAPIUtil {
    static Logger logger= LogManager.getLogger();
    public static <T> T send(String url, HttpMethod method, Class<T> t){

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
            ResponseEntity<T> responseEntity  =
                    restTemplate.exchange(url,method,httpEntity,t);
            T result = responseEntity.getBody();
            logger.info(responseEntity.getStatusCode());
            return result;
        }
        catch(HttpClientErrorException httpClientErrorException) {
            logger.error(httpClientErrorException.getResponseBodyAsString());
            return null;
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
