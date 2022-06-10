package org.fluentcodes.projects.elasticobjects.utils;

import java.io.Serializable;
import java.util.Set;

public class UnmodifiableSet<E> extends UnmodifiableCollection<E>
        implements Set<E>, Serializable {
    private static final long serialVersionUID = -9215047833775013803L;

    public UnmodifiableSet(Set<? extends E> s)     {super(s);}
    public boolean equals(Object o) {return o == this || c.equals(o);}
    public int hashCode()           {return c.hashCode();}
}
