package org.fluentcodes.projects.elasticobjects.domain;

import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerBoolean;

import java.util.Date;

/*.{javaHeader}|*/
/**
 * 
 * Basic bean as super interface for other cached items.  
 * @author Werner Diwischek
 * @creationDate Mon Dec 21 00:00:00 CET 2020
 * @modificationDate Thu Jan 07 16:15:03 CET 2021
 */
public interface BaseInterface {
/*.{}.*/
/*.{javaStaticNames}|*/
   String F_AUTHOR = "author";
   String F_CREATION_DATE = "creationDate";
   String F_DESCRIPTION = "description";
   String F_ID = "id";
   String F_NATURAL_ID = "naturalId";
/*.{}.*/

/*.{javaAccessors}|*/
   public String getAuthor();

   default boolean hasAuthor() {
      return getAuthor() != null && !getAuthor().isEmpty();
   }

   public Date getCreationDate();

   default boolean hasCreationDate() {
      return getCreationDate() != null;
   }

   public String getDescription();

   default boolean hasDescription() {
      return getDescription() != null && !getDescription().isEmpty();
   }

   public Long getId();

   default boolean hasId() {
      return getId() != null;
   }

   public String getNaturalId();

   default boolean hasNaturalId() {
      return getNaturalId() != null && !getNaturalId().isEmpty();
   }

/*.{}.*/

   default Date getModificationDate() {
      return new Date();
   }


}
