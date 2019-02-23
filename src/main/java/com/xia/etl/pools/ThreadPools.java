package com.xia.etl.pools;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPools {
    public final static ExecutorService EXECUTORSERVICE = new ThreadPoolExecutor(10, 10000, 0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>());

    public final static ListeningExecutorService EXECUTORSERVICE_SERVICE =
            MoreExecutors.listeningDecorator(EXECUTORSERVICE);


}
