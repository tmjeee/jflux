package org.jflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// called within UI thread
public class Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    static class InvokerThread extends Thread {
        InvokerThread(Runnable r) {
            super(r);
            setDaemon(true);
            setName(String.format("InvokerThread-%s", System.currentTimeMillis()));
        }
    }

    private final List<Store> listeners = new CopyOnWriteArrayList<>();

    private final PlatformHelper platformHelper;

    public Dispatcher(PlatformHelper platformHelper, Store... stores) {
        this.platformHelper = platformHelper;
        for (Store store : stores) {
            listeners.add(store);
        }
    }

    protected void dispatch(Action action) {
        platformHelper.checkIsUiThread();
        final Holder h = new Holder(action);
        for (Store store : listeners) {
            // todo: temporary solution, pooling mechanism needed
            new InvokerThread(()->{
                Action action1 = h.action; // not really needed, there is a hb between before and after starting a thread / or thread pool submit
                logger.debug("dispatch action "+action1);
                store.callback(action1);
            }).start();
        }
    }

    static class Holder {
        volatile Action action;
        Holder(Action action) {
            this.action = action;
        }
    }
}
