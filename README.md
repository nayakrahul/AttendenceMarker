# Attendance Marker

Attendance Marker is an android app which is used to keep track of your attendance of various courses. It uses location of your phone to mark your attendance.

## How it works:
* It gives you an user interface to fill out any number of courses and the corresponding schedule of the courses. Most crucial thing is to save the GPS coordinates of the Location where the classes are going to be held.
* Schedule and GPS Coordinate is all that it requires. Now whenever the device extracts the coordinates of your current position, it query the databases to find out that whether you have classes at this time or not. If you have, then it calculate the distance by using your current position and the venue set for the classes held.
* It marks your attendance if it finds distance to be below a threshold. If you are not close to the saved venue in the whole of scheduled time interval,  then it will mark your attendance absent.


## Requirements:
* Android phone with OS android Kitkat or higher. Must have a GPS Sensor.
* App has been tested on Xiaomi Mi Note 4 and ASUS Zenfone 2. It is expected to work very smoothly on phone with similar configuration.

## Installation:
* Apk file has been submitted with the project. It can be directly installed with that.
* Source file has also been submitted. Android Studio would be required to build the project.
