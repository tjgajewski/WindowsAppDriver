package xpath.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandList {

    List<Command> commandList;
    Queryable queryable;

    public CommandList(String xpath, Queryable queryable){
        this.queryable=queryable;
        commandList = new ArrayList<>();
        boolean hasNextCommand = true;

        while(hasNextCommand) {
            int scope = 0;
            int indexToReturn = -1;
            String tag;
            List<HashMap<String,String>> propertyConditions = new ArrayList<>();

            //Determine what the scope of the search will be
            if(xpath.startsWith(XpathScope.PARENT)){
                xpath = xpath.replace("..","");
                scope = Scope.PARENT;
            }

            else if (xpath.startsWith(XpathScope.ALLNEXT)) {
                xpath = xpath.substring(2);
                if (xpath.startsWith(XpathScope.PARENT)) {
                    xpath = xpath.replace(XpathScope.PARENT, "");
                    scope = Scope.ANCESTOR;
                } else if (xpath.startsWith(XpathScope.PARENT2)) {
                    xpath = xpath.replace(XpathScope.PARENT2, "");
                    scope = Scope.ANCESTOR;
                } else if (xpath.startsWith(XpathScope.ANCESTOR)) {
                    xpath = xpath.replace(XpathScope.ANCESTOR, "");
                    scope = Scope.ANCESTOR;
                } else if (xpath.startsWith(XpathScope.FOLLOWING)) {
                    xpath = xpath.replace(XpathScope.FOLLOWING, "");
                    scope = Scope.FOLLOWING_ALL;
                } else if (xpath.startsWith(XpathScope.FOLLOWINGSIBLING)) {
                    xpath = xpath.replace(XpathScope.FOLLOWINGSIBLING, "");
                    scope = Scope.FOLLOWINGSIBLING_ALL;
                } else if (xpath.startsWith(XpathScope.PRECEDING)) {
                    xpath = xpath.replace(XpathScope.PRECEDING, "");
                    scope = Scope.PRECEDING_ALL;
                } else if (xpath.startsWith(XpathScope.PRECEDINGSIBLING)) {
                    xpath = xpath.replace(XpathScope.PRECEDINGSIBLING, "");
                    scope = Scope.PRECEDINGSIBLING_ALL;
                } else {
                    scope = Scope.DESCENDENT;
                }
            } else if (xpath.startsWith(XpathScope.NEXT)) {
                xpath = xpath.substring(1);
                if (xpath.startsWith(XpathScope.PARENT)) {
                    xpath = xpath.replace(XpathScope.PARENT, "");
                    scope = Scope.PARENT;
                } else if (xpath.startsWith(XpathScope.PARENT2)) {
                    xpath = xpath.replace(XpathScope.PARENT2, "");
                    scope = Scope.PARENT;
                } else if (xpath.startsWith(XpathScope.ANCESTOR)) {
                    xpath = xpath.replace(XpathScope.ANCESTOR, "");
                    scope = Scope.ANCESTOR;
                } else if (xpath.startsWith(XpathScope.FOLLOWING)) {
                    xpath = xpath.replace(XpathScope.FOLLOWING, "");
                    scope = Scope.FOLLOWING;
                } else if (xpath.startsWith(XpathScope.FOLLOWINGSIBLING)) {
                    xpath = xpath.replace(XpathScope.FOLLOWINGSIBLING, "");
                    scope = Scope.FOLLOWINGSIBLING;
                } else if (xpath.startsWith(XpathScope.PRECEDING)) {
                    xpath = xpath.replace(XpathScope.PRECEDING, "");
                    scope = Scope.PRECEDING;
                } else if (xpath.startsWith(XpathScope.PRECEDINGSIBLING)) {
                    xpath = xpath.replace(XpathScope.PRECEDINGSIBLING, "");
                    scope = Scope.PRECEDINGSIBLING;
                } else {
                    scope = Scope.CHILD;
                }
            } else {
                break;
            }

            //Determine what the tag or control type is
            int tagEndIndex = getTagEndIndex(xpath);
            if(tagEndIndex!=-1) {
                tag = xpath.substring(0, tagEndIndex);
                xpath = xpath.substring(tagEndIndex);
            }
            else{
                tag = xpath;
            }


            //Grab the property and values]
            while(true){
                HashMap<String, String> propertyMap = new HashMap<>();
                xpath=xpath.trim();
                if(xpath.startsWith("]")){
                    xpath=xpath.substring(1).trim();
                }
                if(xpath.startsWith("[")){
                    xpath=xpath.substring(1).trim();
                    if(Character.isDigit(xpath.charAt(0))){
                        indexToReturn = Character.getNumericValue(xpath.charAt(0));
                        xpath = xpath.substring(xpath.indexOf("]"));
                        break;
                    }
                    propertyMap.put("condition","and");
                }
                else if(xpath.startsWith("and")){
                    xpath = xpath.substring(3).trim();
                    propertyMap.put("condition","and");
                }
                else if(xpath.startsWith("or")){
                    xpath = xpath.substring(2).trim();
                    propertyMap.put("condition","or");
                }
                else{
                    break;
                }


                String properties = xpath.substring(0,xpath.indexOf("]"));
                String property;
                String value;
                if(properties.startsWith("@")){
                    property = properties.substring(1, properties.indexOf("=")).trim();
                    value = properties.substring(properties.indexOf("'")+1,properties.indexOf("'",properties.indexOf("'")+1));
                    propertyMap.put("property",property);
                    propertyMap.put("value",value);
                }
                else if(properties.startsWith("contains")){
                    properties = properties.replace("contains(","").replace(")","");
                    property = properties.substring(1, properties.indexOf(",")).trim();
                    value = properties.substring(properties.indexOf("'")+1,properties.indexOf("'",properties.indexOf("'")+1));
                    propertyMap.put("property",property);
                    propertyMap.put("value",value);
                    propertyMap.put("operator","contains");
                }
                else if(properties.startsWith("starts-with")){
                    properties = properties.replace("starts-with(","").replace(")","");
                    property = properties.substring(1, properties.indexOf(",")).trim();
                    value = properties.substring(properties.indexOf("'")+1,properties.indexOf("'",properties.indexOf("'")+1));
                    propertyMap.put("property",property);
                    propertyMap.put("value",value);
                    propertyMap.put("operator","starts-with");
                }
                else if(properties.startsWith("ends-with")){
                    properties = properties.replace("ends-with(","").replace(")","");
                    property = properties.substring(1, properties.indexOf(",")).trim();
                    value = properties.substring(properties.indexOf("'")+1,properties.indexOf("'",properties.indexOf("'")+1));
                    propertyMap.put("property",property);
                    propertyMap.put("value",value);
                    propertyMap.put("operator","ends-with");
                }
                propertyConditions.add(propertyMap);
               // System.out.println(xpath.indexOf(("'")+1,properties.indexOf("'")+1));
                xpath = xpath.substring(xpath.indexOf("'",xpath.indexOf("'")+1)+1);
            }
            commandList.add(new Command(scope, tag, propertyConditions, indexToReturn));
            //System.out.println("Xpath after iteration: "+xpath);
        }
    }

    private int getTagEndIndex(String xpath){
        int nextOpenBracket = xpath.indexOf("[");
        int nextCommand = xpath.indexOf("/");
        if(nextCommand<nextOpenBracket&&nextCommand!=-1){
            return nextCommand;
        }
            return nextOpenBracket;
    }

    public List<Queryable> execute(){
            List<Queryable> queryables = new ArrayList<>();
            List<Queryable> results;
            queryables.add(queryable);
            for (int i=0; i <commandList.size(); i++) {
                results = new ArrayList<>();
                for(Queryable queryable:queryables) {
                    results.addAll(commandList.get(i).execute(queryable));
                }
                queryables = results;
            }
        return queryables;
    }

}
