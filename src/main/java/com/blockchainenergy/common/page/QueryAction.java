package com.blockchainenergy.common.page;

import java.util.List;

@FunctionalInterface
public interface QueryAction<T> {
    List<T> execute();
}
