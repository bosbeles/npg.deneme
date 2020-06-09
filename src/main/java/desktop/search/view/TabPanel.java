package desktop.search.view;

import desktop.search.model.Menu;
import desktop.search.model.MenuGroup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TabPanel extends JPanel {

    private final MenuGroup menuGroup;
    private List<TabItem> tabs = new ArrayList<>();
    private Consumer<Integer> onSelectedAction;

    public TabPanel(MenuGroup menuGroup) {

        this.menuGroup = menuGroup;

        initializePanel();
    }

    private void initializePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        List<Menu> menus = menuGroup.getMenus();

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TabItem ti = (TabItem) e.getSource();

                for (int i = 0; i < tabs.size(); i++) {
                    tabs.get(i).setSelected(i == ti.index);
                }

                onSelectedAction.accept(ti.index);
            }
        };

        for (int i = 0; i < menus.size(); i++) {
            TabItem item = new TabItem(i, menus.get(i).getTitle(), i == 0);
            tabs.add(item);
            item.addMouseListener(mouseListener);
            add(item);
        }


    }

    public void onSelected(Consumer<Integer> onSelectedAction) {
        this.onSelectedAction = onSelectedAction;
    }


    private static class TabItem extends JPanel {
        int index;
        String title;
        boolean selected;

        private JPanel selectedPanel;
        private JLabel selectedLabel;
        private Font f;

        public TabItem(int index, String title, boolean selected) {

            this.index = index;
            this.title = title;
            this.selected = selected;
            initPanel();
        }

        private void initPanel() {
            setOpaque(false);

            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{0, 0};
            gridBagLayout.rowHeights = new int[]{0, 10, 0};
            gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
            setLayout(gridBagLayout);

            selectedLabel = new JLabel(title);

            f = selectedLabel.getFont();
            selectedLabel.setForeground(new Color(255, 255, 255));
            selectedLabel.setHorizontalAlignment(SwingConstants.CENTER);
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
            gbc_lblNewLabel.ipady = 5;
            gbc_lblNewLabel.ipadx = 5;
            gbc_lblNewLabel.insets = new Insets(10, 5, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = 0;
            add(selectedLabel, gbc_lblNewLabel);

            selectedPanel = new JPanel();
            selectedPanel.setPreferredSize(new Dimension(50, 5));
            setSelected(selected);

            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.anchor = GridBagConstraints.SOUTH;
            gbc_panel.fill = GridBagConstraints.HORIZONTAL;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 1;
            add(selectedPanel, gbc_panel);


        }


        public void setSelected(boolean selected) {
            this.selected = selected;

            if (selected) {
                selectedPanel.setOpaque(true);
                selectedPanel.setBackground(new Color(0, 120, 215));
                // bold
                //selectedLabel.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                selectedLabel.setForeground(Color.WHITE);


            } else {
                selectedPanel.setOpaque(false);
                //selectedLabel.setFont(f.deriveFont(f.getStyle() & ~Font.BOLD));
                selectedLabel.setForeground(new Color(205, 205, 205));
            }
            repaint();
        }

        public boolean isSelected() {
            return selected;
        }


    }


}
