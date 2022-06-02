package ru.startandroid.importantdates.core.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private final int id;
    private final String name;

    /**
     * Construct object that shows which category the Event belong to
     *
     * @param id   category ID
     * @param name category name
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(Parcel source) {
        id = source.readInt();
        name = source.readString();
    }

    /**
     * Get event category ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * Get name of the event category
     */
    public String getName() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }

        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }
    };
}
