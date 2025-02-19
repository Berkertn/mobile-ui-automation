mvn clean test -Dcucumber.filter.tags="@regression"

mvn clean test -Dparallel=true -DparallelMode=same_thread

mvn clean test -Dparallel=true -DparallelMode=concurrent

---
Device Configs:
1. Devices config should be placed in: src/main/resources/devices/
2. Device config should be named: xx-yy.device.properties !
3. Each device needs a port to run
4. If device's isOnUse field is not true device will not be used
----
adb -s emulator-5554 emu avd name
adb -l # list of devices