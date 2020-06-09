package deneme.desktop;

import npg.test.GUITester;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Supplier;

public class SearchEverywhere extends JPanel {
    private JTextField textField;

    /**
     * Create the panel.
     */
    public SearchEverywhere(Runnable closeAction) {
        setBorder(new LineBorder(Color.BLACK));
        setLayout(new BorderLayout(0, 0));
        setPreferredSize(new Dimension(300, 400));

        textField = new JTextField();
        textField.setFont(textField.getFont().deriveFont(16f));

        Border outer = textField.getBorder();
        Border search = new MatteBorder(5, 32, 0, 0,
                new ImageIcon(getClass().getClassLoader().getResource("search.png")));
        textField.setBorder(new CompoundBorder(outer, search));


        add(textField, BorderLayout.NORTH);
        textField.setColumns(20);

        JList list = new JList();
        list.setFocusable(false);
        list.setFixedCellHeight(24);
        list.setModel(new AbstractListModel() {
            String[] values = new String[]{"Echo", "Preferences", "Filter Templates", "Remote Filter",
                    "Network Connectivity Matrix", "Direct Connection List", "Secondary Track Number List",
                    "Latency Threshold", "Operator To Operator"};

            public int getSize() {
                return values.length;
            }

            public Object getElementAt(int index) {
                return values[index];
            }
        });

        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);


        textField.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel");
        textField.getActionMap().put("cancel", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeAction.run();
            }
        });

        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                switch (code) {
                    case KeyEvent.VK_UP: {
                        list.setSelectedIndex(Math.max(0, list.getSelectedIndex() - 1));
                        list.ensureIndexIsVisible(list.getSelectedIndex());
                        break;
                    }

                    case KeyEvent.VK_DOWN: {
                        list.setSelectedIndex(Math.min(list.getModel().getSize() - 1, list.getSelectedIndex() + 1));
                        list.ensureIndexIsVisible(list.getSelectedIndex());
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        textField.addActionListener(e -> {
            System.out.println(list.getSelectedValue());
            closeAction.run();
        });

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                closeAction.run();
            }
        });

    }

    @Override
    public void requestFocus() {
        textField.requestFocus();
    }

    public static void main(String[] args) {
        GUITester.test(()-> new SearchEverywhere(()->{}), "Nimbus");
    }

}
