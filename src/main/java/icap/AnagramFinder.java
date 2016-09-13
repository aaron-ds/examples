package icap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagramFinder {

    public static void main(String[] args) {
        Map<AnagramFingerprint, List<String>> w = new HashMap<>();

        List<String> words = new ArrayList<>();
        words.add("red");
        words.add("dog");
        words.add("god");
        words.add("alert");
        words.add("word");
        words.add("later");
        words.add("cat");
        words.add("alter");

        for (String word : words) {
            AnagramFingerprint fp = new AnagramFingerprint(word);

            List<String> ws = w.get(fp);
            if (ws != null) {
                ws.add(word);
            } else {
                List<String> t = new ArrayList<>();
                t.add(word);
                w.put(fp, t);
            }
        }

        for(List<String> q : w.values()) {
            if (q.size() > 1) {
                for (String x : q)
                    System.out.println(x);
            }
        }

    }
}
