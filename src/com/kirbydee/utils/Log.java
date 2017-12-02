package com.kirbydee.utils;

public class Log {

    private static final boolean IS_DEBUG_MODE = true;
    private static String CLASS_NAME;
    private static String METHOD_NAME;
    private static int LINE_NUMBER;

    private Log() {
        /* Protect from instantiations */
        throw new AssertionError();
    }

    public static boolean isDebuggable() {
        return IS_DEBUG_MODE;
    }

    private static String createLog(String log) {
        return "[" + CLASS_NAME + "::" + METHOD_NAME + "(" + LINE_NUMBER + ")]" + log;
    }

    private static void getMethodNames() {
        // get the stack tract elements
        StackTraceElement[] sElements = new Throwable().getStackTrace();

        // start at 2 -> caller of DebugLog
        int stackLevel = 2;
        if (sElements.length > 2) {
            for (int i = 2; i < sElements.length; i++) {
                // get method name
                METHOD_NAME = sElements[i].getMethodName();

                // track method name to be NOT one of the following (we want the real caller)
                if (!METHOD_NAME.equals("log") && !METHOD_NAME.equals("error") && !METHOD_NAME.equals("warn") && !METHOD_NAME.equals("hubu")) {
                    stackLevel = i;
                    break;
                }
            }
        } else {
            stackLevel = sElements.length - 1;
        }

        // get names and number of right stack level
        CLASS_NAME = sElements[stackLevel].getFileName();
        METHOD_NAME = sElements[stackLevel].getMethodName();
        LINE_NUMBER = sElements[stackLevel].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.err.println("error - " + createLog(message));
    }

    public static void e(String message, Throwable e) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.err.print("error - " + createLog(message));
        e.printStackTrace();
    }

    public static void i(String message) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.out.println("info - " + createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.out.println("debug - " + createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.out.println("verbose - " + createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.out.println("warn - " + createLog(message));
    }

    public static void w(String message, Throwable e) {
        if (!isDebuggable())
            return;

        getMethodNames();
        System.out.println("warn - " + createLog(message));
        e.printStackTrace();
    }

    public static void hubu(String message) {
        if (!isDebuggable())
            return;

        System.out.println("hubu - " + message);
    }
}
