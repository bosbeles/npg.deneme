package desktop.search.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class SubMenu {


    private String name;

    private List<String> actionList = new ArrayList<>();


    public SubMenu(String name) {
        this.name = name;
    }

    public SubMenu add(String... actions) {
        actionList.addAll(Arrays.asList(actions));
        return this;
    }

}
