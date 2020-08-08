package com.LizCore.app.AnnotationOptions.ListViewOptions;

public class ItemListView {
    private String Id, Name, Content, Date;

    public ItemListView(int id, String name, String content, String date) {
        this.Id = String.valueOf(id);
        this.Name = name;
        this.Content = content;
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
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    //Date
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

}
