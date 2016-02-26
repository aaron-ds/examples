package managedbeans;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class AllocationWatcher {

    static ObjectName name;
    static MBeanServer mBeanServer;

    static {
        try {
            name = new ObjectName(
                    ManagementFactory.THREAD_MXBEAN_NAME);
            mBeanServer = ManagementFactory.getPlatformMBeanServer();
        } catch (MalformedObjectNameException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) {
        AllocationWatcher aw = new AllocationWatcher();
        aw.something();
    }

    public void something() {
        System.out.println("name is " + ManagementFactory.THREAD_MXBEAN_NAME);
//        mBeanServer.invoke(name, )
    }




}
