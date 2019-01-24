package com.xia.etl.transport;

import com.google.common.collect.Maps;
import com.xia.etl.Page;
import com.xia.etl.pools.ThreadPools;
import com.xia.etl.read.AbstractRead;
import com.xia.etl.write.AbstractWrite;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class Transport {

    private AbstractRead read;
    private AbstractWrite write;
    private AtomicLong submitNumber = new AtomicLong(1L);

    public Transport(AbstractRead read, AbstractWrite write){
        this.read = read;
        this.write = write;
    }

    /**
     * 数据传输
     */
    public void transport(){
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
            Future<Map<String, Object>> future = ThreadPools.EXECUTORSERVICE.submit(transportTask);
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            page.setCurrentPage(page.getCurrentPage() + 1);
        }

    }



}
