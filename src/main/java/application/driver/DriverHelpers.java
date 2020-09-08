package application.driver;

import application.element.factory.ElementHelpers;
import infrastructure.automationapi.IUIAutomation;
import infrastructure.automationapi.IUIAutomationElement;
import infrastructure.automationapi.IUIAutomationTreeWalker;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class DriverHelpers{

    public static List<By> splitCssSelectorBy(By by){
        String original = by.toString().split(":")[1];
        original = original.replaceAll(" ", "");
        List<By>byList = new ArrayList<>();
        String[] stringArray = original.split("(?=\\.)|(?=#)|(?=@)|(?=\\$)");
        By b1 =  By.tagName(stringArray[0]);
        byList.add(b1);
        for(int i = 1; i < stringArray.length; i++){
            String value = stringArray[i];
            By tempBy = cssParser(value);
            byList.add(tempBy);}
        return byList;
    }

    public static By cssParser(String value){
        By tempBy = null;
        if(value.contains(".")){
            tempBy = By.name(value.replaceAll("\\.", ""));}
        if(value.contains("#")){
            tempBy = By.id(value.replaceAll("#", ""));}
        if(value.contains("@")){
            tempBy = By.className(value.replaceAll("@", ""));}
        if(value.contains("$")){
            tempBy = By.linkText(value.replaceAll("\\$", ""));}
        return tempBy;
    }

    public static IUIAutomationElement returnXpathElement(By by, IUIAutomation iuiAutomation, IUIAutomationElement frameElement, String dynamicElementId){
        IUIAutomationTreeWalker treeWalker = iuiAutomation.getTreeWalker();
        String original = by.toString().split(":")[1];
        original = original.replaceAll(" ", "");
        String[] xpathCommandList = original.split("\\/");
        String firstElementString =  xpathCommandList[0];
        By tempBy = xpathParser(firstElementString);
        IUIAutomationElement firstElement =  ElementHelpers.getIUIAutomationElement(tempBy, iuiAutomation, frameElement, dynamicElementId);
        IUIAutomationElement currentElement = firstElement;
        for(int i = 1; i < xpathCommandList.length; i ++){
            String currentCommand = xpathCommandList[i];
            if(currentCommand.equalsIgnoreCase(">")){
                currentElement = treeWalker.getNextSiblingElement(currentElement);}
            if(currentCommand.equalsIgnoreCase("<")){
                currentElement = treeWalker.getPreviousSiblingElement(currentElement);}
            if(currentCommand.equalsIgnoreCase("^")){
                currentElement = treeWalker.getParentElement(currentElement);}
            if(currentCommand.equalsIgnoreCase("*")){
                currentElement = treeWalker.getFirstChildElement(currentElement);}
            if(currentCommand.equalsIgnoreCase("**")){
                currentElement = treeWalker.getLastChildElement(currentElement);} }
        return currentElement;
    }

    public static By xpathParser(String value){
        By tempBy = null;
        String[]valueSplit1 = value.split("\\/");
        String[]valueSplit2 = valueSplit1[0].split("=");
        String attribute = valueSplit2[0];
        String attributeValue = valueSplit2[1].replaceAll("[^a-zA-Z0-9]+","");
        attribute = attribute.replaceAll("[^a-zA-Z0-9]+", "");
        if(attribute.equalsIgnoreCase("id")) {
            tempBy = By.id(attributeValue);
        }
        if(attribute.equalsIgnoreCase("text")||attribute.equalsIgnoreCase("value")){
            tempBy = By.linkText(attributeValue);
        }
        if(attribute.equalsIgnoreCase("name")){
            tempBy = By.name(attributeValue);
        }
        if(attribute.equalsIgnoreCase("class")){
            tempBy = By.className(attributeValue);
        }
        if(attribute.equalsIgnoreCase("tag")||attribute.equalsIgnoreCase("localcontroltype")){
            tempBy = By.tagName(attributeValue);
        }
        return tempBy;
    }

}

