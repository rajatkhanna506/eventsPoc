package com.example.eventspoc.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Links___ {
    @SerializedName("first")
    @Expose
    private First first;
    @SerializedName("prev")
    @Expose
    private Prev prev;
    @SerializedName("self")
    @Expose
    private Self___ self;
    @SerializedName("next")
    @Expose
    private Next next;
    @SerializedName("last")
    @Expose
    private Last last;

    public First getFirst() {
        return first;
    }

    public void setFirst(First first) {
        this.first = first;
    }

    public Prev getPrev() {
        return prev;
    }

    public void setPrev(Prev prev) {
        this.prev = prev;
    }

    public Self___ getSelf() {
        return self;
    }

    public void setSelf(Self___ self) {
        this.self = self;
    }

    public Next getNext() {
        return next;
    }

    public void setNext(Next next) {
        this.next = next;
    }

    public Last getLast() {
        return last;
    }

    public void setLast(Last last) {
        this.last = last;
    }

}
