# algorithmX-color-controls

Donald Knuth's Dancing Links(DLX) based implementation of Algorithm X with Color Controls in Scala, first introduced at Knuth's 2018 Christmas Lecture (https://www-cs-faculty.stanford.edu/~knuth/musings.html).

Note that the color control part is a new extension to his original DLX paper (https://arxiv.org/pdf/cs/0011047.pdf).

# Helpful references

AlgorithmX Wikipedia page (https://en.wikipedia.org/wiki/Knuth%27s_Algorithm_X).
2018 Christmas Lecture Youtube link (https://www.youtube.com/watch?time_continue=215&v=t9OcDYfHqOk).

# Color Controls
The tabular definition of an exact cover problem (which Knuth remarks is the most intuitive representation) is that given a matrix of 1s and 0s, to select the set of rows such that every column is covered exactly once by some row's 1 entry.

The color control part adds to this definition

0. The matrix can have arbitrary non negative integer values.
1. Each column is either primary or secondary.
2. Every primary column must be covered exactly once.
3. A secondary column may or may not be covered.
4. Further, a secondary column C may be covered multiple times. However, every row that covers column C must have the same values. 
