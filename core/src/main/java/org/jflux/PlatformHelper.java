package org.jflux;

public abstract class PlatformHelper {

    public abstract boolean isUiThread();
    public abstract void runInUiThread(Runnable r);
    public void checkIsUiThread() {
        if (!isUiThread())
            throw new IllegalStateException("Must be called from UI thread");
    }
    public void checkIsNonUiThread() {
        if (isUiThread()) {
            throw new IllegalStateException("Must be called from non UI thread");
        }
    }

}
