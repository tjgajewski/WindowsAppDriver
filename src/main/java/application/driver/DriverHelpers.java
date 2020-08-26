package application.driver;

import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class DriverHelpers {

    public static List<By> splitCssSelectorBy(By by){
        String original = by.toString().split(":")[1];
        original = original.replaceAll(" ", "");
        List<By>byList = new ArrayList<>();
        String[] stringArray = original.split("(?=\\.)|(?=#)|(?=@)|(?=\\$)");
        By b1 =  By.tagName(stringArray[0]);
        byList.add(b1);
        for(int i = 1; i < stringArray.length; i++){
            String value = stringArray[i];
            By tempBy = null;
            if(value.contains(".")){
                tempBy = By.name(value.replaceAll("\\.", ""));
            }
            if(value.contains("#")){
                tempBy = By.id(value.replaceAll("#", ""));
            }
            if(value.contains("@")){
                tempBy = By.className(value.replaceAll("@", ""));
            }
            if(value.contains("$")){
                tempBy = By.linkText(value.replaceAll("\\$", ""));
            }
            byList.add(tempBy);
        }
        return byList;
    }
}

