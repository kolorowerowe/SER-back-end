package com.github.ser.util;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CsvBuilder {


    private final List<String> headers;
    private final List<List<String>> lines;
    private final String separator;

    private static final String LINE_SEPARATOR = "\n";

    public CsvBuilder(List<String> headers) {
        this(headers, ",");
    }

    public CsvBuilder(List<String> headers, String separator) {
        this.headers = headers;
        this.separator = separator;
        this.lines = new ArrayList<>();
    }

    public void addRow(List<String> items) {
        if (items.size() != headers.size()){
            return;
        }

        lines.add(items);

    }


    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.join(separator, headers));
        stringBuilder.append(LINE_SEPARATOR);

        lines.forEach(row -> {
            stringBuilder.append(String.join(separator, row));
            stringBuilder.append(LINE_SEPARATOR);
        });

        return stringBuilder.toString();
    }


}
