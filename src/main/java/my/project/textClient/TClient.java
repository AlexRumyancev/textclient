package my.project.textClient;

import my.project.textClient.menuitems.ListContacts;
import my.project.textClient.menuitems.ListMessagesByUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class TClient {

    private static final Logger LOG = LoggerFactory.getLogger(TClient.class);

    private List<Map<String, Object>> users = new ArrayList<>();
    private Map<Integer, Map<String, Object>> messages = new HashMap<>();

    @Autowired
    private RestTemplate rt;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private Menu menu;

    @Autowired
    private ListContacts lc;

    @Autowired
    private ListMessagesByUser lmu;

    @Autowired
    private HttpEntity httpe;

    @PostConstruct
    public void initSheduler() {
        taskScheduler.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
              LOG.info("Обновляем онлайн статус клиента");
                try {
                    ResponseEntity result = rt.exchange("http://localhost:8080/tm/login", HttpMethod.GET, httpe, String.class);
                } catch(Exception e) {
                    LOG.info("Проблема со связью с сервером");
//                    throw new Exception();
                }
            }
        }, new Date(), Constants.ONLINE_REFRESH);

        this.run();

    }

    private void run() {
        Random rnd = new Random();
        users = lc.apply(null);
        lmu.apply(null).forEach((x) -> messages.put(rnd.nextInt(9999), (Map<String, Object>) x));
        System.out.println("Messenger.. Text client..");
        Scanner scn = new Scanner(System.in);
        Boolean exit = false;
        while (!exit) {
            System.out.print(">");
            String inp = scn.nextLine();
            switch (inp.toLowerCase()) {
                case "quit":
                            exit = true;
                            break;
                case "help":
                case "?":
                            menu.getHelp().forEach((x, y) -> System.out.println(String.format("%s - %s", x, y)));
                            break;
                case "listusers":
                            users.forEach((x) -> System.out.println(String.format("%s : статус : %s", x.get("login"), x.get("online"))));
                            break;
                case "listmessages":
                            for(Map.Entry<Integer, Map<String, Object>> item: messages.entrySet()) {
                                System.out.println(String.format("%s - %s - %s - %s", item.getKey(),
                                        item.getValue().get("created"), item.getValue().get("from"), item.getValue().get("message")));
                            }
                            break;
                default:
                            List<String> args = StringToList(inp);
                            if (!"".equals(args.get(0))) {
                                try {
                                    menu.getMenu(args.get(0).toLowerCase()).apply(args);
                                } catch (Exception e) {
                                    System.out.println("Нет такой комманды");
                                }
                            }
                            break;
            }
        }
        System.out.println("Exit from messenger..");
//        SpringApplication.exit(appContext, () -> 0);
        System.exit(0);
    }

    private List<String> StringToList(String str) {
        return Arrays.asList(str.split(" "));
    }
}
