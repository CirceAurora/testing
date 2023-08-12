package dcp.mc.pstp.errors;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class MustBeInjectedException
        extends IllegalStateException
{
    public MustBeInjectedException() {
        super(elementToName(Thread.currentThread().getStackTrace()[0]) + " must be injected!");
    }

    private static String elementToName(StackTraceElement element) {
        return element.getClassName() + '.' + element.getMethodName();
    }
}
