package com.convenient.excel.util;

import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

@Log
public class UniqueIDUtil implements InitializingBean {


    public static IdWorker w = null;

    @Value("wydstation.uid.workid")
    private static Long workerId;
    @Value("wydstation.uid.datacenterId")
    private static Long datacenterId;

    @Override
    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public static void init() {
        if (workerId <= 0) {
            log.info("server id error, please check config file!");
            System.exit(-1);
        }
        w = new IdWorker(workerId, datacenterId);
    }

    public static void init(long workerId, long datacenterId) {

        if (workerId <= 0) {
            log.info("server id error, please check config file!");
            System.exit(-1);
        }
        w = new IdWorker(workerId, datacenterId);
    }


    /**
     * 获取主键ID
     *
     * @return
     * @throws Exception
     */
    public static synchronized long getUniqueID() throws Exception {
        if (w == null) {
            w = new IdWorker(6, 6);
        }
        return w.nextId();
    }
}
