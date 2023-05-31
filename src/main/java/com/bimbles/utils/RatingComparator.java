package com.bimbles.utils;

import java.util.Comparator;
import java.util.List;

public class RatingComparator<Float extends Comparable<Float>> implements Comparator<List<Float>> {
    @Override
    public int compare(List<Float> o1, List<Float> o2) {
        return o1.get(0).compareTo(o2.get(0));
    }
}
