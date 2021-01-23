package xpath.parser;

import java.util.HashMap;
import java.util.List;

public interface Queryable {

    List<Queryable> findAllChildren(String tag, List<HashMap<String, String>> properties, int indexToReturn);

    List<Queryable> findParent();

    public List<Queryable> findAllDescendents(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllAncestors(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllFollowing(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllFollowingSibling(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllPreceding(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllPrecedingSibling(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllFollowingSiblingAll(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllFollowingAll(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllPrecedingAll(String tag, List<HashMap<String,String>> properties, int indexToReturn);

    public List<Queryable> findAllPrecedingSiblingAll(String tag, List<HashMap<String,String>> properties, int indexToReturn);
}
