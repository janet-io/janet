package io.janet;

final public class ActionHolder<A> {

    private final A origin;
    private A action;

    private ActionHolder(A action) {
        this.origin = action;
        this.action = action;
    }

    public static <A> ActionHolder create(A action) {
        return new ActionHolder<A>(action);
    }

    public A action() {
        return action;
    }

    public ActionHolder newAction(A action) {
        if (action == null) {
            throw new IllegalArgumentException("action == null");
        }
        this.action = action;
        return this;
    }

    boolean isOrigin(Object action) {
        return origin == action;
    }
}
