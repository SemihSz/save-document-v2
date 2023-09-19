package com.assistant.savedocument.task;

import java.util.function.Function;

/**
 * Created by Semih, 2.07.2023
 */
public interface SimpleTask<T, R> extends Function<T, R> {
}
