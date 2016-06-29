package com.couchbase.client.sample;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.EntityDocument;
import com.couchbase.client.java.query.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Created by Matt Ingenthron on 6/26/16.
 */
@Named("travelRepo")
@ApplicationScoped
public class TravelInformationRepository {

    CouchbaseCluster cluster;
    Bucket bucket;

    @PostConstruct
    public void buildClient() {
//        cluster = CouchbaseCluster.create(System.getenv("COUCHBASE_URI"));
        cluster = CouchbaseCluster.create();
        bucket = cluster.openBucket("travel-sample");
    }

    @PreDestroy
    public void stop() {
        cluster.disconnect();
    }

    public Airline findAirlineById(String id)  {
        return bucket.repository().get(id, Airline.class).content();
    }

    public Airline findFirstAirlineByName(String name) {
        N1qlQuery simpleQuery = N1qlQuery.simple(("SELECT meta().id AS id from `travel-sample` WHERE type = \"airline\" " +
                        "AND name like \"" + name + "%\" ORDER BY name LIMIT 1"));


        EntityDocument<Airline> theAirline = bucket.async()
                .query(simpleQuery)
                .flatMap(AsyncN1qlQueryResult::rows)
                .map(AsyncN1qlQueryRow::value)
                .flatMap(value -> bucket.repository().async().get(value.getString("id"), Airline.class))
                .toBlocking()
                .single();


        return theAirline.content();
    }

    public List<Airport> findAirports(Airline toFind) {
//        N1qlQuery simpleQuery = N1qlQuery.simple(("SELECT meta().id AS id from `travel-sample` WHERE type = \"airport\" " +
//                "AND name like \"" + name + "%\" ORDER BY name LIMIT 1"));
        N1qlQuery simpleQuery = N1qlQuery.simple("select meta().id as id \n" +
                "from `travel-sample` \n" +
                "where \n" +
                "type = \"airport\" and \n" +
                "\n" +
                "faa within  \n" +
                "\n" +
                "(select route.sourceairport, route.destinationairport\n" +
                "from `travel-sample` route\n" +
                "where route.type = \"route\"  and route.airlineid = \"" + toFind.getId() + "\")");

        return bucket.async()
                .query(simpleQuery)
                .flatMap(AsyncN1qlQueryResult::rows)
                .map(AsyncN1qlQueryRow::value)
                .flatMap(value -> bucket.repository().async().get(value.getString("id"), Airport.class))
                .map(ap -> ap.content())
                .toList()
                .toBlocking()
                .single();

    }


    public List<Airport> findAirportsForAirline(String name) {
        System.err.println("findAirportsForAirline called with: {" + name + "}");
        return findAirports(findFirstAirlineByName(name));
    }

}
