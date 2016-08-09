# _Band Tracker_

#### _Java application project built as a code review of competencies including working with PostgreSQL, Sql2o, and Spark to build many-to-many relationships, using a join table, and CRUD & RESTful Routing to Update/Delete for many-to-many., 05/13/2016_

#### By _**LaTaevia**_

## Description

_Java application webpage assigned as a project for my ninth week at Epicodus. It functions as an app for a band tracker. Users will be able to add a list of bands, and for each band, add venues where they've played concerts._

## Prerequisites

You will need the following properly installed on your computer.

* [Gradle](https://gradle.org/gradle-download/)
* PostgreSQL on Mac with HomeBrew `brew install postgres` 
* [PostgreSQL (All OS)] (http://www.enterprisedb.com/products-services-training/pgdownload#windows)

## Installation

* `git clone https://github.com/LATAEVIA/Band-Tracker.git`
* Change into the new directory
* In a new terminal window/tab `postgres` to launch postgres
* In another new terminal window/tab `psql` to launch psql
* In psql window/tab `CREATE DATABASE band_tracker;`
* In the terminal window/tab `psql band_tracker < band_tracker.sql`
* In psql window/tab `CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;`
* `gradle build`

## Running / Development

* `gradle run`
* Visit app at [http://localhost:4567](http://localhost:4567).

## Technologies Used

* _HTML_
* _CSS_
* _Bootstrap_
* _Java_
* _Gradle_
* _Spark_
* _Velocity_
* _FluentLenium_
* _PostgreSQL_

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 **_LaTaevia_**
