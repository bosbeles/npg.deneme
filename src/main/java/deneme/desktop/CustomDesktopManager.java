package deneme.desktop;

import desktop.search.factory.MenuFactory;
import desktop.search.model.Menu;
import desktop.search.model.MenuGroup;
import desktop.search.view.MenuPanel;
import npg.test.GUITester;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomDesktopManager extends DefaultDesktopManager {
    @Override
    public void iconifyFrame(JInternalFrame f) {
        super.iconifyFrame(f);

        /*
        JInternalFrame.JDesktopIcon icon = f.getDesktopIcon();
        Dimension prefSize = icon.getPreferredSize();
        icon.setBounds(f.getX(), f.getY(), prefSize.width, prefSize.height);

         */
    }

    public void cascade() {
        int x = 20, y = 20;

        Collection<JInternalFrame> values = frames.values();
        for (JInternalFrame iFrame : values) {
            if (iFrame.isVisible()) {
                iFrame.setLocation(x, y);
                x += 20;
                y += 20;
            }

        }
    }


    static CustomDesktopManager dm;
    static JDesktopPane desktopPane;
    static JMenu menu;
    static Popup searchPopup;

    public static void main(String args[]) {

        GUITester.test(CustomDesktopManager::createPanel, "Nimbus", f -> {
            f.getJMenuBar().add(menu);
        });


    }

    private static JDesktopPane createPanel() {
        desktopPane = new JDesktopPane();
        dm = new CustomDesktopManager();
        desktopPane.setDesktopManager(dm);

        JPopupMenu popup = new JPopupMenu();
        // add menu items to popup
        JMenuItem close_all = new JMenuItem("Close All");
        close_all.addActionListener(e -> {
            Collection<JInternalFrame> values = frames.values();
            for (JInternalFrame iFrame : values) {
                if (iFrame.isVisible()) {
                    iFrame.setVisible(false);
                }

            }

        });

        popup.add(close_all);
        JMenuItem minimize_all = new JMenuItem("Minimize All");
        minimize_all.addActionListener(e -> {
            Collection<JInternalFrame> values = frames.values();
            for (JInternalFrame iFrame : values) {
                if (iFrame.isVisible()) {
                    try {
                        iFrame.setIcon(true);
                    } catch (PropertyVetoException propertyVetoException) {
                        propertyVetoException.printStackTrace();
                    }
                }

            }
        });
        popup.add(minimize_all);
        JMenuItem restore_all = new JMenuItem("Restore All");
        restore_all.addActionListener(e -> {
            Collection<JInternalFrame> values = frames.values();
            for (JInternalFrame iFrame : values) {
                if (iFrame.isVisible()) {
                    try {
                        iFrame.setIcon(false);
                    } catch (PropertyVetoException propertyVetoException) {
                        propertyVetoException.printStackTrace();
                    }

                }

            }
        });
        popup.add(restore_all);

        JMenuItem cascase_all = new JMenuItem("Cascase All");
        cascase_all.addActionListener(e -> {
            dm.cascade();
        });
        popup.add(cascase_all);


        desktopPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JButton b1 = new JButton(new ImageIcon(CustomDesktopManager.class.getClassLoader().getResource("open-menu.png")));

        desktopPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                b1.setBounds(desktopPane.getWidth() - 80
                        , 100, 50, 40);

            }
        });
        b1.setVisible(true);
        desktopPane.add(b1);
        b1.addActionListener(e->{
            MenuPanel panel = new MenuPanel(MenuFactory.createMenuGroup());



            PopupFactory factory = PopupFactory.getSharedInstance();

            panel.setPreferredSize(new Dimension(
                    Math.min(desktopPane.getWidth()-10, panel.getPreferredSize().width),
                    Math.min(desktopPane.getHeight()-10, panel.getPreferredSize().height)));
            Point p = new Point(desktopPane.getWidth() - 30 - panel.getPreferredSize().width, 140);
            SwingUtilities.convertPointToScreen(p, desktopPane);
            searchPopup = factory.getPopup(desktopPane, panel, p.x, p.y);
            searchPopup.show();
            panel.requestFocus();

        });

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println(evt.getPropertyName() + " : " + evt.getNewValue() + " old = " + evt.getOldValue() );

            }
        });

        InputMap inputMap = desktopPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), "Search Everywhere");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "Search Everywhere");
        desktopPane.getActionMap().put("Search Everywhere", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchEverywhere se = new SearchEverywhere(() -> {
                    searchPopup.hide();
                });


                PopupFactory factory = PopupFactory.getSharedInstance();

                se.setPreferredSize(new Dimension(
                        Math.min(desktopPane.getWidth()-10, se.getPreferredSize().width),
                        Math.min(desktopPane.getHeight()-10, se.getPreferredSize().height)));
                Point p = new Point(desktopPane.getWidth() / 2 - se.getPreferredSize().width / 2, desktopPane.getHeight() / 2 - se.getPreferredSize().height / 2);
                SwingUtilities.convertPointToScreen(p, desktopPane);
                searchPopup = factory.getPopup(desktopPane, se, p.x, p.y);
                searchPopup.show();
                se.requestFocus();

            }
        });


        //createInternalFrames(desktopPane);
        dm.cascade();


        menu = new JMenu("Frames");
        menu.add(createMenuItem("Echo"));
        menu.add(createMenuItem("Operator"));
        menu.add(createMenuItem("Latency"));
        menu.add(createMenuItem("Filter"));
        menu.add(createMenuItem("Filter Template"));
        menu.add(createMenuItem("Routing"));
        menu.add(createMenuItem("Network Connectivity Matrix"));
        menu.add(createMenuItem("Direct Connection List"));
        menu.add(createMenuItem("Secondary Track Number List"));

        return desktopPane;
    }

    private static JMenuItem createMenuItem(String name) {
        JMenuItem jMenuItem = new JMenuItem(name);
        jMenuItem.addActionListener(e -> {
            createInternalFrame(desktopPane, name);
        });
        return jMenuItem;
    }


    private static Map<String, JInternalFrame> frames = new HashMap<>();

    private static void createInternalFrames(JDesktopPane desktopPane, String name) {
        for (int i = 0; i < 10; i++) {
            createInternalFrame(desktopPane, name + " " + i);
        }

    }

    private static void createInternalFrame(JDesktopPane desktopPane, String name) {
        JInternalFrame iFrame = frames.get(name);
        if (iFrame == null) {
            iFrame = new JInternalFrame(name, true, true, true, true);
            iFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            iFrame.setSize(200, 150);
            desktopPane.add(iFrame);
            frames.put(name, iFrame);
        }
        iFrame.setVisible(true);

    }
}