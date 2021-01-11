package my.project.textClient;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Menu {

    private final Map<String, Function<List, List>> mnu;
    private final Map<String, String> help;

    public Menu(Map<String, Function<List, List>> menu, Map<String, String> help) {
        this.mnu = menu;
        this.help = help;
    }

    public Function<List, List> getMenu(String item) {
        return mnu.get(item);
    }

    public Map<String, String> getHelp() {
        return help;
    }
}
