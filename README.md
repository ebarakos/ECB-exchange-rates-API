# Instructions for setting up ECB API #

* Install Maven and run `mvn clean package && mvn spring-boot:run` from the command line

You must now have the API server up and running

* You can view 90-day history of rates by typing http://localhost:8080/currency/{3 digit currency here} e.g http://localhost:8080/currency/CHF

* You can view rates for for a particular date by typing http://localhost:8080/date/{yyyy-mm-dd} e.g http://localhost:8080/date/2016-03-01 for 1st of March 2016

* You can view rates for for a particular date and currency by typing http://localhost:8080/currency/{3 digit currency here}/date/{yyyy-mm-dd} e.g http://localhost:8080/currency/CHF/date/2016-03-01 for CHF on 1st of March 2016




