package binaryobjects;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


public class BinaryTrade implements Trade, Serializable {

    public static final long MILLISECONDS_IN_DAY = 86_400_000L;
    private static int OFFSET = 0;
    private static final int ID = OFFSET;                           //long - 8 bytes
    private static final int TRADE_DATE = OFFSET += 8;              //custom date - 2 bytes
    private static final int BUY_SELL = OFFSET += 2;                //custom - 1 byte
    private static final int BASE_CURRENCY = OFFSET += 1;           //custom - 1 byte
    private static final int AMOUNT = OFFSET += 1;                  //long - 8 bytes
    private static final int RATE = OFFSET += 8;                    //double - 8 bytes
    private static final int COUNTER_CURRENCY = OFFSET += 8;        //custom - 1 byte
    private static final int CONTRA_AMOUNT = OFFSET += 1;           //long - 8 bytes
    private static final int SETTLEMENT_DATE = OFFSET += 8;         //custom date - 2 bytes
    private static final int OBJECT_SIZE = OFFSET += 2;

    private byte[] data = new byte[OBJECT_SIZE];

    @Override
    public long getId() {
        return getLong(ID);
    }

    @Override
    public void setId(long id) {
        putLong(id, ID);
    }

    @Override
    public Date getTradeDate() {
        long date = (long)(data[TRADE_DATE] & 0xFF);
        date += (((long)(data[TRADE_DATE + 1] & 0xFF)) << 8);
        date *= MILLISECONDS_IN_DAY;
        return new Date(date);
    }

    @Override
    public void setTradeDate(Date tradeDate) {
        long date = tradeDate.getTime();
        date /= MILLISECONDS_IN_DAY;
        //we only need two bytes to represent the dates we are interested in
        data[TRADE_DATE] = (byte)date;
        data[TRADE_DATE + 1] = (byte)(date >>> 8);
    }

    @Override
    public String getBuySell() {
        return BuySell.fromBytes(data[BUY_SELL]).getBuySell();
    }

    @Override
    public void setBuySell(String buySell) {
        data[BUY_SELL] = BuySell.valueOf(buySell).getCode();
    }

    @Override
    public String getBaseCurrency() {
        return Currency.fromBytes(data[BASE_CURRENCY]).getCurrency();
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        Currency c = Currency.valueOf(baseCurrency);
        data[BASE_CURRENCY] = c.getByte();
    }

    @Override
    public long getAmount() {
        return getLong(AMOUNT);
    }

    @Override
    public void setAmount(long amount) {
        putLong(amount, AMOUNT);
    }

    @Override
    public double getRate() {
        byte[] bytes = new byte[8];
        for (int i = 0, offset = RATE; i < 8; i++, offset++) {
            bytes[i] = data[offset];
        }
        return ByteBuffer.wrap(bytes).getDouble();
    }

    @Override
    public void setRate(double rate) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(rate);
        for (int i = 0, offset = RATE; i < 8; i++, offset++) {
            data[offset] = bytes[i];
        }
    }

    @Override
    public String getCounterCurrency() {
        return Currency.fromBytes(data[COUNTER_CURRENCY]).getCurrency();
    }

    @Override
    public void setCounterCurrency(String counterCurrency) {
        Currency c = Currency.valueOf(counterCurrency);
        data[COUNTER_CURRENCY] = c.getByte();
    }

    @Override
    public long getContraAmount() {
        return getLong(CONTRA_AMOUNT);
    }

    @Override
    public void setContraAmount(long contraAmount) {
        putLong(contraAmount, CONTRA_AMOUNT);
    }

    @Override
    public Date getSettlementDate() {
        long date = (long)(data[SETTLEMENT_DATE] & 0xFF);
        date += (((long)(data[SETTLEMENT_DATE + 1] & 0xFF)) << 8);
        date *= MILLISECONDS_IN_DAY;
        return new Date(date);
    }

    @Override
    public void setSettlementDate(Date settlementDate) {
        long date = settlementDate.getTime();
        date /= MILLISECONDS_IN_DAY;
        //we only need two bytes to represent the dates we are interested in
        data[SETTLEMENT_DATE] = (byte)date;
        data[SETTLEMENT_DATE + 1] = (byte)(date >>> 8);
    }

    private void putLong(long value, int offset) {
        // little endian
        for (int i = 0; i < 8; i++, offset++) {
            data[offset] = (byte)(value >> (i * 8));
        }
    }

    private long getLong(int offset) {
        // little endian
        long value = 0;
        for (int i = 0; i < 8; i++, offset++) {
            // get the byte, make it unsigned, cast to a long and the shift it left
            value += ((long)(data[offset] & 0xFF)) << (i * 8);
        }
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryTrade that = (BinaryTrade) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    private enum BuySell {
        BUY("BUY", (byte)0),
        SELL("SELL", (byte)1);

        private String buySell;
        private byte code;

        BuySell(String buySell, byte code) {
            this.buySell = buySell;
            this.code = code;
        }

        public String getBuySell() {
            return buySell;
        }

        public byte getCode() {
            return code;
        }

        public static BuySell fromBytes(byte b) {
            return b == (byte)0 ? BuySell.BUY : BuySell.SELL;
        }
    }

    private enum Currency {
        GBP("GBP", (byte)0),
        EUR("EUR", (byte)1),
        USD("USD", (byte)2),
        CHF("CHF", (byte)3);

        private String currency;
        private byte code;

        Currency(String currency, byte code) {
            this.currency = currency;
            this.code = code;
        }

        public String getCurrency() {
            return currency;
        }

        public byte getByte() {
            return code;
        }

        public static Currency fromBytes(byte b) {
            Currency[] currencies = Currency.values();
            for (int i = 0; i < currencies.length; i++) {
                if (b == currencies[i].getByte()) {
                    return currencies[i];
                }
            }
            throw new RuntimeException("Currency not known");
        }

    }
}
