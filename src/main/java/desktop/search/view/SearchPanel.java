package desktop.search.view;

import org.jdesktop.swingx.JXSearchField;
import org.jdesktop.swingx.JXSearchPanel;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.function.Consumer;

public class SearchPanel extends JPanel {

    private JTextField textField;
    private Consumer<String> onUpdateFunction;


    public SearchPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(50, 40));

        textField = new JTextField() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                if (getText().length() == 0) {
                    int h = getHeight();
                    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    Insets ins = getInsets();
                    FontMetrics fm = g.getFontMetrics();
                    int c0 = getBackground().getRGB();
                    int c1 = getForeground().getRGB();
                    int m = 0xfefefefe;
                    int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
                    g.setColor(new Color(c2, true));
                    g.drawString("Type to search commands...", ins.left, h / 2 + fm.getAscent() / 2 - 2);
                }
            }
        };

        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        Border outer = textField.getBorder();
        Border search = new MatteBorder(5, 32, 0, 0,
                new ImageIcon(getClass().getClassLoader().getResource("search.png")));
        textField.setBorder(new CompoundBorder(outer, search));

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.weighty = 1.0;
        gbc_textField.weightx = 1.0;
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridy = 0;
        gbc_textField.gridx = 0;
        add(textField, gbc_textField);
        textField.setColumns(10);
        //textField.setFocusable(false);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                doSearch();

            }


            @Override
            public void insertUpdate(DocumentEvent e) {
                doSearch();


            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                doSearch();

            }


        });

    }

    private void doSearch() {
        if (onUpdateFunction != null) {
            onUpdateFunction.accept(textField.getText());
        }
    }

    public void onUpdate(Consumer<String> onUpdateFunction) {
        this.onUpdateFunction = onUpdateFunction;
    }

}
