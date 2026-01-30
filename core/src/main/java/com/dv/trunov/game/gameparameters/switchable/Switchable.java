package com.dv.trunov.game.gameparameters.switchable;

public interface Switchable<T extends Enum<T>> {

    @SuppressWarnings("unchecked")
    default T next() {
        T[] values = (T[]) ((Enum<?>) this).getDeclaringClass().getEnumConstants();
        int index = getIndex();
        return index < values.length - 1 ? values[index + 1] : (T) this;
    }

    @SuppressWarnings("unchecked")
    default T previous() {
        T[] values = (T[]) ((Enum<?>) this).getDeclaringClass().getEnumConstants();
        int index = getIndex();
        return index > 0 ? values[index - 1] : (T) this;
    }

    default int getIndex() {
        return ((Enum<?>) this).ordinal();
    }

    default int size() {
        return ((Enum<?>) this).getDeclaringClass().getEnumConstants().length;
    }
}
