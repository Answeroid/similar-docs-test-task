# similar-docs-test-task

Write Scala program/test that from 10 documents will find 3 with highest similiarity. Program/test should be
executed via SBT task.
Criterias for similiarity should be defined by yourself, but those should be well documented. Documents also
should be prepared by yourself.

## HOWTO
To use it please do the following:
 - install Scala and SBT (please note that for each OS installation instructions will be different)
 - git clone it to proper folder (git clone https://github.com/Answeroid/similar-docs-test-task.git)
 - cd to cloned repo
 - start demo run using "sbt run" command

If you want to try it with your personal files - place all of them into "testfiles" directlory and start script again.

## Description
Test program compares 2 docs using Levenstein alg https://en.wikipedia.org/wiki/Levenshtein_distance
Implementation was used from Java nad can be rewritten to Scala if needed.

Long story short - Levenshtein distance between two Strings is the minimum number of single-character edits (insertions, deletions or substitutions) required to change one word into the other.

## TODO
- delete all punctuation marks inside of the docs before comparing them (DONE)
- compared docs moved to lowercase before comparing them (DONE)
- add exception handling if file/folder not found (DONE)
- loop to compare all docs but not hardcoded 2 docs as it done now (DONE)
- add break to avoid same doc comparison (DONE)
- print file names based on closest files (DONE)
- an ability compare 2 docs using simplest search to have number of 1st doc word occurrences in 2nd doc

## OUTPUT EXAMPLE
```
-----Closest 3 files-----
Key = 48
Value = List(baa baa black sheepyes sir yes sirthree bags fullone for the masterwho lives down the lanebaa baa black sheephave you any woolthree bags full, baa baa black sheephave you any wooland one for the little boywho lives down the lanebaa baa black sheephave you any woolyes sir yes sirthree bags full)
Key = 64
Value = List(baa baa black sheephave you any wooland one for the little boywho lives down the lanebaa baa black sheephave you any woolyes sir yes sirthree bags full, baa baa black sheephave you any woolyes sir yes sirthree bags fullone for the masterone for the dameand one for the little boywho lives down the lanebaa baa black sheephave you any woolyes sir yes sirthree bags full)
Key = 66
Value = List(baa baa black sheephave you any woolyes sir yes sirthree bags fullone for the masterone for the dameand one for the little boywho lives down the lane, baa baa black sheephave you any woolyes sir yes sirthree bags fullone for the masterone for the dameand one for the little boywho lives down the lanebaa baa black sheephave you any woolyes sir yes sirthree bags full)
Those files are close to each other:
...similar-docs-test-task/testfiles/test5
...similar-docs-test-task/testfiles/test4
Distance: 48
Those files are close to each other:
...similar-docs-test-task/testfiles/test4
...similar-docs-test-task/testfiles/test1
Distance: 64
Those files are close to each other:
...similar-docs-test-task/testfiles/test2
...similar-docs-test-task/testfiles/test1
Distance: 66
```