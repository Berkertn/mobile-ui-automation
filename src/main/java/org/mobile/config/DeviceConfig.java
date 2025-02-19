package org.mobile.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mobile.base.DriverManager;

@AllArgsConstructor
@Data
public class DeviceConfig {
    private String deviceName;
    private String deviceUDID;
    private DriverManager.OS_TYPES platform;
    private int port;
    private Boolean isRealDevice;
    private Boolean isOnUse;

    public boolean hasUdid() {
        return deviceUDID != null && !deviceUDID.isEmpty();
    }
}
