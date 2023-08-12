package dcp.mc.pstp.errors;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public class StaticOnlyInitialization extends UnsupportedOperationException{
    public StaticOnlyInitialization() {
        super("Do not call the constructor");
    }
}
