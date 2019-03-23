**JSON SUMMARY**

This application is developed as a part of programming assignment.

The main purpose of the application is to stream and parse chunks of JSON file in batch of 1000.
It also processes the read objects and calculates statistical summaries.

The application has been designed in a modular way so as to be extensible in future with newer implementations of Queries.

The application reads big file using GSON stream apis.

It utilizes Chain of responsibility design pattern to handle the read objects.

The Objects are then batch processed when either the batch size reaches 1000 or the file stream is closed.

The Application is designed to work on User objects that are read from the file.

A sample user Json looks like:

`{
     "guid": "893663ef-1911-4318-a477-626774fc05b6",
     "isActive": true,
     "balance": "$4,611.22",
     "age": 44,
     "eyeColor": "blue",
     "name": "Esperanza Wilkerson",
     "gender": "female",
     "email": "esperanzawilkerson@avit.com",
     "phone": "+1 (920) 582-3308",
     "address": "217 Mill Lane, Fivepointville, North Carolina, 8708",
     "registered": "2017-11-14T04:11:28 +08:00",
     "friends": [
       {
         "name": "Chan Patel"
       }
     ],
     "greeting": "Hello, Esperanza Wilkerson! You have 82 unread messages.",
     "favoriteFruit": "blueberry"
   }`  
   
The application has various queries that are ran over the batch to calculate results.
the queries also output the final results when all the objects are read and the file is closed.


**Compile and run instructions:**

compile using `mvn clean install`

execute using `java -jar <PATH\TO\json_summary-runme.jar> <COMPLETE\PATH\TO\JSON\FILE>`