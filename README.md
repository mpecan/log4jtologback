Contains java code to allow you to convert from log4j.xml files to logback.xml files.

The log4j-to-logback.xsl file was retrieved from https://github.com/rpuch/log4j2logback for which I have to thank the original author Roman Puchkovskiy.

This simplifies the retrieval of libraries and allows you to use maven for retrieving all dependencies.

The usage is simple:

* Build the project by running `mvn package`
* Run the jar and give it parameters:
```bash
java -jar target/log4j-to-logback-1.0-SNAPSHOT-jar-with-dependencies.jar ${path_to_original} ${path_to_output}
```

* path_to_original - path to the original log4j.xml file
* path_to_output - path to the output logback.xml file, if it is not provided the output will be printed to stdout