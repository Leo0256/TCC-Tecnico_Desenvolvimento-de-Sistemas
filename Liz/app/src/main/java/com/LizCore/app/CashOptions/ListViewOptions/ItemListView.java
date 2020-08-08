package com.LizCore.app.CashOptions.ListViewOptions;

public class ItemListView {
    private String Id, Name, Value;

    public ItemListView(String id, String name, String content) {
        this.Id = String.valueOf(id);
        this.Name = name;
        this.Value = content;
    }

    //Getter's and Setter's

    // Id
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    // Name
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    // Value
    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }


}
