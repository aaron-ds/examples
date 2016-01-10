package binaryobjects;

import java.io.*;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TradeTest {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException, ClassNotFoundException {

        System.out.println("----------- Starting to read trades from file -------------");
        long start = System.currentTimeMillis();

        List<Trade> trades = Files.lines(new File("/Users/aarondesouza/dev/trade_data.txt").toPath())
                .map(BinaryTrade::parseTrade)
                .filter(trade -> trade != null)
                .collect(Collectors.toList());

        System.out.printf("Parsed %d trades%n", trades.size());

        long end = System.currentTimeMillis();

        System.out.printf("Time taken to create list was %d mS %n", (end - start));

        start = System.currentTimeMillis();
        testSerialization(trades);
        end = System.currentTimeMillis();

        System.out.printf("Time taken to serialise trades was %d mS %n", (end - start));

        start = System.currentTimeMillis();
        testBatchSerialization(trades);
        end = System.currentTimeMillis();

        System.out.printf("Time taken to batch serialise trades was %d mS %n ", (end - start));

    }

    private static void testSerialization(List<Trade> trades) throws IOException, ClassNotFoundException {
        List<Trade> deserializedTrades = new ArrayList<Trade>();
        byte[] bytes = null;

        for (Trade trade : trades) {
            bytes = serialize(trade);
            deserializedTrades.add((Trade)deserialize(bytes));
        }
        checkResults(trades, deserializedTrades);
    }

    private static void testBatchSerialization(List<Trade> trades) throws IOException, ClassNotFoundException {
        List<Trade> deserializedTrades = new ArrayList<Trade>();
        byte[] bytes = null;

        bytes = serialize(trades);
        deserializedTrades.addAll((List<Trade>)deserialize(bytes));

        checkResults(trades, deserializedTrades);
    }

    private static void checkResults(List<Trade> trades, List<Trade> deserializedTrades) {

        for (int i = 0; i < 1_000_000; i++) {
            Trade t = trades.get(i);
            Trade t2 = deserializedTrades.get(i);
            if (!t.equals(t2)) {
                System.out.println("Results are Bad :-(");
                return;
            }
        }
        System.out.println("Results are Good :-)");
    }

    private static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(bs);
        o.writeObject(obj);
        return bs.toByteArray();
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(bs);
        return in.readObject();
    }
}
