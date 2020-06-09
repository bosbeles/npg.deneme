package desktop.search.model;

import deneme.desktop.SearchableAction;
import lombok.Data;

import java.util.List;

@Data
public class MenuGroup {
    private List<Menu> menus;
    private List<SearchableAction> searchableActions;
}
