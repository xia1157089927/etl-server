package com.xia.etl.pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    public final static ExecutorService EXECUTORSERVICE = new ThreadPoolExecutor(10, 20, 0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>());
}
