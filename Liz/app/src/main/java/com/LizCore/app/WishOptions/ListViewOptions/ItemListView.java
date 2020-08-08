package com.LizCore.app.WishOptions.ListViewOptions;

public class ItemListView {
    private String Id, Name, Value, Comment;
    private int Image;

    public ItemListView(int id, String name, String value, int image, String comment) {
        this.Id = String.valueOf(id);
        this.Name = name;
        this.Value = value;
        this.Image = image;
        this.Comment = comment;
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

    // Image
    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    //Comment
    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }
}
