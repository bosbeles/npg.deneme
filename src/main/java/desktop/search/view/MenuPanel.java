package desktop.search.view;

import deneme.desktop.SearchableAction;
import desktop.search.factory.MenuFactory;
import desktop.search.model.Menu;
import desktop.search.model.MenuGroup;
import npg.test.GUITester;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuPanel extends JPanel {

    private MenuGroup menuGroup;
    private Menu selectedMenu;
    private TabContentPanel selectedTab;


    public MenuPanel(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;

        initializePanel();
    }

    private void initializePanel() {
        setPreferredSize(new Dimension(800, 600));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.BLACK));


        SearchPanel searchPanel = new SearchPanel();


        add(searchPanel);

        TabPanel tabPanel = new TabPanel(menuGroup);
        add(tabPanel);

        CardLayout cardLayout = new CardLayout();
        JPanel contentJPanel = new JPanel(cardLayout);



        Map<Integer, TabContentPanel> tabs = new HashMap<>();
        for (int i = 0; i < menuGroup.getMenus().size(); i++) {
            Menu menu = menuGroup.getMenus().get(i);
            TabContentPanel comp = new TabContentPanel(menu);
            contentJPanel.add(menu.getTitle(), comp);
            tabs.put(i, comp);
        }


        add(contentJPanel);

        selectedMenu = menuGroup.getMenus().get(0);
        selectedTab = tabs.get(0);
        tabPanel.onSelected(index -> {
            selectedMenu = menuGroup.getMenus().get(index);
            cardLayout.show(contentJPanel, selectedMenu.getTitle());
            selectedTab = tabs.get(index);
           // KeyboardFocusManager.getCurrentKeyboardFocusManager().focusNextComponent();
        });

        searchPanel.onUpdate(text -> {
            List<SearchableAction> result = SearchableAction.search(menuGroup.getSearchableActions(), text);
            selectedMenu.getSearchMenu().setActionList(result.stream().map(sa -> sa.toString()).collect(Collectors.toList()));
            selectedTab.updatePanel();
        });

    }


    public static void main(String[] args) {
        GUITester.test(() -> new MenuPanel(MenuFactory.createMenuGroup()));
    }


}
