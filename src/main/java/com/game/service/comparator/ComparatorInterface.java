package com.game.service.comparator;

import java.util.Comparator;

public interface ComparatorInterface<T> {
    Comparator<T> getComparator(String order);

}
