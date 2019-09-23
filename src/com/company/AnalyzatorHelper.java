package com.company;

import javafx.util.Pair;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AnalyzatorHelper {

    private String _filePath;

    public AnalyzatorHelper(String filePath) {
        _filePath = filePath;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private String ReadFileAsString() {
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get(_filePath)));
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            return data;
        }
    }

    private int GetCountOfConsonantal(String word) {
        int count = 0;
        Pattern pattern = Pattern.compile("[b-df-hj-np-tv-z]");
        for (char symbol: word.toLowerCase().toCharArray()) {
            Matcher matcher = pattern.matcher(String.valueOf(symbol));
            if (matcher.find()) {
                count++;
            }
        }
        return count;
    }

    protected Map<String, Integer> GetStatisticsByConsonantal(String text) {
        Map<String, Integer> countConsonantal = new HashMap<String, Integer>();
        Pattern pattern = Pattern.compile("[\\w]+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = text.substring(matcher.start(), matcher.end());
            if (!countConsonantal.containsKey(word)) {
                countConsonantal.put(word, GetCountOfConsonantal(word));
            }
        }
        return countConsonantal;
    }

    protected void PrintStatistic(Map<String, Integer> statistic) {
        statistic = sortByValue(statistic);
        for (Map.Entry<String, Integer> wordStat: statistic.entrySet()) {
            System.out.println(wordStat.getKey() + " has " + wordStat.getValue() + " consonantal");
        }
    }

    public void ShowStatistics() {
        String data = ReadFileAsString();
        if (data.isEmpty()) {
            return;
        }
        Map<String, Integer> statistic = GetStatisticsByConsonantal(data);
        PrintStatistic(statistic);
    }
}
