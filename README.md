# similar-docs-test-task

Write Scala program/test that from 10 documents will find 3 with highest similiarity. Program/test should be
executed via SBT task.
Criterias for similiarity should be defined by yourself, but those should be well documented. Documents also
should be prepared by yourself.

## Description
Test program compares 2 docs using Levenstein alg https://en.wikipedia.org/wiki/Levenshtein_distance
Implementation was used from Java nad can be rewritten to Scala if needed.

Long story short - Levenshtein distance between two Strings is the minimum number of single-character edits (insertions, deletions or substitutions) required to change one word into the other.

## TODO
- delete all punctuation marks inside of the docs before comparing them (DONE)
- compared docs moved to lowercase before comparing them (DONE)
- loop to compare all docs but not hardcoded 2 docs as it done now
- an ability compare 2 docs using simplest search to have number of 1st doc word occurrences in 2nd doc