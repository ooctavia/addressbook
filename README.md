# addressbook
Implementation of an address book api using ElasticSearch and Spark
Below is the specification:

GET /contact?pageSize={}&page={}&query={}

This endpoint will providing a listing of all contacts, you will need to allow for a defined pageSize (number of results allowed back), and the ability to offset by page number to get multiple pages. Query also should be a query for queryStringQuery as defined by Elasticsearch that you can pass directly in the Elasticsearch call.

POST /contact

This endpoint should create the contact.  Given that name should be unique, this may need to be enforced manually.  

GET /contact/{name}

This endpoint should return the contact by a unique name. This name should be specified by the person entering the data.  

PUT /contact/{name}

This endpoint should update the contact by a unique name (and should error if not found)

DELETE /contact/{name}

This endpoint should delete the contact by a unique name (and should error if not found)
