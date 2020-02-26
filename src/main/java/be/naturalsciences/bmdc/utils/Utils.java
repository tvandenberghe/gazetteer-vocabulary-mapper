/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.naturalsciences.bmdc.utils;

import be.naturalsciences.bmdc.mapper.general.RemoteEntry;
import static be.naturalsciences.bmdc.mapper.places.general.GazetteerApi.replacements;
import be.naturalsciences.bmdc.mapper.general.MatchType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import be.naturalsciences.bmdc.mapper.places.model.GazetteerPlace;
import java.util.LinkedHashSet;

/**
 *
 * @author thomas
 */
public class Utils {

    public static String simplifyLocationWord(String location) {
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String toReplace = entry.getKey();
            String replacement = entry.getValue();
            Pattern p = Pattern.compile(toReplace);
            Matcher m = p.matcher(location);
            if (m.find()) {
                location = location.replaceAll(toReplace, replacement).trim();
            }

        }
        return location;
    }

    public static List<String> simplifyLocationWord(List<String> locations) {

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String toReplace = entry.getKey();
            String replacement = entry.getValue();
            Pattern p = Pattern.compile(toReplace);

            int i = 0;
            for (String location : locations) {
                Matcher m = p.matcher(location);
                if (m.find()) {
                    location = location.replaceAll(toReplace, replacement).trim();
                    locations.set(i, location);
                }
                i++;
            }
        }

        return locations;
    }

    public static boolean simplifiedWordContainsViceVersa(String source, String subItem) {
        if (subItem.contains("shire")) {
            int a = 5;
        }
        boolean r = false;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            String toReplace = entry.getKey();
            String replacement = entry.getValue();
            Pattern p = Pattern.compile(toReplace);
            Matcher m = p.matcher(subItem);
            if (m.find()) {
                subItem = subItem.replaceAll(toReplace, replacement).trim();
                r = StringUtils.wholeWordContainsViceVersa(source, subItem);
            }

            Matcher m2 = p.matcher(source);
            if (m2.find()) {
                source = source.replaceAll(toReplace, replacement).trim();
                r = r || StringUtils.wholeWordContainsViceVersa(source, subItem);
            }
        }
        return r;
    }

    public static <T> List<T> addAndReturnNewList(List<T> list, T el) {
        list = new ArrayList(list);
        list.add(el);
        return list;

    }

    public static <K, V extends RemoteEntry> Map<K, Set<V>> addElementToMapSetValue(Map<K, Set<V>> map, K key, V element, boolean ignoreCheck) {
        Set<V> l = map.get(key);
        if (l != null) {
            if (ignoreCheck || MatchType.canAddToCollection(l, element)) {
                l.add(element);
                map.put(key, l);
            }
        } else {
            map.put(key, new LinkedHashSet<>(Arrays.asList(element)));
        }
        return map;
    }

    public static <K, V extends GazetteerPlace> Map<K, List<V>> addElementToMapListValue(Map<K, List<V>> map, K key, V element) {
        List<V> l = map.get(key);

        if (l != null) {
            l.add(element);
            map.put(key, l);
        } else {
            map.put(key, new ArrayList<>(Arrays.asList(element)));
        }
        return map;
    }
}
