# Energymart

Reference application for my Pluralsight course: [Java Performance Tuning](http://www.pluralsight.com/courses/java-performance-tuning)

The energymart application is a spring-boot based web service for comparing energy prices from different suppliers, and getting a recommendation on which supplier to choose. The service has an endpoint for uploading a meter reading and an endpoint for getting back a list of all or some of the electricity readings that have been uploaded. It also has the recommendation endpoint which calculates the cost of electricity for each supplier's plan for the past 7 days and returns a list of recommendations based on the cheapest plan.

Included in this project are a number of benchmark tests also. These benchmarks, check the performance of optimizations to the use of ArrayLists and HashMaps.

## Git setup
There are 2 branches in this repo: master and develop

Master contains the un-optimized version of the application (a before snapshot), while develop contains the optimized version (after snapshot)
