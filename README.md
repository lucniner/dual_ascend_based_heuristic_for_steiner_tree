# shortest path heuristic and dual ascend

This repository is for the second programming exercise in Networks Design and Analysis summer term 2018 at the technical university of vienna.


## Build

To build the code and run with default values simply execute the following:

```shell
./gradlew clean jar
cd build/libs/
java -jar dual_ascend_based_heuristic_for_steiner_tree.jar
```

The following options are available and can be displayed when adding the -h flag to the jar execution. 

```shell
usage: java -jar <name> [-a] [-d <arg>] [-f <arg>] [-h] [-v]
 -a,--dualAscent        use dual ascent before shortest path heuristic
 -d,--directory <arg>   load all instances from given directory
 -f,--file <arg>        load instance from given file
 -h,--help              print this message
 -v,--visualize         visualization of the output
```

## Implementation

The implementation assumes that the input graph follows the [steinlib format](http://steinlib.zib.de/format.php). The documentation for Edges and Arcs is not accurate on the website so we decided to use for Edges the following meaning: First and Second number are nodes, third number is the weight. For Arcs it is the same we simply use the last number as weight.

## Test Instance
The implementation got tested with instances from [steinlib](http://steinlib.zib.de/download.php).

## Visualization

For the shortest path there is a visualization for steiner trees available. It simply prints the input graph and marks the path taken, as well as terminals. Visualization is done with the GraphStream API.

## LICENCE - MIT

Copyright 2018, Patrick Weißkirchner | Lukas Kathrein

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.