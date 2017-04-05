# Awesome AI

Example application showing CTA prediction based on historical data using play 2.5 and reactive mongo

## Configure MongoDB

Just change it in application.conf
```
mongodb.uri = "mongodb://localhost/ngconf"
```

## Run Application
```
sbt run
```
## Endpoints

| Type        | Endpoint           | Payload  | Description  |
| ------------- |-------------| -----| -----|
|GET|/events|{ "hitType": "pageview", "page": "/", "sessionId": "3616391b-63cd-4a8b-83c1-c3cca33d7757", "ip": "7.9.12.2"}|Get recent 30 events list|
|POST|/events|--|Send an event and get the prediction|
|GET|/ip/:ip|127.0.0.1|Get IP details of IP passed|
|GET|/generate|--|Generate dummy data for testing|


## Notes
- Prediction algorithm is generated using [Apache Spark](http://spark.apache.org/)
- Apache Spark code for Machine Learning is in `apache-spark.scala`
- `apache-spark.scala` should be run in Apache Spark Shell
