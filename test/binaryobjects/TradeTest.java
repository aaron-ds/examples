package binaryobjects;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TradeTest {


    public static void main(String[] args) throws IOException, ParseException, InterruptedException, ClassNotFoundException {

        List<Trade> trades = new ArrayList<Trade>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        BufferedReader br = new BufferedReader(new FileReader("/Users/aarondesouza/dev/trade_data.txt"));

        System.out.println("----------- Starting to read trades from file -------------");
        long start = System.currentTimeMillis();
        String line;
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            Trade trade = new BinaryTrade();
            trade.setId(Long.parseLong(tokens[0]));
            trade.setTradeDate(df.parse(tokens[1]));
            trade.setBuySell(tokens[2]);
            trade.setBaseCurrency(tokens[3]);
            trade.setAmount(Long.parseLong(tokens[4]));
            trade.setRate(Double.parseDouble(tokens[5]));
            trade.setCounterCurrency(tokens[6]);
            trade.setContraAmount(Long.parseLong(tokens[7]));
            trade.setSettlementDate(df.parse(tokens[8]));

            trades.add(trade);
        }

        long end = System.currentTimeMillis();

        System.out.println("time taken to create list was " + (end - start) + "mS");

        start = System.currentTimeMillis();
        testSerialization(trades);
        end = System.currentTimeMillis();

        System.out.println("Time taken to serialise trades was " + (end - start) + "mS");

        start = System.currentTimeMillis();
        testBatchSerialization(trades);
        end = System.currentTimeMillis();

        System.out.println("Time taken to batch serialise trades was " + (end - start) + "mS");

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
