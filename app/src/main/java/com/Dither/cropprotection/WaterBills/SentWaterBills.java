package com.Dither.cropprotection.WaterBills;

public class SentWaterBills {
    public int BillNumber;
    public String WaterUnits;
    public String BillingDate;
    public String MeterReading;
    public String AmountPayable;
    public String MeterNumber;
    public SentWaterBills() {
    }

    public SentWaterBills(int BillNumber, String WaterUnits, String BillingDate, String MeterReading, String AmountPayable, String MeterNumber) {
        this.BillNumber = BillNumber;
        this.WaterUnits = WaterUnits;
        this.BillingDate= BillingDate;
        this.MeterReading = MeterReading;
        this.AmountPayable = AmountPayable;
        this.MeterNumber = MeterNumber;
    }
    public String getMeterNumber() {
        return MeterNumber;
    }
    public void setMeterNumber(String MeterNumber) {
        this.MeterNumber = MeterNumber;
    }

    public String getAmountPayable() {
        return AmountPayable;
    }
    public void setAmountPayable(String AmountPayable) {
        this.AmountPayable = AmountPayable;
    }

    public String getMeterReading() {
        return MeterReading;
    }
    public void setMeterReading(String MeterReading) { this.MeterReading = MeterReading; }

    public String getBillingDate() {
        return BillingDate;
    }
    public void setBillingDate(String BillingDate) {
        this.BillingDate = BillingDate;
    }

    public String getWaterUnits() {
        return WaterUnits;
    }
    public void setWaterUnits(String WaterUnits) {
        this.WaterUnits = WaterUnits;
    }

    public int getBillNumber() {
        return BillNumber;
    }
    public void setBillNumber(int BillNumber) {
        this.BillNumber = BillNumber;
    }

}
