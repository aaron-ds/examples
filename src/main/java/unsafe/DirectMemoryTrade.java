package unsafe;

import static unsafe.UnsafeAccess.UNSAFE;

public class DirectMemoryTrade {

    private static long offset = 0;
    private static long tradeIdOffset = offset;
    private static long clientIdOffset = offset += 8;
    private static long venueCodeOffset = offset += 8;
    private static long instrumentCodeOffset = offset += 4;
    private static long priceOffset = offset += 4;
    private static long quantityOffset = offset += 8;
    private static long sideOffset = offset += 8;

    private static long objectSize = offset += 2;

    private long objectOffset;

    public static long getObjectSize() {
        return objectSize;
    }

    public void setObjectOffset(long offset) {
        objectOffset = offset;
    }

    public long getTradeId() {
        return UNSAFE.getLong(objectOffset + tradeIdOffset);
    }

    public void setTradeId(long tradeId) {
        UNSAFE.putLong(objectOffset + tradeIdOffset, tradeId);
    }

    public long getClientId() {
        return UNSAFE.getLong(objectOffset + clientIdOffset);
    }

    public void setClientId(long clientId) {
        UNSAFE.putLong(objectOffset + clientIdOffset, clientId);
    }

    public int getVenueCode() {
        return UNSAFE.getInt(objectOffset + venueCodeOffset);
    }

    public void setVenueCode(int venueCode) {
        UNSAFE.putInt(objectOffset + venueCodeOffset, venueCode);
    }

    public long getInstrumentCode() {
        return UNSAFE.getInt(objectOffset + instrumentCodeOffset);
    }

    public void setInstrumentCode(int instrumentCode) {
        UNSAFE.putInt(objectOffset + instrumentCodeOffset, instrumentCode);
    }

    public long getPrice() {
        return UNSAFE.getLong(objectOffset + priceOffset);
    }

    public void setPrice(long price) {
        UNSAFE.putLong(objectOffset + priceOffset, price);
    }

    public long getQuantity() {
        return UNSAFE.getLong(objectOffset + quantityOffset);
    }

    public void setQuantity(long quantity) {
        UNSAFE.putLong(objectOffset + quantityOffset, quantity);
    }

    public char getSide() {
        return UNSAFE.getChar(objectOffset + sideOffset);
    }

    public void setSide(char side) {
        UNSAFE.putChar(objectOffset + sideOffset, side);
    }
}
