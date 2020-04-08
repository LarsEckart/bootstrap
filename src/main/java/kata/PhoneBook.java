package kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneBook {

  private List<String> entries = new ArrayList<>();

  public void add(String entry) {

    String[] parts = entry.split(" ");
    String number =
        Arrays.stream(parts)
            .skip(1)
            .collect(Collectors.joining(""));
    entries.add(number);
  }

  public String isConsistent() {
    if (entries.size() <= 1) return "consistent";
    for (int i = 0; i < entries.size(); i++) {
      for (int j = i + 1; j < entries.size(); j++) {
        String entry = entries.get(i);
        String otherEntry = entries.get(j);

        if (entry.startsWith(otherEntry) || otherEntry.startsWith(entry)) {
          return "prefix error";
        }
      }
    }
    return "consistent";
  }
}
