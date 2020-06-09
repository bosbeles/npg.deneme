package desktop.search.factory;

import deneme.desktop.SearchableAction;
import desktop.search.model.Menu;
import desktop.search.model.MenuGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuFactory {


    public static MenuGroup createMenuGroup() {
        MenuGroup group = new MenuGroup();
        group.setMenus(createMenuFactory());
        group.setSearchableActions(SearchableAction.getSearchableActions());

        return group;
    }


    public static List<Menu> createMenuFactory() {
        Menu allMenu = new Menu("All");
        Menu dlpMenu = new Menu("Data Link Processor");
        Menu jreMenu = new Menu("Jreap Processor");


        allMenu.add("Frequently Used", "Link Configuration", "Filter Management", "Routing", "Operator To Operator Message")
                .add("Recently Used", "Filter Templates", "Link Configuration", "Echo Message");

        dlpMenu.add("Title 1", "Item 1.1", "Item 1.2", "Item 1.3")
                .add("Title 2", "Item 2.1")
                .add("Title 3", "Item 3.1", "Item 3.2");

        jreMenu.add("Connection Management", "Link Configuration", "Echo Message")
                .add("Network", "Routing", "Network Connectivity", "Direct Connection", "Connectivity Feedback", "Latency Management", "Secondary Track Number List")
                .add("Filters", "Filter Templates", "Filter Management")
                .add("Others", "Record & Replay", "Link Capabilities");


        List<Menu> menus = new ArrayList<>();
        menus.addAll(Arrays.asList(allMenu, dlpMenu, jreMenu));
        return menus;
    }


}
