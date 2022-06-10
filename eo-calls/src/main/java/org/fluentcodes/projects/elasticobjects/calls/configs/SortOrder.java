package org.fluentcodes.projects.elasticobjects.calls.configs;

import java.util.Comparator;

//https://stackoverflow.com/questions/28607191/how-to-use-a-java8-lambda-to-sort-a-stream-in-reverse-order
public enum SortOrder {
    NONE(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return 0;
        }
    }),
    ASC(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }),
    DESC(new Comparator<String>() {
        @Override
        public int compare(String o1, String o2) {
            return o2.compareToIgnoreCase(o1);
        }
    });

    private final Comparator<String> comparator;

    SortOrder(Comparator<String> comparator) {
        this.comparator = comparator;
    }

    public Comparator<String> getComparator() {
        return comparator;
    }


}
