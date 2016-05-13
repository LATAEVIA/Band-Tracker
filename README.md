# _Epicodus JAVA Week 4 Independent Project: Band Tracker_

#### _When run through Gradle, will create an app for a band tracker. Users will be able to add a list of bands, and for each band, add venues where they've played concerts., 05/13/2016_

#### By _**LaTaevia**_

## Description

_This a static webpage with dynamic elements, a snapshot of sorts, assigned as an end-of-week project for my ninth week at Epicodus. It uses HTML, CSS, Bootstrap, Java, Velocity, Spark, FluentLenium, PostgreSQL and Gradle, and functions as an app for a band tracker. Users will be able to add a list of bands, and for each band, add venues where they've played concerts._

## Setup/Installation Requirements

* _Clone through the command line using 'git clone https://github.com/LATAEVIA/JAVA-WK4-Band-Tracker.git'_
* _Open files in any text editor to view source code_

* _In PSQL:_
* _CREATE DATABASE band_tracker;_
* _\c band_tracker;_
* _CREATE TABLE bands (id serial PRIMARY KEY, band_name varchar);_
* _CREATE TABLE venues (id serial PRIMARY KEY, venue_name varchar);_
* _CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int);_
* _CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;_

*_To Demo:_
* _With Gradle installed, navigate to folder in the terminal and 'gradle run'_
* _Open localhost:4567 in any browser to view_

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
