package org.jflux.swing;

import org.jflux.PlatformHelper;

import javax.swing.*;

public class SwingPlatformHelper extends PlatformHelper {
    @Override
    public boolean isUiThread() {
        return SwingUtilities.isEventDispatchThread();
    }

    @Override
    public void runInUiThread(Runnable runnable) {
        SwingUtilities.invokeLater(runnable);
    }
}
