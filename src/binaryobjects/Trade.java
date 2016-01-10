package binaryobjects;

import java.util.Date;

public interface Trade {

    long getId();
    void setId(long id);
    Date getTradeDate();
    void setTradeDate(Date tradeDate);
    String getBuySell();
    void setBuySell(String buySell);
    String getBaseCurrency();
    void setBaseCurrency(String baseCurrency);
    long getAmount();
    void setAmount(long amount);
    double getRate();
    void setRate(double rate);
    String getCounterCurrency();
    void setCounterCurrency(String counterCurrency);
    long getContraAmount();
    void setContraAmount(long contraAmount);
    Date getSettlementDate();
    void setSettlementDate(Date settlementDate);
}
