
package com.example.eventspoc.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Sales {

    @SerializedName("public")
    @Expose
    private Public _public;

    public Public getPublic() {
        return _public;
    }

    public void setPublic(Public _public) {
        this._public = _public;
    }
}
