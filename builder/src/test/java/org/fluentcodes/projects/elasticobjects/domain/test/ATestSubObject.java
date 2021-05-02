package org.fluentcodes.projects.elasticobjects.domain.test;
/*=>{javaHeader}|*/
/**
 * 
 * A sub object as an example. 
 * @author Werner Diwischek
 * @creationDate Fri Nov 20 00:00:00 CET 2020
 * @modificationDate Sun Jan 31 06:30:46 CET 2021
 */
public class ATestSubObject  {
/*=>{}.*/

/*=>{javaStaticNames}|*/
   public static final String TEST_NAME = "testName";
/*=>{}.*/

/*=>{javaInstanceVars}|*/
   /* Just a small test string used in test models.  */
   private String testName;
/*=>{}.*/

/*=>{javaAccessors}|*/
   public String getTestName() {
      return this.testName;
   }
   public boolean hasTestName() {
      return getTestName() != null && !getTestName().isEmpty();
   }
   public ATestSubObject setTestName(final String testName) {
      this.testName = testName;
      return this;
    }

/*=>{}.*/
}
