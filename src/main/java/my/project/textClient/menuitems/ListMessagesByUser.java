package my.project.textClient.menuitems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
public class ListMessagesByUser implements Function<List, List> {

    private static final Logger LOG = LoggerFactory.getLogger(ListMessagesByUser.class);

    @Autowired
    private RestTemplate rt;

    @Autowired
    private HttpEntity httpe;

    @Override
    public List apply(List args) {
        List result = new ArrayList();
       try {
            result = rt.exchange("http://localhost:8080/tm/recieve", HttpMethod.GET, httpe, List.class).getBody();
        } catch(Exception e) {
            LOG.info("Проблема со связью с сервером");
        }
        System.out.println(String.format("Полученно с сервера %s сообщений", result.size()));
        return result;
    }
}
