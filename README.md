# _Spark-Gradle-Template_
A barebones project with scala, apache spark built using gradle. Spark-shell provides `spark` and `sc` variables pre-initialised, here I did the same using a *scala trait* that you can extend.

## Prerequisites
- [Java](https://java.com/en/download/)
- [Gradle](https://gradle.org/)
- [Scala](https://www.scala-lang.org/)

## Build and Demo process

### Clone the Repo
`git clone https://github.com/knoma/spark-template`

### Build
`./gradlew clean build`
### Run
`./gradlew run`
### All Together
`./gradlew clean run`


## What the demo does?
Take a look at *src->main->scala->template->spark* directory

We have two Items here. 


The file `Main.scala` has the executable class `Main`. 
In this class, I do 4 things

- Print spark version.
- Find sum from 1 to 100 (inclusive).
- Read a csv file into a structured `DataSet`. 
- Find average age of persons from the csv.



## Libraries Included
- Spark - 2.1.1

## Useful Links
- [Spark Docs - Root Page](http://spark.apache.org/docs/latest/)
- [Spark Programming Guide](http://spark.apache.org/docs/latest/programming-guide.html)
- [Spark Latest API docs](http://spark.apache.org/docs/latest/api/)
- [Scala API Docs](http://www.scala-lang.org/api/2.12.1/scala/)
 