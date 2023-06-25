package com.thyamix.utils.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ThreadUtils {
    public static final ScheduledExecutorService SCHEDULER = Executors.newSingleThreadScheduledExecutor();
}
