# Pub/Sub to BigQuery Ingestion Tutorial

This project is part of a tutorial on how to persist Pub/Sub messages to BigQuery
table using Pub/Sub subscriptions without any deployment.

For more information, please refer to this Medium article of 
[Server-less Pub/Sub to BigQuery ingestion](https://medium.com/@iraj.hedayati/server-less-pub-sub-to-bigquery-ingestion-3088dd75feb9).

Also, you can watch this online on [YouTube](https://www.youtube.com/watch?v=Y7hCeXTQWwI)

## Preparation

we need to ensure that Pub/Sub service account has the BigQuery Data Editor role. 
The service account is `service-<PROJECT ID>@gcp-sa-pubsub.iam.gserviceaccount.com`.

## Text Messages

1. Create a topic
2. Create BQ table
   ```sql
   CREATE TABLE `dataedu-web.demo.json_events` (
        data STRING
   );
   ```
3. Create a subscription
4. Publish to the topic
5. Check the table
   ```sql
   SELECT * FROM `dataedu.demo.json_events`
   ```

# Proto
1. Create schema
    ```
    syntax = "proto2";
    message Event {
      required string id = 1;
      required string url = 2;
      required int64 user_id = 3;
      required int64 timestamp = 4;
    }
    ```
2. Create a topic with this schema
3. Create BQ table
    ```sql
    CREATE TABLE `dataedu.demo.pubsub_events` 
    ( 
       id STRING NOT NULL, 
       url STRING NOT NULL, 
       user_id INT64 NOT NULL, 
       timestamp INT64 NOT NULL
    );
    ```
4. Create a subscription
5. Publish to the topic
6. Check the table
    ```sql
    SELECT * FROM `dataedu.demo.pubsub_events`
    ```

# Schema Evolution
1. Create another schema with an optional field added
    ```
    syntax = "proto2";
    message EventV2 {
      required string id = 1;
      required string url = 2;
      required int64 user_id = 3;
      required int64 timestamp = 4;
      optional string new_field = 5;
    }
    ```
2. Create a new topic with this new schema
3. Alter the BQ table
    ```sql
    ALTER TABLE `project-rnd.ds.events` ADD COLUMN new_field STRING;
    ```
4. Create a subscription
5. Publish to the topic
6. Check the table
    ```sql
    SELECT * FROM `dataedu.demo.pubsub_events`
    ```
