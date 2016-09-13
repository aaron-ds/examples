package memory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MemoryTest {

    public static void main(String[] args) {

        byte[][] byteArray = new byte[1000][];

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        sleep(10000);

        for (int i = 0; i < 1000; i++) {
            byteArray[i] = new byte[1 * 1024 * 1024];
            sleep(1000);
            byteArray[i] = null;
        }



    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
