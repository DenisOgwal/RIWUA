package com.example.cropprotection.WaterBills;

public class SentWaterBillPayments {
    public int BillNumber;
    public String amountpaid;
    public String PaymentDate;
    public String PaidBy;
    public String Contact;
    public int paymentid;
    public SentWaterBillPayments() {
    }

    public SentWaterBillPayments(int BillNumber, String amountpaid, String PaymentDate, String PaidBy, String Contact, int paymentid) {
        this.BillNumber = BillNumber;
        this.amountpaid = amountpaid;
        this.PaymentDate= PaymentDate;
        this.PaidBy = PaidBy;
        this.Contact = Contact;
        this.paymentid = paymentid;
    }
    public int getBillNumber() {
        return BillNumber;
    }
    public void setBillNumber(int BillNumber) {
        this.BillNumber = BillNumber;
    }

    public String getamountpaid() {
        return amountpaid;
    }
    public void setamountpaid(String amountpaid) {
        this.amountpaid = amountpaid;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }
    public void setPaymentDate(String PaymentDate) { this.PaymentDate = PaymentDate; }

    public String getPaidBy() {
        return PaidBy;
    }
    public void setPaidBy(String PaidBy) {
        this.PaidBy = PaidBy;
    }

    public String getContact() { return Contact; }
    public void setContact(String Contact) {
        this.Contact = Contact;
    }

    public int getpaymentid() {
        return paymentid;
    }
    public void setpaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

}
