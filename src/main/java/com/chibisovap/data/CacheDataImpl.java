package com.chibisovap.data;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class CacheDataImpl implements  CacheData {

    private final Set<String> cachedCurrencies = new ConcurrentSkipListSet<>();
    private final static String FILE_NAME = "cache.txt";
    private LocalDate lastUpdate;


    @Override
    public boolean isActualCache(LocalDate date){
        return lastUpdate!= null && date.isEqual(lastUpdate);
    }

    @Override
    public void loadFromFile(){
        String line;
        cachedCurrencies.clear();
        if (isExistCacheFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
                line = reader.readLine();
                if (line != null) {
                    try {
                        lastUpdate = LocalDate.parse(line);
                    } catch (DateTimeParseException e) {
                        return;
                    }
                }
                while ((line = reader.readLine()) != null) {
                    cachedCurrencies.add(line);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isExistCacheFile() {
        File file = new File(FILE_NAME);
        return file.exists() && file.isFile();
    }

    @Override
    public void saveToFile() {
        if (lastUpdate != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                writer.write(lastUpdate.toString() + System.lineSeparator());
                for (String currency : cachedCurrencies) {
                    writer.write(currency + System.lineSeparator());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getValue(String from, String to) {
        for (String currency : cachedCurrencies) {
            if (currency.startsWith(from + " => " + to))
                return currency;
        }
        return null;
    }

    @Override
    public void addValue(String value) {
        if(lastUpdate == null) {
            lastUpdate = LocalDate.now();
        } else {

            if (!isActualCache(LocalDate.now())) {
                lastUpdate = LocalDate.now();
                cachedCurrencies.clear();
            }
        }
        cachedCurrencies.add(value);
    }

    @Override
    public int getSize() {
        return cachedCurrencies.size();
    }

    @Override
    public void clear() {
        cachedCurrencies.clear();
    }
}
