package binaryobjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class ObjectTrade implements Trade, Serializable {

    long id;
    Date tradeDate;
    String buySell;
    String baseCurrency;
    long amount;
    double rate;
    String counterCurrency;
    long contraAmount;
    Date settlementDate;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Date getTradeDate() {
        return tradeDate;
    }

    @Override
    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    @Override
    public String getBuySell() {
        return buySell;
    }

    @Override
    public void setBuySell(String buySell) {
        this.buySell = buySell;
    }

    @Override
    public String getBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Override
    public long getAmount() {
        return amount;
    }

    @Override
    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String getCounterCurrency() {
        return counterCurrency;
    }

    @Override
    public void setCounterCurrency(String counterCurrency) {
        this.counterCurrency = counterCurrency;
    }

    @Override
    public long getContraAmount() {
        return contraAmount;
    }

    @Override
    public void setContraAmount(long contraAmount) {
        this.contraAmount = contraAmount;
    }

    @Override
    public Date getSettlementDate() {
        return settlementDate;
    }

    @Override
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectTrade that = (ObjectTrade) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(rate, that.rate) &&
                Objects.equals(contraAmount, that.contraAmount) &&
                Objects.equals(tradeDate, that.tradeDate) &&
                Objects.equals(buySell, that.buySell) &&
                Objects.equals(baseCurrency, that.baseCurrency) &&
                Objects.equals(counterCurrency, that.counterCurrency) &&
                Objects.equals(settlementDate, that.settlementDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tradeDate, buySell, baseCurrency, amount, rate, counterCurrency, contraAmount, settlementDate);
    }
}
