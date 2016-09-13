package binaryobjects;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class TradeFactory {


    private static final long NO_OF_RECORDS = 500_000L;

    private Random random = new Random();

    private enum Currency {
        GBPUSD("GBP", "USD", 1.49265),
        GBPEUR("GBP", "EUR", 1.35883),
        GBPCHZ("GBP", "CHF", 1.47482);

        String base, counter;
        double rate;

        Currency(String base, String counter, double rate) {
            this.base = base;
            this.counter = counter;
            this.rate = rate;
        }

        public String getBase() {
            return base;
        }

        public String getCounter() {
            return counter;
        }

        public double getRate() {
            return rate;
        }
    }

    public static void main(String[] args) throws IOException {

        TradeFactory tf = new TradeFactory();
        tf.generate();

    }



    private void generate() throws IOException {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("/Users/aarondesouza/dev/trade_data_small.txt")));

        long start = System.currentTimeMillis();
        for (int i = 0; i < NO_OF_RECORDS; i++) {
            writer.println(createRecord(i + 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("Time taken to write records was " + (end - start) + "mS");

        writer.close();
    }


    private String createRecord(int id) {
        Currency currency = getCurrency();
        long amount = getAmount();
        long contraAmount = (long)(amount * currency.rate);
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",")
                .append(createDate()).append(",")
                .append(getBuySell()).append(",")
                .append(currency.getBase()).append(",")
                .append(amount).append(",")
                .append(currency.getRate()).append(",")
                .append(currency.getCounter()).append(",")
                .append(contraAmount).append(",")
                .append(createDate());

        return sb.toString();
    }

    private Currency getCurrency() {
        Currency[] values = Currency.values();
        int i = random.nextInt(values.length);

        return values[i];
    }

    private String createDate() {
        int day = random.nextInt(27) + 1;
        int month = random.nextInt(11) + 1;
        return day + "/" + month + "/2015";
    }

    private String getBuySell() {
        int rand = random.nextInt(10);
        return rand < 5 ? "BUY" : "SELL";
    }

    private long getAmount() {
        int amount = random.nextInt(10) + 1;
        return amount * 1_000_000L;
    }
}
