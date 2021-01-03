# CSX42: Assignment 2
## Name: Omkar Udawant

-----------------------------------------------------------------------

Following are the commands and the instructions to run ANT on your project.


Note: build.xml is present in [channelpopularity/src](./channelpopularity/src/) folder.

## Instruction to clean:

```commandline
ant -buildfile channelpopularity/src/build.xml clean
```

Description: It cleans up all the .class files that were generated when you
compiled your code.

## Instructions to compile:

```commandline
ant -buildfile channelpopularity/src/build.xml all
```
The above command compiles your code and generates .class files inside the BUILD folder.

## Instructions to run:

```commandline
ant -buildfile channelpopularity/src/build.xml run -Dinput="input.txt" -Doutput="output.txt" 
```
Note: Arguments accept the absolute path of the files.


## Description:
State Classes: Unpopular, Mildly_Popular, Highly_Popular, Ultra_Popular
These classes implement menthods to add video, remove video and accept of reject ad for a video. 
AbstractState class implements State Interface and Metrics method which updates Views, Likes, Dislikes for a video.
Context class implements SimpleFactory class. Its object is created in drive class and methods are called based on current state.
Results object is created to write the output to a file. 
Exception handling is done to trace appropriate errors. 

Popularity score is updated after new video is added or a video is removed and when metrics method is called. If Popularity score is negative, it is set to 0. 

Created class for custom Exception handling called ContextException.
Comments are added where necessary.
Average Popularity Score is of float type and rounded upto 2 places.  
Data Structure used HashMap and ArrayList


## Academic Honesty statement:

"I have done this assignment completely on my own. I have not copied
it, nor have I given my solution to anyone else. I understand that if
I am involved in plagiarism or cheating an official form will be
submitted to the Academic Honesty Committee of the Watson School to
determine the action that needs to be taken. "

Date: 06/24/2020


