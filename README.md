# coding-assignment-1
SpringBoot application for solving Airport Baggage system using Graph

Takes in an input file as a parameter to the jar and prints the Baggage route in the console and writes it to an output file (BaggageRouteOutput.txt)

Format of the Input file:
``shell
# Section: Conveyor System
Node 1> <Node 2> <travel_time>
# Section: Departures
<flight_id> <flight_gate> <destination> <flight_time>
# Section: Bags
<Bag_Number> <point_1> <point_2> [<point_3>, …] : <total_travel_time>

**Build**

The project is a Gradle project. To build, open up your Terminal and fire up the following commands:
```shell
$ cd coding-assignment-1
$ gradle build

This would build the application and run JUnit tests. 

To create JAR

$gradle createJar

Run the application

$ java -jar coding-assignment-<gradle_build_version>.jar <input_file_path>
