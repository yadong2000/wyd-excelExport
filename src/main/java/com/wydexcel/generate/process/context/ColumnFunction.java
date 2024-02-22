package com.wydexcel.generate.process.context;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface ColumnFunction<T, R> extends Function<T, R>, Serializable {
}