package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypeSerializerString;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Werner on 19.06.2015.
 */
public class PathPattern {
    private ArrayList<Path> paths = new ArrayList<Path>();

    public PathPattern() {
    }

    public PathPattern(boolean allFlag) {
        if (allFlag) {
            paths.add(new Path("+"));
        }
    }

    public PathPattern(String[] path) {
        this.addPaths(path);
    }

    public PathPattern(List<String> paths) {
        this.addPaths(paths);
    }

    public PathPattern(String paths) {
        this();
        setSerialized(paths);
    }

    public ArrayList<Path> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<Path> paths) {
        this.paths = paths;
    }

    public boolean isEmpty() {
        return paths == null || paths.isEmpty() || paths.get(0) == null;
    }

    public boolean isAll() {
        for (Path path : paths) {
            String actual = path.first();
            if (actual.equals(PathElement.MATCHER_ALL) || actual.equals(PathElement.MATCHER)) {
                return true;
            }
        }
        return false;
    }

    public Path getPath(Integer i)  {
        if (i > this.paths.size() - 1) {
            throw new EoException("Size " + this.paths.size() + " is smaller than " + i);
        }
        return this.paths.get(i);
    }

    public List<Path> get() {
        return this.paths;
    }

    public List<String> getFirstPath() {
        List<String> firstPath = new ArrayList<String>();
        for (Path path : this.paths) {
            String first = path.getFirstEntry();
            if (!firstPath.contains(first)) {
                firstPath.add(first);
            }
        }
        return firstPath;
    }

    public List<String> filter(Set<String> fields) {
        List<String> result = new ArrayList<String>();
        filter(fields, result);
        return result;
    }

    public List<String> filter(Set<String> fields, List<String> result) {
        List<String> firstPath = getFirstPath();
        if (fields == null) {
            return result;
        }
        if (fields.size() == 0) {
            return result;
        }
        for (String pathPattern : firstPath) {
            if (pathPattern == null) {
                throw new EoException("Null pathPattern");
            }
            pathPattern = pathPattern.replace("*", ".*");
            pathPattern = pathPattern.replace("+", ".*");
            for (Object fieldObject : fields) {
                String field = new ShapeTypeSerializerString().asObject(fieldObject);
                if (field == null) {
                    continue;
                }
                if (result.contains(field)) {
                    continue;
                }
                if (field.equals(pathPattern)) {
                    result.add(field);
                } else if (field.matches(pathPattern)) {
                    result.add(field);
                }
            }
        }
        return result;
    }

    public List<String> filter(List<String> fields) {
        return this.filter(new LinkedHashSet<>(fields));
    }


    public PathPattern getPathList(String key) {
        PathPattern result = new PathPattern();
        for (Path path : this.paths) {
            boolean allPathFlag = false;
            String pathPattern = path.first();
            if (pathPattern.equals("+")) {
                allPathFlag = true;
            }
            pathPattern = pathPattern.replace("*", ".*");
            pathPattern = pathPattern.replace("+", ".*");
            try {
                if (pathPattern.equals(key)) {
                    /*Path chompPath = path.removeFirst();
                    if (chompPath.size() > 0) {
                        result.addPath(chompPath);
                    }*/
                } else if (key.matches(pathPattern)) {
                    /*Path chompPath = path.removeFirst();
                    if (chompPath.size() == 0 && allPathFlag) {
                        //chompPath.addPaths("+");
                    }
                    if (chompPath.size() > 0) {
                        //result.addPath(chompPath);
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Integer size() {
        return this.paths.size();
    }

    public void addPath(String path) {
        paths.add(new Path(path));
    }


    public void addPaths(List<String> paths) {
        for (String path : paths) {
            if (path == null || "".equals(path)) {
                continue;
            }
            this.addPath(path);
        }
    }

    public void addPaths(String[] paths) {
        for (String path : paths) {
            if (path == null || "".equals(path)) {
                continue;
            }
            this.addPath(path);
        }
    }

    @Override
    public String toString() {
        return getSerialized();
    }

    public String getSerialized() {
        return getSerialized(true);
    }

    public void setSerialized(String pathsAsString) {
        if (pathsAsString == null || pathsAsString.isEmpty()) {
            return;
        }
        addPaths(pathsAsString.split(","));
    }

    public String getSerialized(boolean absolute) {
        StringBuffer buffer = new StringBuffer("");
        int counter = 0;
        for (Path path : this.paths) {
            if (counter > 0) {
                buffer.append(",");
            }
            buffer.append(path.directory(absolute));
            counter++;
        }
        return buffer.toString();
    }

}
