package io.janet.helper;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import io.janet.ActionState;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * To transform {@link ActionState} to action.
 * <pre>
 *     START - nothing
 *     PROGRESS - nothing
 *     SUCCESS - action with result
 *     FAIL - error. it's necessary to handle it using {@link Subscriber#onError(Throwable)}
 * </pre>
 */
public final class ActionStateToActionTransformer<A> implements ObservableTransformer<ActionState<A>, A>,
        FlowableTransformer<ActionState<A>, A>,
        SingleTransformer<ActionState<A>, A>,
        MaybeTransformer<ActionState<A>, A> {

    @Override public Publisher<A> apply(Flowable<ActionState<A>> upstream) {
        return upstream.flatMap(new Function<ActionState<A>, Publisher<A>>() {
            @Override public Publisher<A> apply(ActionState<A> state) throws Exception {
                switch (state.status) {
                    case START:
                        return Flowable.empty();
                    case PROGRESS:
                        return Flowable.empty();
                    case SUCCESS:
                        return Flowable.just(state.action);
                    case FAIL:
                        return Flowable.error(new JanetActionException(state.exception, state.action));
                    default:
                        throw new IllegalArgumentException("Action status is unknown");
                }
            }
        });
    }

    @Override public MaybeSource<A> apply(Maybe<ActionState<A>> upstream) {
        return upstream.flatMap(new Function<ActionState<A>, Maybe<A>>() {
            @Override public Maybe<A> apply(ActionState<A> state) throws Exception {
                switch (state.status) {
                    case START:
                        return Maybe.empty();
                    case PROGRESS:
                        return Maybe.empty();
                    case SUCCESS:
                        return Maybe.just(state.action);
                    case FAIL:
                        return Maybe.error(new JanetActionException(state.exception, state.action));
                    default:
                        throw new IllegalArgumentException("Action status is unknown");
                }
            }
        });
    }

    @Override public ObservableSource<A> apply(Observable<ActionState<A>> upstream) {
        return upstream.flatMap(new Function<ActionState<A>, Observable<A>>() {
            @Override public Observable<A> apply(ActionState<A> state) throws Exception {
                switch (state.status) {
                    case START:
                        return Observable.empty();
                    case PROGRESS:
                        return Observable.empty();
                    case SUCCESS:
                        return Observable.just(state.action);
                    case FAIL:
                        return Observable.error(new JanetActionException(state.exception, state.action));
                    default:
                        throw new IllegalArgumentException("Action status is unknown");
                }
            }
        });
    }

    @Override public SingleSource<A> apply(Single<ActionState<A>> upstream) {
        return upstream.flatMap(new Function<ActionState<A>, Single<A>>() {
            @Override public Single<A> apply(ActionState<A> state) throws Exception {
                switch (state.status) {
                    case START:
                        return Single.never();
                    case PROGRESS:
                        return Single.never();
                    case SUCCESS:
                        return Single.just(state.action);
                    case FAIL:
                        return Single.error(new JanetActionException(state.exception, state.action));
                    default:
                        throw new IllegalArgumentException("Action status is unknown");
                }
            }
        });
    }
}
