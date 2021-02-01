# reportGeneration

This API Generates two types of Reports:

- Total liability by currency
- Selection liability by currency

There are two types of requests:
- Uploading a Csv file
- Sending a Json format (Accepting a list of json)

Generate Liability Report.

http://localhost:8080/upload/liability

http://localhost:8080/json/liability

Generate Selection Report

http://localhost:8080/upload/selection

http://localhost:8080/json/selection


The output of both requests file and json,
will generate a CSV text format string.

It can be tested using Postman.
