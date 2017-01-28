Steps to run:
1) compile all classes
--> javac *.java

2) start rmiregistry at port 35001
--> rmiregistry 3500 &

3) start HelloServer
--> java HelloServer &

4) run HelloC
--> java HelloC