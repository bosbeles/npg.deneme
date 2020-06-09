package desktop.search.view;

import desktop.search.model.Menu;
import desktop.search.model.SubMenu;
import desktop.search.view.util.Gbc;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.function.Function;

public class TabContentPanel extends JScrollPane {


    private final Menu menu;

    public TabContentPanel(Menu menu) {
        this.menu = menu;

        initPanel();
    }

    public void updatePanel() {
        initPanel();
    }

    private void initPanel() {
        JXPanel content = new JXPanel(new GridBagLayout());


        int i = 0;

        Function<Gbc, Gbc> gbcFunction = gbc -> gbc.fillHorizontal().anchor(GridBagConstraints.WEST);

        SubMenu searchMenu = menu.getSearchMenu();
        if (!searchMenu.getActionList().isEmpty()) {
            content.add(new SubMenuPanel(searchMenu), Gbc.constraints(i++, 0).applyAndBuild(gbcFunction));
        }

        List<SubMenu> subMenus = menu.getSubMenus();
        for (SubMenu subMenu : subMenus) {
            content.add(new SubMenuPanel(subMenu), Gbc.constraints(i++, 0).applyAndBuild(gbcFunction));
        }

        content.add(new JPanel(), Gbc.constraints(i++, 0).fillBoth().build());

        content.setScrollableTracksViewportHeight(false);
        content.setScrollableTracksViewportWidth(true);

        this.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        this.setViewportView(content);

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addPropertyChangeListener("focusOwner",
                        new PropertyChangeListener() {

                            @Override
                            public void propertyChange(PropertyChangeEvent evt) {
                                if (!(evt.getNewValue() instanceof JComponent)) {
                                    return;
                                }
                                JComponent focused = (JComponent) evt.getNewValue();
                                if (content.isAncestorOf(focused)) {
                                    focused.scrollRectToVisible(focused.getBounds());
                                }
                            }
                        });

        validate();


    }
}
