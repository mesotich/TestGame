package com.game.service.filter;

import java.util.List;
import java.util.function.Predicate;

public interface FilterInterface<T> {

    void addFilter(Predicate<T> filter);

    List<Predicate<T>> getFilterList();
}
