package org.fluentcodes.projects.elasticobjects.domain.test;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/*=>{javaHeader}|*/
/**
 * 
 * Model class with different types of fields including generic collections, maps and {@link link} ASubObject objects. 
 * @author Werner Diwischek
 * @creationDate null
 * @modificationDate Sun Jan 31 10:42:16 CET 2021
 */
public class ATestObject  {
/*=>{}.*/

/*=>{javaStaticNames}|*/
   public static final String TEST_ATEST_OBJECT = "testATestObject";
   public static final String TEST_ATEST_SUB_OBJECT = "testATestSubObject";
   public static final String TEST_DATE = "testDate";
   public static final String TEST_INT = "testInt";
   public static final String TEST_LIST = "testList";
   public static final String TEST_MAP = "testMap";
   public static final String TEST_STRING = "testString";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   /* A ATestObject field. */
   private ATestObject testATestObject;
   /* testATestSubObject */
   private ATestSubObject testATestSubObject;
   /* testDate */
   private Date testDate;
   /* testInt */
   private Integer testInt;
   /* testList */
   private ArrayList testList;
   /* testMap */
   private HashMap testMap;
   /* Just a small test string used in test models.  */
   private String testString;
/*=>{}.*/

/*=>{javaAccessors}|*/
   public ATestObject getTestATestObject() {
      return this.testATestObject;
   }
   public boolean hasTestATestObject() {
      return getTestATestObject() != null;
   }
   public ATestObject setTestATestObject(final ATestObject testATestObject) {
      this.testATestObject = testATestObject;
      return this;
    }

   public ATestSubObject getTestATestSubObject() {
      return this.testATestSubObject;
   }
   public boolean hasTestATestSubObject() {
      return getTestATestSubObject() != null;
   }
   public ATestObject setTestATestSubObject(final ATestSubObject testATestSubObject) {
      this.testATestSubObject = testATestSubObject;
      return this;
    }

   public Date getTestDate() {
      return this.testDate;
   }
   public boolean hasTestDate() {
      return getTestDate() != null;
   }
   public ATestObject setTestDate(final Date testDate) {
      this.testDate = testDate;
      return this;
    }

   public Integer getTestInt() {
      return this.testInt;
   }
   public boolean hasTestInt() {
      return getTestInt() != null;
   }
   public ATestObject setTestInt(final Integer testInt) {
      this.testInt = testInt;
      return this;
    }

   public ArrayList getTestList() {
      return this.testList;
   }
   public boolean hasTestList() {
      return getTestList() != null && !getTestList().isEmpty();
   }
   public ATestObject setTestList(final ArrayList testList) {
      this.testList = testList;
      return this;
    }

   public HashMap getTestMap() {
      return this.testMap;
   }
   public boolean hasTestMap() {
      return getTestMap() != null && !getTestMap().isEmpty();
   }
   public ATestObject setTestMap(final HashMap testMap) {
      this.testMap = testMap;
      return this;
    }

   public String getTestString() {
      return this.testString;
   }
   public boolean hasTestString() {
      return getTestString() != null && !getTestString().isEmpty();
   }
   public ATestObject setTestString(final String testString) {
      this.testString = testString;
      return this;
    }

/*=>{}.*/
}
