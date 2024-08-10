package com.czareg.battlefield.feature.common.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/*
https://github.com/pivovarit/articles/blob/master/java-random-stream/src/main/java/com/pivovarit/stream/ImprovedRandomSpliterator.java
 */
class ImprovedRandomSpliterator<T, L extends RandomAccess & List<T>> implements Spliterator<T> {

    private final Random random;
    private final List<T> source;
    private int size;

    ImprovedRandomSpliterator(L source, Supplier<? extends Random> random) {
        Objects.requireNonNull(source, "source can't be null");
        Objects.requireNonNull(random, "random can't be null");

        this.source = source;
        this.random = random.get();
        this.size = this.source.size();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (size > 0) {
            int nextIdx = random.nextInt(size);
            int lastIdx = --size;

            T last = source.get(lastIdx);
            T elem = source.set(nextIdx, last);
            action.accept(elem);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Spliterator<T> trySplit() {
        return null;
    }

    @Override
    public long estimateSize() {
        return source.size();
    }

    @Override
    public int characteristics() {
        return SIZED;
    }
}