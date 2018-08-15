package com.octavianusosbert.addressbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;

/*
Notes!
- Need to find out how to create the ElasticSearch client
- How to properly set up the client server
 */

public class ElasticSearchConnect {
    // Need to fix getContacts, it should utilize ArrayList.
    // public ArrayList<Contacts>...
    // Return all the contacts available
    public void getContacts(int pageSize, int page, String query) throws IOException {

    }

    public boolean deleteContact(String name) throws IOException {
        RestClient restClient = elasticRestClient();
        indexChecker(restClient);
        Response deleteResponse = restClient.performRequest(
                "POST",
                "/contact/data/_delete_by_query?q=name:"+name+"&filter_path=deleted"
        );
        String jsonString = EntityUtils.toString(deleteResponse.getEntity());
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(jsonString, JsonObject.class).getAsJsonObject();
        int deleteCount = obj.get("deleted").getAsInt();
        restClient.close();
        return true;
    }

    public boolean createContact(Contacts contact) throws IOException {
        RestClient restClient = elasticRestClient();
        indexChecker(restClient);
        Map<String, String> map = Collections.emptyMap();
        HttpEntity postEntity = new NStringEntity(new Gson().toJson(contact, Contacts.class), ContentType.APPLICATION_JSON);
        restClient.performRequest("POST", "/contact/data", map, postEntity);
        restClient.close();
        return true;
    }

    public RestClient elasticRestClient() throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9201, "http")).build();
        return restClient;
    }

    private void indexChecker(RestClient restClient) throws IOException {
        Response testIndexResponse = restClient.performRequest("HEAD", "/contact/");
        if (testIndexResponse.getStatusLine().getStatusCode() == 404) {
            HttpEntity entity = new NStringEntity(
                    "{\n" + "    \"name\" : \"JohnDoe\",\n" + "    \"phone\" : \"9999999999\"\n" +
                            "    \"email\" : \"JohnDoe@yahoo.com\"\n" + "    \"address\" : \"College Park, Maryland\"\n" +
                            "}", ContentType.APPLICATION_JSON);
            restClient.performRequest("PUT","/contact/data/1",Collections.<String, String>emptyMap(),entity);
        }
    }
}
