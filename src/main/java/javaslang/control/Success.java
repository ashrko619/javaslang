/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.control;

import javaslang.Function1;
import javaslang.Tuple;
import javaslang.Tuple1;
import javaslang.algebra.HigherKinded1;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A succeeded Try.
 *
 * @param <T> component type of this Success
 * @since 1.0.0
 */
public final class Success<T> implements Try<T> {

    private static final long serialVersionUID = 1L;

    private final T value;

    public Success(T value) {
        this.value = value;
    }

    @Override
    public boolean isFailure() {
        return false;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public T orElseGet(Function1<? super Throwable, ? extends T> other) {
        return value;
    }

    @Override
    public void orElseRun(Consumer<? super Throwable> action) {
        // nothing to do
    }

    @Override
    public <X extends Throwable> T orElseThrow(Function1<? super Throwable, X> exceptionProvider) throws X {
        return value;
    }

    @Override
    public Success<T> recover(Function1<Throwable, ? extends T> f) {
        return this;
    }

    @Override
    public Success<T> recoverWith(Function1<Throwable, Try<T>> f) {
        return this;
    }

    @Override
    public Success<T> onFailure(Consumer<Throwable> f) {
        return this;
    }

    @Override
    public Some<T> toOption() {
        return new Some<>(value);
    }

    @Override
    public Right<Throwable, T> toEither() {
        return new Right<>(value);
    }

    @Override
    public Try<T> filter(Predicate<? super T> predicate) {
        try {
            if (predicate.test(value)) {
                return this;
            } else {
                return new Failure<>(new NoSuchElementException("Predicate does not hold for " + value));
            }
        } catch (Throwable t) {
            return new Failure<>(t);
        }
    }

    @Override
    public Failure<Throwable> failed() {
        return new Failure<>(new UnsupportedOperationException("Success.failed()"));
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        action.accept(value);
    }

    @Override
    public <U> Try<U> map(Function1<? super T, ? extends U> mapper) {
        try {
            return new Success<>(mapper.apply(value));
        } catch (Throwable t) {
            return new Failure<>(t);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U, TRY extends HigherKinded1<U, Try<?>>> Try<U> flatMap(Function1<? super T, TRY> mapper) {
        try {
            return (Try<U>) mapper.apply(value);
        } catch (Throwable t) {
            return new Failure<>(t);
        }
    }

    @Override
    public Tuple1<T> unapply() {
        return Tuple.of(value);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == this) || (obj instanceof Success && Objects.equals(value, ((Success<?>) obj).value));
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return String.format("Success(%s)", value);
    }
}
