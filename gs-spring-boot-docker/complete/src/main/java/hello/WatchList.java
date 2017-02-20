package hello;

import org.springframework.data.annotation.Id;


public class WatchList {

    @Id
    public String id;

    public String listName;
    public String listType;
    public String listOwner;

    public WatchList() {}

    public WatchList(String listName, String listType, String listOwner) {
        this.listName = listName;
        this.listType = listType;
        this.listOwner = listOwner;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, listName='%s', listType='%s']",
                id, listName, listType);
    }

}