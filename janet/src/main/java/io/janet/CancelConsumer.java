package io.janet;

interface CancelConsumer<A> {
    void accept(A action);
}
