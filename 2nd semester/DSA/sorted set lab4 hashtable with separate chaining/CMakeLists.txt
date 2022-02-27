cmake_minimum_required(VERSION 3.17)
project(sorted_set_lab4_hashtable_with_separate_chaining)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(sorted_set_lab4_hashtable_with_separate_chaining
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedSet.cpp
        SortedSet.h
        SortedSetIterator.cpp
        SortedSetIterator.h)
