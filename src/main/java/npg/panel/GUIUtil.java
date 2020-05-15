package npg.panel;

import javax.swing.*;
import java.awt.*;

public class GUIUtil {

    public static void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container) component, enable);
            }
        }
    }


    public static ImageIcon getIcon(String path) {
        return new ImageIcon(GUIUtil.class.getClassLoader().getResource(path));
    }
}
