package ipc;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

public class IpcTest {
    public static void main(String... args) throws IOException {
        for (int i = 0; i < 10; i++)
            doTest();
    }

    public static void doTest() {
        final ByteBuffer writeBuffer = ByteBuffer.allocateDirect(64 * 1024);
        final ByteBuffer readBuffer = writeBuffer.slice();
        final AtomicInteger readCount = new PaddedAtomicInteger();
        final AtomicInteger writeCount = new PaddedAtomicInteger();

        for(int i=0;i<3;i++)
            performTiming(writeBuffer, readBuffer, readCount, writeCount);
        System.out.println();
    }

    private static void performTiming(ByteBuffer writeBuffer, final ByteBuffer readBuffer, final AtomicInteger readCount, final AtomicInteger writeCount) {
        writeBuffer.clear();
        readBuffer.clear();
        readCount.set(0);
        writeCount.set(0);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] bytes = new byte[128];
                while (!Thread.interrupted()) {
                    int rc = readCount.get(), toRead;
                    while ((toRead = writeCount.get() - rc) <= 0) ;
                    for (int i = 0; i < toRead; i++) {
                        byte len = readBuffer.get();
                        if (len == -1) {
                            // rewind.
                            readBuffer.clear();
//                            rc++;
                        } else {
                            int num = readBuffer.getInt();
                            if (num != rc)
                                throw new AssertionError("Expected " + rc + " but got " + num) ;
                            rc++;
                            readBuffer.get(bytes, 0, len - 4);
                        }
                    }
                    readCount.lazySet(rc);
                }
            }
        });
        t.setDaemon(true);
        t.start();
        Thread.yield();
        long start = System.nanoTime();
        int runs = 30 * 1000 * 1000;
        int len = 32;
        byte[] bytes = new byte[len - 4];
        int wc = writeCount.get();
        for (int i = 0; i < runs; i++) {
            if (writeBuffer.remaining() < len + 1) {
                // reader has to catch up.
                while (wc - readCount.get() > 0) ;
                // rewind.
                writeBuffer.put((byte) -1);
                writeBuffer.clear();
            }
            writeBuffer.put((byte) len);
            writeBuffer.putInt(i);
            writeBuffer.put(bytes);
            writeCount.lazySet(++wc);
        }
        // reader has to catch up.
        while (wc - readCount.get() > 0) ;
        t.interrupt();
        t.stop();
        long time = System.nanoTime() - start;
        System.out.printf("Message rate was %.1f M/s offsets %d %d %d%n", runs * 1e3 / time
                , addressOf(readBuffer) - addressOf(writeBuffer)
                , addressOf(readCount) - addressOf(writeBuffer)
                , addressOf(writeCount) - addressOf(writeBuffer)
        );
    }

    // assumes -XX:+UseCompressedOops.
    public static long addressOf(Object... o) {
        long offset = UNSAFE.arrayBaseOffset(o.getClass());
        return UNSAFE.getInt(o, offset) * 8L;
    }

    public static final Unsafe UNSAFE = getUnsafe();
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    private static class PaddedAtomicInteger extends AtomicInteger {
        public long p2, p3, p4, p5, p6, p7;

        public long sum() {
//            return 0;
            return p2 + p3 + p4 + p5 + p6 + p7;
        }
    }
}
