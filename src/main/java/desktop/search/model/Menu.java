package desktop.search.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class Menu {

    private String title;

    private SubMenu searchMenu = new SubMenu("Search Results");

    private List<SubMenu> subMenus = new ArrayList<>();


    public Menu(String title) {
        this.title = title;
    }

    public Menu add(SubMenu subMenu) {
        subMenus.add(subMenu);
        return this;
    }

    public Menu add(String title, String... actions) {
        return add(new SubMenu(title).add(actions));
    }
}
