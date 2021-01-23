package infrastructure.xpath;

import application.element.factory.WindowsBy;
import application.element.factory.WindowsElement;
import com.sun.jna.ptr.PointerByReference;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElementArray;
import xpath.parser.Queryable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ElementQueryable implements Queryable {

    public WindowsElement getElement() {
        return element;
    }

    WindowsElement element;
    String xpath;

    public ElementQueryable(WindowsElement element, String xpath){
        this.xpath=xpath;
        this.element=element;
    }

    @Override
    public List<Queryable> findAllChildren(String tag, List<HashMap<String, String>> properties, int indexToReturn) {


        IUIAutomationElementArray elements = element.getIUIAutomationElement().findAll(buildCondition(tag, properties),new WindowsBy("xpath",xpath),2);
        List<Queryable> queryableList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            WindowsElement currentElement = new WindowsElement(elements.getElement(i), element.getWrappedDriver());
            queryableList.add(new ElementQueryable(currentElement,xpath));
        }
        return queryableList;
    }

    @Override
    public List<Queryable> findParent() {
        List<Queryable> queryables = new ArrayList<>();
        queryables.add(new ElementQueryable(element.getParent(),xpath));
        return queryables;
    }

    @Override
    public List<Queryable> findAllDescendents(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        IUIAutomationElementArray elements = element.getIUIAutomationElement().findAll(buildCondition(tag, properties),new WindowsBy("xpath",xpath),4);
        List<Queryable> queryableList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            WindowsElement currentElement = new WindowsElement(elements.getElement(i), element.getWrappedDriver());
            queryableList.add(new ElementQueryable(currentElement,xpath));
        }
        if(indexToReturn!=-1){
            List<Queryable> results = new ArrayList<>();
            results.add(queryableList.get(indexToReturn));
            return results;
        }
        return queryableList;
    }

    @Override
    public List<Queryable> findAllAncestors(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        IUIAutomationElementArray elements = element.getIUIAutomationElement().findAll(buildCondition(tag, properties),new WindowsBy("xpath",xpath),16);
        List<Queryable> queryableList = new ArrayList<>();
        for(int i = 0; i < elements.getLength(); i++){
            WindowsElement currentElement = new WindowsElement(elements.getElement(i), element.getWrappedDriver());
            queryableList.add(new ElementQueryable(currentElement,xpath));
        }
        if(indexToReturn!=-1){
            List<Queryable> results = new ArrayList<>();
            results.add(queryableList.get(indexToReturn));
            return results;
        }
        return queryableList;
    }

    @Override
    public List<Queryable> findAllFollowing(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllFollowingSibling(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        String runtimeId = element.getRuntimeId().toString();
        List<WindowsElement> siblings = element.getAllSiblings();
        HashMap<Integer,String> siblingIndexes = new HashMap<>();
        boolean afterElement = false;
        for(WindowsElement sibling:siblings){
            if(afterElement)
            siblingIndexes.put(siblingIndexes.size(),sibling.getRuntimeId().toString());

            if(element.getRuntimeId().toString().equals(runtimeId))
            afterElement=true;
        }
        List<Queryable> allResults = new ElementQueryable(element.getParent(),xpath).findAllChildren(tag, properties, -1);
        List<Queryable> results = new ArrayList<>();
        for(Queryable queryable:allResults){
            if(!siblingIndexes.containsValue(((ElementQueryable) queryable).getElement().getRuntimeId().toString())){
                results.add(queryable);
            }
        }
        if(indexToReturn!=-1){
            List<Queryable> trimmedResults = new ArrayList<>();
            results.add(results.get(indexToReturn));
            return trimmedResults;
        }
        return results;
    }

    @Override
    public List<Queryable> findAllPreceding(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllPrecedingSibling(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllFollowingSiblingAll(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllFollowingAll(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllPrecedingAll(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        return null;
    }

    @Override
    public List<Queryable> findAllPrecedingSiblingAll(String tag, List<HashMap<String, String>> properties, int indexToReturn) {
        String runtimeId = element.getRuntimeId().toString();
        List<WindowsElement> siblings = element.getAllSiblings();
        HashMap<Integer,String> siblingIndexes = new HashMap<>();
        boolean afterElement = true;
        for(WindowsElement sibling:siblings){
            if(element.getRuntimeId().toString().equals(runtimeId))
            afterElement=false;

            if(afterElement)
                siblingIndexes.put(siblingIndexes.size(),sibling.getRuntimeId().toString());

        }
        List<Queryable> allResults = new ElementQueryable(element.getParent(),xpath).findAllChildren(tag, properties, -1);
        List<Queryable> results = new ArrayList<>();
        for(Queryable queryable:allResults){
            if(!siblingIndexes.containsValue(((ElementQueryable) queryable).getElement().getRuntimeId().toString())){
                results.add(queryable);
            }
        }
        if(indexToReturn!=-1){
            List<Queryable> trimmedResults = new ArrayList<>();
            results.add(results.get(indexToReturn));
            return trimmedResults;
        }
        return results;
    }

    private PointerByReference buildCondition(String tag, List<HashMap<String,String>>properties){
        IUIAutomation iuiAutomation = element.getWrappedDriver().getIuiAutomation();
        PointerByReference cond = null;
        for(HashMap<String,String>propertyMap:properties){
            WindowsBy by = new WindowsBy(propertyMap.get("property"),propertyMap.get("value"));
            int flag = 0;
            if(propertyMap.containsKey("operator")) {
                if (propertyMap.get("operator").equals("contains")) {
                    flag = 2;
                }
            }

            if(cond!=null){
                if(propertyMap.get("condition").equals("and")){
                    cond=iuiAutomation.createAndCondition(cond,iuiAutomation.createPropertyConditionEx(by.getAttributeIndex(), by.getAttributeValue().toString(), flag));
                }
                else if(propertyMap.get("condition").equals("or")){
                    cond=iuiAutomation.createOrCondition(cond,iuiAutomation.createPropertyConditionEx(by.getAttributeIndex(), by.getAttributeValue().toString(), flag));
                }
            }
            else{
                cond = iuiAutomation.createPropertyConditionEx(by.getAttributeIndex(), by.getAttributeValue().toString(), flag);
            }
        }
        if(!tag.equals("*")){
            if(cond==null){
                cond = iuiAutomation.createPropertyCondition(30004, tag);
            }
            else {
                cond = iuiAutomation.createAndCondition(cond, iuiAutomation.createPropertyCondition(30004, tag));
            }
        }
        return cond;
    }
}
