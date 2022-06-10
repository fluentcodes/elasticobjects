package org.fluentcodes.projects.elasticobjects.testobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ForTestClass {

    private int integer;
    private String string;
    private boolean bool;
    private Date date;
    private byte[] bin;

    private List<String> stringList;
    private Map<String, String> stringMap;

    private ForTestClass forTestClass;
    private List<ForTestClass> forTestList;
    private Map<String, ForTestClass> forTestMap;

    public static final ForTestClass of1() {
        return new ForTestClass()
                .setInteger(1)
                .setBool(true)
                .setString(ForTestString.of1())
                .setDate(new Date(1))
                .setBin("Bin1".getBytes());
    }
    public static final ForTestClass of2() {
        return new ForTestClass()
                .setInteger(2)
                .setBool(false)
                .setString(ForTestString.of2())
                .setDate(new Date(2))
                .setBin("Bin2".getBytes());
    }
    public static final ForTestClass of3() {
        return new ForTestClass()
                .setInteger(3)
                .setBool(false)
                .setString(ForTestString.of3())
                .setDate(new Date(3))
                .setBin("Bin3".getBytes());
    }
    public static final List<ForTestClass> ofList1() {
        List<ForTestClass> list = new ArrayList();
        list.add(of1());
        return list;
    }
    public static final Map<String, ForTestClass> ofMap1() {
        Map<String, ForTestClass> map = new LinkedHashMap<>();
        map.put(ForTestString.ofKey1(), of1());
        return map;
    }

    public static final ForTestClass ofForTest() {
        return of1().setForTestClass(of2());
    }

    public static final ForTestClass ofListForTest() {
        return of1().setForTestList(ofList1());
    }

    public static final ForTestClass ofMapForTest() {
        return of1().setForTestMap(ofMap1());
    }

    public int getInteger() {
        return integer;
    }

    public ForTestClass setInteger(int integer) {
        this.integer = integer;
        return this;
    }

    public String getString() {
        return string;
    }

    public ForTestClass setString(String string) {
        this.string = string;
        return this;
    }

    public boolean isBool() {
        return bool;
    }

    public ForTestClass setBool(boolean bool) {
        this.bool = bool;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ForTestClass setDate(Date date) {
        this.date = date;
        return this;
    }

    public byte[] getBin() {
        return bin;
    }

    public ForTestClass setBin(byte[] bin) {
        this.bin = bin;
        return this;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public ForTestClass setStringList(List<String> stringList) {
        this.stringList = stringList;
        return this;
    }

    public Map<String, String> getStringMap() {
        return stringMap;
    }

    public ForTestClass setStringMap(Map<String, String> stringMap) {
        this.stringMap = stringMap;
        return this;
    }

    public ForTestClass getForTestClass() {
        return forTestClass;
    }

    public ForTestClass setForTestClass(ForTestClass forTestClass) {
        this.forTestClass = forTestClass;
        return this;
    }

    public List<ForTestClass> getForTestList() {
        return forTestList;
    }

    public ForTestClass setForTestList(List<ForTestClass> forTestList) {
        this.forTestList = forTestList;
        return this;
    }

    public Map<String, ForTestClass> getForTestMap() {
        return forTestMap;
    }

    public ForTestClass setForTestMap(Map<String, ForTestClass> forTestMap) {
        this.forTestMap = forTestMap;
        return this;
    }
}
