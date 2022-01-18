package org.fluentcodes.projects.elasticobjects.domain.test;

/*.{javaHeader}|*/

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Model class with different types of fields including generic collections, maps and {@link ASubObject} objects.
 */
public class AnObject {
    public static final String F_ID = "id";
    public static final String MY_ASUB_OBJECT = "myASubObject";
    public static final String MY_ASUB_OBJECT_LIST = "myASubObjectList";
    public static final String MY_ASUB_OBJECT_MAP = "myASubObjectMap";
    public static final String MY_AN_OBJECT = "myAnObject";
    public static final String MY_BOOLEAN = "myBoolean";
    public static final String MY_DATE = "myDate";
    public static final String MY_DOUBLE = "myDouble";
    public static final String MY_FLOAT = "myFloat";
    public static final String F_MY_INT = "myInt";
    public static final String MY_LIST = "myList";
    public static final String MY_LONG = "myLong";
    public static final String MY_MAP = "myMap";
    public static final String MY_OBJECT = "myObject";
    public static final String F_MY_STRING = "myString";
    public static final String NATURAL_ID = "naturalId";

    /* The numeric id of an instance of a class. */
    private Long id;
    /* myASubObject */
    private ASubObject myASubObject;
    /* A generic parametrized example List with @ASubObject */
    private List<ASubObject> myASubObjectList;
    /* A generic parametrized example Map with @ASubObject */
    private Map<String, ASubObject> myASubObjectMap;
    /* A AnObject field. */
    private AnObject myAnObject;
    /* myBoolean */
    private Boolean myBoolean;
    /* myDate */
    private Date myDate;
    /* myDouble */
    private Double myDouble;
    /* myFloat */
    private Float myFloat;
    /* myInt */
    private Integer myInt;
    /* myList */
    private List<String> myList;
    /* myLong */
    private Long myLong;
    /* myMap */
    private Map<String, Object> myMap;
    /* A test object thing.  */
    private Object myObject;
    /* Just a small test string used in test models.  */
    private String myString;
    /* The natural key in @Base */
    private String naturalId;

    public Long getId() {
        return this.id;
    }

    public boolean hasId() {
        return getId() != null;
    }

    public AnObject setId(final Long id) {
        this.id = id;
        return this;
    }

    public ASubObject getMyASubObject() {
        return this.myASubObject;
    }

    public boolean hasMyASubObject() {
        return getMyASubObject() != null;
    }

    public AnObject setMyASubObject(final ASubObject myASubObject) {
        this.myASubObject = myASubObject;
        return this;
    }

    public List<ASubObject> getMyASubObjectList() {
        return this.myASubObjectList;
    }

    public boolean hasMyASubObjectList() {
        return getMyASubObjectList() != null && !getMyASubObjectList().isEmpty();
    }

    public AnObject setMyASubObjectList(final List<ASubObject> myASubObjectList) {
        this.myASubObjectList = myASubObjectList;
        return this;
    }

    public Map<String, ASubObject> getMyASubObjectMap() {
        return this.myASubObjectMap;
    }

    public boolean hasMyASubObjectMap() {
        return getMyASubObjectMap() != null && !getMyASubObjectMap().isEmpty();
    }

    public AnObject setMyASubObjectMap(final Map<String, ASubObject> myASubObjectMap) {
        this.myASubObjectMap = myASubObjectMap;
        return this;
    }

    public AnObject getMyAnObject() {
        return this.myAnObject;
    }

    public boolean hasMyAnObject() {
        return getMyAnObject() != null;
    }

    public AnObject setMyAnObject(final AnObject myAnObject) {
        this.myAnObject = myAnObject;
        return this;
    }

    public Boolean getMyBoolean() {
        return this.myBoolean;
    }

    public boolean hasMyBoolean() {
        return getMyBoolean() != null;
    }

    public boolean isMyBoolean() {
        return hasMyBoolean() && getMyBoolean();
    }

    public AnObject setMyBoolean(final Boolean myBoolean) {
        this.myBoolean = myBoolean;
        return this;
    }

    public Date getMyDate() {
        return this.myDate;
    }

    public boolean hasMyDate() {
        return getMyDate() != null;
    }

    public AnObject setMyDate(final Date myDate) {
        this.myDate = myDate;
        return this;
    }

    public Double getMyDouble() {
        return this.myDouble;
    }

    public boolean hasMyDouble() {
        return getMyDouble() != null;
    }

    public AnObject setMyDouble(final Double myDouble) {
        this.myDouble = myDouble;
        return this;
    }

    public Float getMyFloat() {
        return this.myFloat;
    }

    public boolean hasMyFloat() {
        return getMyFloat() != null;
    }

    public AnObject setMyFloat(final Float myFloat) {
        this.myFloat = myFloat;
        return this;
    }

    public Integer getMyInt() {
        return this.myInt;
    }

    public boolean hasMyInt() {
        return getMyInt() != null;
    }

    public AnObject setMyInt(final Integer myInt) {
        this.myInt = myInt;
        return this;
    }

    public List<String> getMyList() {
        return this.myList;
    }

    public boolean hasMyList() {
        return getMyList() != null && !getMyList().isEmpty();
    }

    public AnObject setMyList(final List<String> myList) {
        this.myList = myList;
        return this;
    }

    public Long getMyLong() {
        return this.myLong;
    }

    public boolean hasMyLong() {
        return getMyLong() != null;
    }

    public AnObject setMyLong(final Long myLong) {
        this.myLong = myLong;
        return this;
    }

    public Map<String, Object> getMyMap() {
        return this.myMap;
    }

    public boolean hasMyMap() {
        return getMyMap() != null && !getMyMap().isEmpty();
    }

    public AnObject setMyMap(final Map<String, Object> myMap) {
        this.myMap = myMap;
        return this;
    }

    public Object getMyObject() {
        return this.myObject;
    }

    public boolean hasMyObject() {
        return getMyObject() != null;
    }

    public AnObject setMyObject(final Object myObject) {
        this.myObject = myObject;
        return this;
    }

    public String getMyString() {
        return this.myString;
    }

    public boolean hasMyString() {
        return getMyString() != null && !getMyString().isEmpty();
    }

    public AnObject setMyString(final String myString) {
        this.myString = myString;
        return this;
    }

    public String getNaturalId() {
        return this.naturalId;
    }

    public boolean hasNaturalId() {
        return getNaturalId() != null && !getNaturalId().isEmpty();
    }

    public AnObject setNaturalId(final String naturalId) {
        this.naturalId = naturalId;
        return this;
    }
}
