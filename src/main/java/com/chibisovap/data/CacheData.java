package com.chibisovap.data;

import java.time.LocalDate;

public interface CacheData {
    boolean isExistCacheFile();
    boolean isActualCache(LocalDate date);
    void loadFromFile();
    void saveToFile();

    String getValue(String from, String to);
    void addValue(String value);
    int getSize();
    void clear();
}
