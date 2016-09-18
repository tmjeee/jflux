package org.jflux.javafx;

import javafx.application.Platform;
import org.jflux.PlatformHelper;

public class JavaFXPlatformHelper extends PlatformHelper {
    @Override
    public boolean isUiThread() {
        return Platform.isFxApplicationThread();
    }

    @Override
    public void runInUiThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
