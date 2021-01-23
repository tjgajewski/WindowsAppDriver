package xpath.parser;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Command {

    int scope;
    String tag;
    List<HashMap<String,String>> propertyConditions;
    int indextToReturn;

    public Command(int scope, String tag, List<HashMap<String,String>> propertyConditions, int indexToReturn){
        this.scope = scope;
        this.tag = tag;
        this.propertyConditions = propertyConditions;
        this.indextToReturn=indexToReturn;

    }

    public List<Queryable> execute(Queryable queryable){
        List<Queryable> results;
        switch(scope)
        {
            case Scope.CHILD:
                results=queryable.findAllChildren(tag, propertyConditions, indextToReturn);
                break;
            case Scope.DESCENDENT:
                results=queryable.findAllDescendents(tag, propertyConditions, indextToReturn);
                break;
            case Scope.PARENT:
                results=queryable.findParent();
                break;
            case Scope.ANCESTOR:
                results=queryable.findAllAncestors(tag, propertyConditions, indextToReturn);
                break;
            case Scope.FOLLOWING:
                results=queryable.findAllFollowing(tag, propertyConditions, indextToReturn);
                break;
            case Scope.FOLLOWINGSIBLING:
                results=queryable.findAllFollowingSibling(tag, propertyConditions, indextToReturn);
                break;
            case Scope.PRECEDING:
                results=queryable.findAllPreceding(tag, propertyConditions, indextToReturn);
                break;
            case Scope.PRECEDINGSIBLING:
                results=queryable.findAllPrecedingSibling(tag, propertyConditions, indextToReturn);
                break;
            case Scope.FOLLOWING_ALL:
                results=queryable.findAllFollowingAll(tag, propertyConditions, indextToReturn);
                break;
            case Scope.FOLLOWINGSIBLING_ALL:
                results=queryable.findAllFollowingSiblingAll(tag, propertyConditions, indextToReturn);
                break;
            case Scope.PRECEDING_ALL:
                results=queryable.findAllPrecedingAll(tag, propertyConditions, indextToReturn);
                break;
            case Scope.PRECEDINGSIBLING_ALL:
                results=queryable.findAllPrecedingSiblingAll(tag, propertyConditions, indextToReturn);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + scope);
        }
        return results;
    }



}
