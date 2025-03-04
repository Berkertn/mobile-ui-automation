# MOBILE UI Automation Project

This project is a UI automation framework designed for testing mobile applications using Appium, Selenium, JUnit 4, Cucumber BDD, and Extent Reports. The framework supports parallel execution, detailed reporting, and is easily configurable for different environments.

---

## 🚀 **Technologies and Tools**

- **Java 17**: Core programming language.
- **Appium 9.3.0**: For mobile automation.
- **Selenium 4.27.0**: Browser automation support.
- **JUnit 4.13.2**: Test framework.
- **Cucumber 7.11.1**: BDD (Behavior-Driven Development) support.
- **Extent Reports 5.1.2**: Test reporting.
- **Log4j 2.24.3**: Logging.
- **Lombok 1.18.36**: Code simplification.

---

## 📁 **Project Structure**

```
ui-automation/
├─ src/
│   ├─ main/
│   │   ├─ java/org/mobile
│   │   ├─ resources/
│   │   │   ├─ config.properties
│   │   │   ├─ devices/
│   │   │   │   ├─ xx-yy.device.properties
│   ├─ test/
│   │   ├─ java/org/mobile
│   │   ├─ resources/
│   │       ├─ features/
│   │       │   ├─ sample.feature
│   │       ├─ config.properties
│   │       ├─ extent.properties
│   │       └─ spark-config.xml
│   └─ test-output/
└─ pom.xml
```

---

## **Configuration**

### **Device Configs:**

1. Devices configuration files should be located under `src/main/resources/devices/`
2. Device config files must follow the naming convention: `xx-yy.device.properties`
3. Each device needs a unique port configuration.
4. Only devices with `isOnUse=true` in the config file will be used during testing.

Example device configuration:
```properties
deviceName=emulator-5554
deviceUDID=12345678-90AB-CDEF-1234-567890ABCDEF
platformName=Android
isOnUse=true
```

### **Extent Reports Configuration**

File: `src/test/resources/extent.properties`

```properties
extent.reporter.spark.start=true
extent.reporter.spark.out=build/reports/extent-report/spark.html
extent.reporter.spark.config=src/test/resources/spark-config.xml

extent.reporter.json.start=true
extent.reporter.json.out=build/reports/extent-report/extent-report.json
```

---

## **Test Execution**

### **Run Specific Tag:**
```bash
mvn clean test -Dcucumber.filter.tags="@regression"
```

### **Single-Threaded Execution:**
```bash
mvn clean test -Dparallel.choice=none -Dthread.count=1
```

### **Parallel Execution:**
```bash
mvn clean test -Dparallel.choice=methods -Dthread.count=2
```

### **Device Management:**

- List connected devices (android):
```bash
adb -l
```

- List connected devices (iOS):
```bash
xcrun simctl list devices
```

---

## **Key Points**

- The framework uses `maven-surefire-plugin` to handle parallel and single-threaded execution.
- Extent Reports generate HTML, JSON, and Spark reports in the `build/reports` directory.
- Device configurations are dynamically loaded based on the `isOnUse` flag.

