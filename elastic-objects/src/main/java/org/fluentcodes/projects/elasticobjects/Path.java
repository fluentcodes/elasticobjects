package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.PathElement.SAME;

/**
 * Path creates from a string a special of elements splitted by te delimiter
 */
public class Path {
    public static final String DELIMITER = "/";
    private final PathElement[] entries;
    private final Boolean absolute;

    Path(boolean isAbsolute, PathElement... pathElements) {
        this.absolute = isAbsolute;
        if (pathElements == null || pathElements.length == 0) {
            this.entries = new PathElement[0];
            return;
        }
        this.entries = pathElements;
    }

    public Path(String... pathEntries) {
        if (pathEntries == null || pathEntries.length == 0) {
            this.entries = new PathElement[0];
            this.absolute = false;
            return;
        }
        if (pathEntries.length == 1) {
            if (pathEntries[0] == null || pathEntries[0].equals(SAME)) {
                this.entries = new PathElement[0];
                this.absolute = false;
                return;
            } else if (pathEntries[0].equals(DELIMITER)) {
                absolute = true;
                this.entries = new PathElement[0];
                return;
            }
        }

        String concatenated = String.join(DELIMITER, pathEntries);
        if (concatenated.startsWith(DELIMITER)) {
            absolute = true;
            concatenated = concatenated.replaceAll("^[/]+", "");
        } else {
            absolute = false;
        }
        this.entries = Arrays.stream(concatenated.split(DELIMITER))
                .map(x -> x.replaceAll("\\s", ""))
                .filter(x -> !x.equals("null"))
                .filter(x -> !x.equals(""))
                .filter(x -> !x.equals(SAME))
                .map(PathElement::new)
                .toArray(PathElement[]::new);
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public boolean isFilter() {
        return getParent().isFilter();
    }

    protected List<PathElement> getEntries() {
        return Arrays.asList(this.entries);
    }

    public String first() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[0].getKey();
    }

    public String last() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[entries.length - 1].getKey();
    }

    public boolean isEmpty() {
        return entries == null || entries.length == 0;
    }

    /**
     * Gets the first entry.
     *
     * @return returns the first entry of the path.
     */
    public String getFirstEntry() {
        if (isEmpty()) {
            return null;
        }
        return getFirstPathElement().getKey();
    }

    public String getLastEntry() {
        if (isEmpty()) {
            return null;
        }
        return getLastPathElement().getKey();
    }

    public PathElement getFirstPathElement() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[0];
    }

    public PathElement getLastPathElement() {
        if (isEmpty()) {
            return null;
        }
        return this.entries[entries.length - 1];
    }

    public Path createChildPath() {
        if (size() < 1) {
            return new Path("/");
        }
        return new Path(this.absolute, Arrays.copyOfRange(this.entries, 1, entries.length));
    }

    public Path getParentPath() {
        if (this.entries.length == 0) {
            return new Path(Path.DELIMITER);
        }
        try {
            return new Path(this.absolute, Arrays.copyOfRange(this.entries, 0, entries.length - 1));
        } catch (IllegalArgumentException e) {
            throw new EoException(e);
        }
    }

    public PathElement getParent() {
        if (size() < 1) {
            throw new EoException("No entry in path");
        }
        return entries[entries.length - 1];
    }

    public int size() {
        return this.entries.length;
    }

    public PathElement getPathElement(int i) {
        return this.entries[i];
    }

    public String get(int i) {
        return getPathElement(i).getKey();
    }

    @Override
    public String toString() {
        return directory(true);
    }

    public String directory() {
        return directory(false);
    }

    public String directory(boolean includeModels) {
        if (isEmpty()) {
            return isAbsolute() ? DELIMITER : SAME;
        }
        StringBuilder result = isAbsolute() ? new StringBuilder(DELIMITER) : new StringBuilder();
        for (PathElement element : entries) {
            if (includeModels) {
                result.append(element.toString());
            } else {
                result.append(element.getKey());
            }
            result.append(DELIMITER);
        }
        return result.toString().replaceAll(DELIMITER + "$", "");
    }

    public String parent() {
        return getParentPath().directory(true);
    }
}
