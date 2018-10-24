# api-probe-template

Template for API probe coding test.

## Prerequisites
* Java 8 (or newer) installed
* Maven installed

## How to build and run code
1. Checkout project template:
  `git clone git@bitbucket.org:infrasightlabs/api-probe-template.git`
2. Write code in ApiProbeMain and own classes
3. Compile project:
  `mvn compile`
4. Run the program:
  `mvn exec:java`
5. Goto 2 until program works

## Task
Arbetsförmedligen (AF) has a REST API documented at https://jobtechdev.se/swagger.

There are two Java classes predefined in api-probe-template:

1. County.java - Represents a county (län)
2. Office.java - Represents an office (plats) for Arbetsförmedligen

These classes have several fields. Use the API to retrieve data and create an instance for the county of Skåne filled with data from the API. Complete as many of the fields as possible.

Several API endpoints are required to fill all fields. You may use any open source libraries available and it is recommended that Maven is used for library dependencies.

Example libraries for JSON processing: Jackson, Google Gson or org.JSON.

In the end of the ApiProbeMain main method, print the County object to System.out.