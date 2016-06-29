package com.couchbase.client.sample;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;

/**
 * Created by ingenthr on 6/27/16.
 */
public class Airline {

    @Field
    String name;

    @Id
    String docid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return docid;
    }

    public void setId(String id) {
        this.docid = id;
    }

    @Override
    public String toString() {
        return "Airline{" +
                "name='" + name + '\'' +
                ", id='" + docid + '\'' +
                '}';
    }
}
