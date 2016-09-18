package org.jflux;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Store {

    private final List<View> viewListeners = new CopyOnWriteArrayList<>();

    private PlatformHelper platformHelper;

    public Store(PlatformHelper platformHelper) {
        this.platformHelper = platformHelper;
    }

    // called from a non-ui thread
    public abstract void callback(Action action);

    // called from ui-thread
    public void register(View view) {
        platformHelper.checkIsUiThread();
        viewListeners.add(view);
    }

    // called from ui-thread
    public void unregister(View view) {
        platformHelper.checkIsUiThread();
        viewListeners.remove(view);
    }

    // called from non-ui thread in this class
    protected void emitEvent(Event event) {
        final Holder h = new Holder(event);
        platformHelper.checkIsNonUiThread();
        for (View view : viewListeners) {
            platformHelper.runInUiThread(()->{
                final Event event1 = h.event;
                view.onEvent(event1);
            });
        }
    }

    static class Holder {
        volatile Event event;
        Holder(Event event) {
            this.event = event;
        }
    }
}
