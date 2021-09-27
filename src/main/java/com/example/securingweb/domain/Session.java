package com.example.securingweb.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private Date date;

    private Integer startAmount;

    private Integer cash;

    private Integer nonCash;

    private Integer cashReturn;

    private Integer nonCashReturn;

    private Integer cleaning;

    private Integer collection;

    private Integer terminal;

    private Integer endAmount;

    private Integer bonus;

    private String note;

    private boolean checked;

    public Session() {
    }

    public Integer getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(Integer startAmount) {
        this.startAmount = startAmount;
    }

    public Integer getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(Integer endAmount) {
        this.endAmount = endAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getNonCash() {
        return nonCash;
    }

    public void setNonCash(Integer nonCash) {
        this.nonCash = nonCash;
    }

    public Integer getCashReturn() {
        return cashReturn;
    }

    public void setCashReturn(Integer cashReturn) {
        this.cashReturn = cashReturn;
    }

    public Integer getNonCashReturn() {
        return nonCashReturn;
    }

    public void setNonCashReturn(Integer nonCashReturn) {
        this.nonCashReturn = nonCashReturn;
    }

    public Integer getCleaning() {
        return cleaning;
    }

    public void setCleaning(Integer cleaning) {
        this.cleaning = cleaning;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTerminal() {
        return terminal;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public void setTerminal(Integer terminal) {
        this.terminal = terminal;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
