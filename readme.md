WARGAMES
------------------------------

Wargames is a simulation program, where one can create armies, add different types
of units to the armies, and then simulate a battle between two chosen armies. Health values of all
units, and when units are defeated, will all be displayed during the simulation of the battle.

#### How to run the program and create a JAR-file
The Wargames project uses maven as build tool. To run the program, clone or zip this project to your
local computer. Make sure maven and java are installed. If you are using IntelliJ, go to the Maven tab,
under plugins choose javafx and then press javafx:run. The application should now start.
Using a terminal, make sure you are in the root directory and type:
```bash
    mvn javafx:run
```
To create a jar-file using the terminal use the following command:
```bash
    mvn clean package
```
If you are using IntelliJ, go to the maven tab. Under lifecycle first press clean, once the project
is cleaned, press the package button.

The Jar-file can be found in the target directory that is made in the root folder of the project.

#### Author
Andreas Follevaag Malde