package com.xia.etl.transport;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.xia.etl.Page;
import com.xia.etl.pools.ThreadPools;
import com.xia.etl.read.AbstractRead;
import com.xia.etl.write.AbstractWrite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class Transport {

    private AbstractRead read;
    private AbstractWrite write;
    private AtomicLong submitNumber = new AtomicLong(1L);
    private List<Map<String, Object>> taskList = Lists.newCopyOnWriteArrayList();
    private List<ListenableFuture<Map<String, Object>>> futures = Lists.newArrayList();

    public Transport(AbstractRead read, AbstractWrite write){
        this.read = read;
        this.write = write;
    }

    /**
     * 数据传输
     * @return 执行结果
     */
    public List<Map<String, Object>> transport(){
        Page page = read.getPage();
        for (long i=page.getCurrentPage(); i<=page.getTotalPage(); i++){
            Callable<Map<String, Object>> transportTask = () -> {
                Map<String, Object> res = Maps.newHashMap();
                res.put("flag", submitNumber.longValue());
                System.out.println(res);
                List<Map<String, Object>> exchangeData = read.readData(page);
                write.writeData(exchangeData);
                submitNumber.addAndGet(1L);
                return res;
            };
            ListenableFuture future = ThreadPools.EXECUTORSERVICE_SERVICE.submit(transportTask);
            futures.add(future);
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            page.setCurrentPage(page.getCurrentPage() + 1);
        }
        ListenableFuture<List<Map<String, Object>>> resultsFuture = Futures.successfulAsList(futures);
        try {
            taskList = resultsFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return taskList;

    }




}
