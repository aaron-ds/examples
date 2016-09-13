package unsafe;

import static unsafe.UnsafeAccess.UNSAFE;

public class UnsafeTest {

    private static final long NUM_RECORDS = 1_000_000;
    private static final DirectMemoryTrade TRADE_FLYWEIGHT = new DirectMemoryTrade();

    private static long address;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++)
        {
            System.gc();
            perfRun(i);
        }
    }

    private static void perfRun(final int runNum) {
        long start = System.currentTimeMillis();

        init();

        System.out.format("Memory %,d total, %,d free\n",
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().freeMemory());

        long buyCost = 0;
        long sellCost = 0;

        for (int i = 0; i < NUM_RECORDS; i++) {
            final DirectMemoryTrade trade = get(i);

            if (trade.getSide() == 'B') {
                buyCost += (trade.getPrice() * trade.getQuantity());
            } else {
                sellCost += (trade.getPrice() * trade.getQuantity());
            }
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println(runNum + " - duration " + duration + "ms");
        System.out.println("buyCost = " + buyCost + " sellCost = " + sellCost);

        destroy();
    }

    public static void destroy() {
        UNSAFE.freeMemory(address);
    }


    public static void init() {
        final long requiredHeap = NUM_RECORDS * DirectMemoryTrade.getObjectSize();
        address = UNSAFE.allocateMemory(requiredHeap);

        final byte[] londonStockExchange = {'X', 'L', 'O', 'N'};
        final int venueCode = pack(londonStockExchange);

        final byte[] billiton = {'B', 'H', 'P'};
        final int instrumentCode = pack( billiton);

        for (int i = 0; i < NUM_RECORDS; i++)
        {
            DirectMemoryTrade trade = get(i);

            trade.setTradeId(i);
            trade.setClientId(1);
            trade.setVenueCode(venueCode);
            trade.setInstrumentCode(instrumentCode);

            trade.setPrice(i);
            trade.setQuantity(i);

            trade.setSide((i & 1) == 0 ? 'B' : 'S');
        }
    }

    private static DirectMemoryTrade get(final int index) {
        final long offset = address + (index * DirectMemoryTrade.getObjectSize());
        TRADE_FLYWEIGHT.setObjectOffset(offset);
        return TRADE_FLYWEIGHT;
    }

    private static int pack(final byte[] value)     {
        int result = 0;
        switch (value.length) {
            case 4:
                result |= (value[3]);
            case 3:
                result |= ((int)value[2] << 8);
            case 2:
                result |= ((int)value[1] << 16);
            case 1:
                result |= ((int)value[0] << 24);
                break;

            default:
                throw new IllegalArgumentException("Invalid array size");
        }

        return result;
    }
}
