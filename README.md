mvn clean test -Dcucumber.filter.tags="@regression"

mvn clean test -Dparallel=true -DparallelMode=same_thread

mvn clean test -Dparallel=true -DparallelMode=concurrent
