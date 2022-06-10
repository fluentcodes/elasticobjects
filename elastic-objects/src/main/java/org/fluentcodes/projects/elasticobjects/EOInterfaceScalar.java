package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface EOInterfaceScalar extends EOInterfaceBase, EOInterfaceModel, EOInterfaceCall, EOInterfaceLog, EOInterfaceRole, EOInterfaceSerialize {
    String compare(final EOInterfaceScalar other);
}
