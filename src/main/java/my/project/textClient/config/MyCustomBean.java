package my.project.textClient.config;

import my.project.textClient.Menu;
import my.project.textClient.menuitems.ListContacts;
import my.project.textClient.menuitems.ListMessagesByUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class MyCustomBean {

    @Bean
    public RestTemplate initRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public Menu initMenu(ListContacts lContacts, ListMessagesByUser lmu) {
        Map<String, Function<List, List>> mnu = new HashMap<>();
        Map<String, String> hlp = new HashMap<>();
        hlp.put("help".toLowerCase(), "список всех комманд");
        hlp.put("quit".toLowerCase(), "выход из программы");
        hlp.put("listUsers".toLowerCase(), "список всех пользователей");
        hlp.put("listMessages".toLowerCase(), "список всех сообщений, полученных от сервера");
        mnu.put("listContacts".toLowerCase(), lContacts);
        hlp.put("listContacts".toLowerCase(), "получить текущий список всех пользователей");
        mnu.put("recieveMessages".toLowerCase(), lContacts);
        hlp.put("recieveMessages".toLowerCase(), "получить все сообщения для пользователя которые лежат на сервере");
        return new Menu(mnu, hlp);
    }
}
