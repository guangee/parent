package com.coding.common;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 容器启动工具类.
 *
 * @author fengfei
 */
@Slf4j
public class ContainerHelper {


    private static volatile boolean running = true;

    private static List<Container> cachedContainers;

    public static void start(List<Container> containers) {

        cachedContainers = containers;

        // 启动所有容器
        startContainers();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            synchronized (ContainerHelper.class) {

                // 停止所有容器.
                stopContainers();
                running = false;
                ContainerHelper.class.notify();
            }
        }));

        synchronized (ContainerHelper.class) {
            while (running) {
                try {
                    ContainerHelper.class.wait();
                } catch (Throwable e) {
                    log.error("start", e);
                }
            }
        }
    }

    private static void startContainers() {
        for (Container container : cachedContainers) {
            log.info("starting container [{}]", container.getClass().getName());
            container.start();
            log.info("container [{}] started", container.getClass().getName());
        }
    }

    private static void stopContainers() {
        for (Container container : cachedContainers) {
            log.info("stopping container [{}]", container.getClass().getName());
            try {
                container.stop();
                log.info("container [{}] stopped", container.getClass().getName());
            } catch (Exception ex) {
                log.warn("container stopped with error", ex);
            }
        }
    }
}
