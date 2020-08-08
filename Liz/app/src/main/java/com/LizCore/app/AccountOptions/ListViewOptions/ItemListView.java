package com.LizCore.app.AccountOptions.ListViewOptions;

public class ItemListView {
    private String Id, Name, Value, Date;

    public ItemListView(int id, String name, String value, String date) {
        this.Id = String.valueOf(id);
        this.Name = name;
        this.Value = value;
        this.Date = date;
    }

    //Getter's and Setter's

    // Id
    public String getId() {
        return Id;
    }

    public void setId(int id) {
        Id = String.valueOf(id);
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

    //Date
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}
