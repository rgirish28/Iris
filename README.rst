Iris
====

Authentication system using iris recognition and visual crytography. The Iris recognition system consists of a segmentation system based on the Hough transform. It is able to localise iris and pupil region, excluding eyelids, eyelashes and refletions.

Iris region is then normalised and filtered by 1D Log-Gabor. Phase data is extracted and quantised to four levels creating an unique pattern of the iris.

The Minkowski distance is used for classification and comparison of patterns. The system is parallelised for efficient detection. 

Using Naor and Shamir's 2,2 visual cryptography method, we then encode a code within an image and split it into 2 shares. One is given to the user which is combined with the one remaining in the system to authenticate the user. 

Share 1

.. image:: https://raw.github.com/rgirish28/Iris/master/shares/488877share1.png

Share 2

.. image:: https://raw.github.com/rgirish28/Iris/master/shares/488877share2.png

Combined

.. image:: https://raw.github.com/rgirish28/Iris/master/combined/488877.png



:Authors:
    Girish Ramesh
  ( University of Manchester )
