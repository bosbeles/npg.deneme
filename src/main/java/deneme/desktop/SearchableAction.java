package deneme.desktop;

import java.util.*;
import java.util.stream.Collectors;

public class SearchableAction implements Comparable<SearchableAction> {
    private static final Locale TR = Locale.forLanguageTag("tr-TR");
    private String actionName;
    private Set<String> tags;


    public SearchableAction(String actionName) {
        this(new String[]{actionName});

    }

    public SearchableAction(String... nameAndTags) {
        actionName = nameAndTags[0];
        tags = new HashSet<>();
        for (int i = 0; i < nameAndTags.length; i++) {
            tags.add(nameAndTags[i].toUpperCase(Locale.ENGLISH));
            tags.add(nameAndTags[i].toUpperCase(TR));
        }

    }

    @Override
    public String toString() {
        return actionName;
    }

    public static List<SearchableAction> search(List<SearchableAction> list, String keyword) {
        if (keyword.trim().isEmpty()) return Collections.emptyList();
        String[] keywords = keyword.split("\\W+");
        Set<String> keywordSet = Arrays.stream(keywords).flatMap(k -> {
            String keywordInUpper = k.toUpperCase(TR);
            String keywordInUpperEnglish = k.toUpperCase(Locale.ENGLISH);
            return Arrays.asList(keywordInUpper, keywordInUpperEnglish).stream();

        }).collect(Collectors.toSet());

        List<SearchableAction> candidates = list.stream().filter(a -> {
            for (String k : keywordSet) {
                for (String tag : a.tags) {
                    if (tag.contains(k)) {
                        return true;
                    }
                }
            }
            return false;
        }).collect(Collectors.toList());
        candidates.sort((a1, a2) -> {
            int score1 = 0, score2 = 0;
            for (String k : keywordSet) {
                for (String tag : a1.tags) {
                    if (tag.contains(k)) {
                        score1++;
                    }
                }
                for (String tag : a2.tags) {
                    if (tag.contains(k)) {
                        score2++;
                    }
                }
            }
            return Integer.compare(score2, score1);
        });

        return candidates.stream().limit(4).collect(Collectors.toList());
    }


    public static void main(String[] args) {
        List<SearchableAction> list = getSearchableActions();

        List<SearchableAction> searchResult = search(list, "Close Pencereleri");
        searchResult.forEach(System.out::println);


    }

    public static List<SearchableAction> getSearchableActions() {
        SearchableAction a1 = new SearchableAction("Echo", "Yankı", "Ping");
        SearchableAction a2 = new SearchableAction("Operator to Operator Message", "Operator", "Operatör", "Operatör Mesaj");
        SearchableAction a3 = new SearchableAction("Latency Threshold Message", "Gecikme Eşiği");
        SearchableAction a4 = new SearchableAction("Filter Templates", "Filtre Şablonları");
        SearchableAction a5 = new SearchableAction("Filters", "Filtreler");
        SearchableAction a6 = new SearchableAction("Routing", "Yönlendirme");
        SearchableAction a7 = new SearchableAction("Network Connectivity Matrix", "Ağ Bağlantı Matrisi");
        SearchableAction a8 = new SearchableAction("Direct Connection List", "Doğrudan Bağlantı Listesi");
        SearchableAction a9 = new SearchableAction("Preferences", "Ayarlar");
        SearchableAction a10 = new SearchableAction("Link Monitor", "Bağlantı Monitörü");
        SearchableAction a11 = new SearchableAction("Link Configuration", "Bağlantı Ayarları");
        SearchableAction a12 = new SearchableAction("Close All Windows", "Tüm Pencereleri Kapat");
        SearchableAction a13 = new SearchableAction("Minimize All Windows", "Tüm Pencereleri Küçült");
        SearchableAction a14 = new SearchableAction("Restore All Windows", "Tüm Pencereleri Aç");
        SearchableAction a15 = new SearchableAction("Cascase All Windows", "Tüm Pencereleri Diz");
        SearchableAction a16 = new SearchableAction("Close Other Windows", "Diğer Pencereleri Kapat");


        List<SearchableAction> list = new ArrayList<>();
        list.add(a1);
        list.add(a2);
        list.add(a3);
        list.add(a4);
        list.add(a5);
        list.add(a6);
        list.add(a7);
        list.add(a8);
        list.add(a9);
        list.add(a10);
        list.add(a11);
        list.add(a12);
        list.add(a13);
        list.add(a14);
        list.add(a15);
        list.add(a16);
        return list;
    }

    @Override
    public int compareTo(SearchableAction o) {
        return actionName.compareTo(o.actionName);
    }
}
