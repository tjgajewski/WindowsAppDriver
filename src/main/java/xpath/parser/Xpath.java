package xpath.parser;

public class Xpath {

    Queryable queryable;

    public Xpath(Queryable queryable){
        this.queryable=queryable;
    }

    public CommandList compile(String xpath){
        return new CommandList(xpath,queryable);
    }
}
