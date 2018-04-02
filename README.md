# FuzzP
[![Build Status](https://travis-ci.org/AttilaOrs/FuzzP.svg?branch=master)](https://travis-ci.org/AttilaOrs/FuzzP)

FuzzP is Fuzzy Petri-net and a Unified Enhanced Time Petri Neti visualizer and executor for teaching and academic purposes. The user has to define the structure of the Petri-net, and the rule-tables inculded in the transition. Based on these FuzzyP is capable to  execute the Fuzzy Petri net, and to vizualize it's behaviour.
For visualization  uses [JGraphX](https://github.com/jgraph/jgraphx) and [Gral](https://github.com/eseifert/gral) libraries. 

# Components
* FuzzPCore contains the executors and the realted algorithms.
* FuzzPVizual contains a presentation UI which is able to draw the structure of the Petri net, and the evolution of places.
* FuzzPExt contains a small language to define Petri nets. It is inspired by Graphviz's dot and OOP princeples. FuzzPExt also has a code generator which output is compatibe with FuzzPCore's API. The UETPN Lisp (used for Genetic programming)  also has a textual implenetation here. For parsing [ANTLR](http://www.antlr.org/) is used, the code generation is done with [StringTemplate](http://www.stringtemplate.org/).
* FuzzPExamples  contains some examples.
* FuzzPGenetic is a framework used for UETPN model generation, and retraining of the UETPN models. I also contains multiple examples such as the artifical ant, line follower robot etc.

# Requirements
Java 1.8 (or higher) for FuzzPCore [Gson](https://github.com/google/gson) is required (for serialziation). For FuzzPVizual the two libraries mentioned above. Running the tests requires the usual: [JUnit](http://junit.org/junit4/)

The FuzzPExamples should run with the newest build from fatJar. (The jars contain all the dependeces)
