package io.techery.janet;

import rx.Observable;
import rx.functions.Func1;

class StateToAction<A> implements Observable.Transformer<ActionState<A>, A> {
    @Override
    public Observable<A> call(Observable<ActionState<A>> observable) {
        return observable.flatMap(new Func1<ActionState<A>, Observable<A>>() {
            @Override
            public Observable<A> call(ActionState<A> state) {
                switch (state.status) {
                    case START:
                        return Observable.never();
                    case SUCCESS:
                        return Observable.just(state.action);
                    case SERVER_ERROR:
                        return Observable.just(state.action);
                    case FAIL:
                        return Observable.error(state.throwable);
                    default:
                        throw new IllegalArgumentException("Action status is unknown");
                }
            }
        });
    }
}