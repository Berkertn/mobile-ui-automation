package org.mobile.base;

import org.mobile.config.DeviceConfig;
import org.mobile.utils.DevicesConfigReader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.mobile.config.LogConfig.logDebug;

public class DeviceManager {
    private static final List<DeviceConfig> deviceConfigs = DevicesConfigReader.getDeviceConfigs();
    private static final ConcurrentHashMap<Long, DeviceConfig> deviceMap = new ConcurrentHashMap<>();

    public static synchronized DeviceConfig getAvailableDevice() {
        long threadId = Thread.currentThread().getId();

        if (deviceMap.containsKey(threadId)) {
            logDebug("[THREAD-" + threadId + "] Already assigned device: " + deviceMap.get(threadId).getDeviceName());
            return deviceMap.get(threadId);
        }

        for (DeviceConfig device : deviceConfigs) {
            if (!deviceMap.containsValue(device)) {
                logDebug("[THREAD-" + threadId + "] Assigned new device: " + device.getDeviceName());
                return device;
            }
        }

        throw new RuntimeException("No available devices! Increase the number of devices in config.");
    }

    public static void releaseDevice() {
        deviceMap.remove(Thread.currentThread().getId());
    }

    public static DeviceConfig getCurrentDevice() {

        DeviceConfig config = deviceMap.computeIfAbsent(Thread.currentThread().getId(), k -> {
            logDebug("Assigning new device to thread: " + k);
            return getAvailableDevice();
        });
        logDebug("Assigned device config: " + config);
        return config;
    }
}
