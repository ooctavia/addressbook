package com.octavianusosbert.addressbook;

import java.io.IOException;
import java.util.ArrayList;
import static spark.Spark.*;
import com.google.gson.Gson;

public class SparkConnect {
    public static void main(String[] args) throws IOException {
        // port(9000);
        // Continuously getting Internal Server Error 500, need to check.
        // Often times the server isn't responding, need to check.
        // There are other errors when trying to create the index, need to check.
        // Need to do more research on ElasticSearch

        ElasticSearchConnect elasticSearch = new ElasticSearchConnect();

        get("/contact", (request, response)->{
            ArrayList<Contacts> contactList = new ArrayList<Contacts>();
            if (request.queryParams() != null) {
                int pageSize = 25;
                int page = 0;
                String query = "*";
                if (request.queryParamsValues("pageSize") != null && request.queryParamsValues("pageSize").length > 0) {
                    pageSize = Integer.parseInt(request.queryParamsValues("pageSize")[0]);
                    page = Integer.parseInt(request.queryParamsValues("page")[0]);
                    query = request.queryParamsValues("query")[0];
                }
                //contactList = elasticSearch.getContacts(pageSize, page, query);
            }
            return new Gson().toJson(contactList);
        });

        put("/contact/:name", (request, response) -> {
            response.type("application/json");
            String name = request.params(":name");
            Contacts contact = new Gson().fromJson(request.body(), Contacts.class);
            return new Gson().toJson("Contact has been updated!");
        });

        post("/contact", (request, response) -> {
            response.type("application/json");
            Contacts contact = new Gson().fromJson(request.body(), Contacts.class);
            return new Gson().toJson("Contact has been created!");
        });

        delete("/contact/:name", (request, response) -> {
            response.type("application/json");
            String name = request.params(":name");
            boolean result = elasticSearch.deleteContact(name);
            if (!result) {
                response.status(404);
                return new Gson().toJson("There's no contact with that name!");
            }
            else {
                return new Gson().toJson("Contact has been deleted!");
            }
        });
    }
}
