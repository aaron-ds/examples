package mappedbytebuffer;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferTest {

    public static final int PAGE_SIZE = 1024 * 4;
    public static final long FILE_SIZE = PAGE_SIZE * 2000L * 1000L;
    public static final byte[] BLANK_PAGE = new byte[PAGE_SIZE];

    public static void main(String[] args) throws Exception{
        RandomAccessFile file = new RandomAccessFile("file.dat", "rw");

        for (long i = 0; i < FILE_SIZE; i += PAGE_SIZE)
        {
            file.write(BLANK_PAGE, 0, PAGE_SIZE);
        }

        file.close();

        FileChannel channel = file.getChannel();
        MappedByteBuffer bb = channel.map(FileChannel.MapMode.READ_WRITE,
                0, 1024 * 1_000_000);

    }
}
