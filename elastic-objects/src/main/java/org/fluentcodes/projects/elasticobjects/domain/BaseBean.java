package org.fluentcodes.projects.elasticobjects.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerBoolean;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerDate;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerInteger;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerLong;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.Date;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_AUTHOR;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_CREATION_DATE;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_DESCRIPTION;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_ID;
import static org.fluentcodes.projects.elasticobjects.domain.BaseInterface.F_NATURAL_ID;

/*.{javaHeader}|*/
/**
 * 
 * Base bean as super object for model beans with id, naturalId and description but no annotations.  
 * @author Werner Diwischek
 * @creationDate Wed Dec 21 00:00:00 CET 2016
 * @modificationDate Sat Jan 09 13:58:40 CET 2021
 */
public class BaseBean {
    public static final Logger LOG = LogManager.getLogger(BaseBean.class);
    public static Boolean toBoolean(final Object value) {
        return new ShapeTypeSerializerBoolean().asObject(value);
    }
    public static Integer toInteger(final Object value) {
        return new ShapeTypeSerializerInteger().asObject(value);
    }
    public static String toString(final Object value) {
        return new ShapeTypeSerializerString().asObject(value);
    }
    public static Long toLong(final Object value) {
        return new ShapeTypeSerializerLong().asObject(value);
    }
    public static Date toDate(final Object value) {
        return new ShapeTypeSerializerDate().asObject(value);
    }
/*.{}.*/
    /*.{javaInstanceVars}|*/
   /* The author of the class. */
   private String author;
   /* Used to define the creation of an item. */
   private Date creationDate;
   /* A description of the model used by every model extending BaseClassImpl.  */
   private String description;
   /* The numeric id of an instance of a class. */
   private Long id;
   /* The natural key in @Base */
   private String naturalId;
/*.{}.*/

    /**
     * Just an empty constructor since basic
     */
    public BaseBean() {
    }

    public BaseBean(final String naturalId) {
        this.naturalId = naturalId;
    }

    public BaseBean(final Map<String, Object> configMap) {
        if (configMap == null) {
            throw new EoException("Problem with null configMap.");
        }
        setId(
                toLong(configMap.get(F_ID)));
        setNaturalId(
                toString(configMap.get(F_NATURAL_ID)));
        setDescription(
                toString(configMap.get(F_DESCRIPTION)));
        setCreationDate(
                toDate(configMap.get(F_CREATION_DATE)));
        setAuthor(
                toString(configMap.get(F_AUTHOR)));
    }

    public BaseBean(final BaseConfig  config) {
        setNaturalId(config.getNaturalId());
        this.author = config.getAuthor();
        this.creationDate = config.getCreationDate();
        this.description = config.getDescription();
        this.id = config.getId();
    }

    public void merge(final BaseBean bean) {
        mergeId(bean.getId());
        mergeNaturalId(bean.getNaturalId());
        mergeDescription(bean.getDescription());
        mergeCreationDate(bean.getCreationDate());
        mergeAuthor(bean.getAuthor());
    }

    /*.{javaAccessors}|*/
   public String getAuthor() {
      return this.author;
   }

   public BaseBean setAuthor(final String author) {
      this.author = author;
      return this;
    }

   public boolean hasAuthor() {
      return getAuthor() != null && !getAuthor().isEmpty();
   }

   public Date getCreationDate() {
      return this.creationDate;
   }

   public BaseBean setCreationDate(final Date creationDate) {
      this.creationDate = creationDate;
      return this;
    }

   public boolean hasCreationDate() {
      return getCreationDate() != null;
   }

   public String getDescription() {
      return this.description;
   }

   public BaseBean setDescription(final String description) {
      this.description = description;
      return this;
    }

   public boolean hasDescription() {
      return getDescription() != null && !getDescription().isEmpty();
   }

   public Long getId() {
      return this.id;
   }

   public BaseBean setId(final Long id) {
      this.id = id;
      return this;
    }

   public boolean hasId() {
      return getId() != null;
   }

   public String getNaturalId() {
      return this.naturalId;
   }

   public BaseBean setNaturalId(final String naturalId) {
      this.naturalId = naturalId;
      return this;
    }

   public boolean hasNaturalId() {
      return getNaturalId() != null && !getNaturalId().isEmpty();
   }
/*.{}.*/

    private void mergeNaturalId(final Object value) {
        if (value == null) return;
        if (hasNaturalId()) return;
        setNaturalId(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeId(final Object value) {
        if (value == null) return;
        if (hasId()) return;
        setId(new ShapeTypeSerializerLong().asObject(value));
    }

    private void mergeDescription(final Object value) {
        if (value == null) return;
        if (hasDescription()) return;
        setDescription(new ShapeTypeSerializerString().asObject(value));
    }

    private void mergeCreationDate(final Object value) {
        if (value == null) return;
        if (hasCreationDate()) return;
        setCreationDate(new ShapeTypeSerializerDate().asObject(value));
    }

    private void mergeAuthor(final Object value) {
        if (value == null) return;
        if (hasAuthor()) return;
        setAuthor(new ShapeTypeSerializerString().asObject(value));
    }
}
