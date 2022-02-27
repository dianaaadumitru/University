cmake_minimum_required(VERSION 3.17)
project(t2_913_Dumitru_Diana_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(t2_913_Dumitru_Diana_1
        domain.cpp
        domain.h
        Person.cpp
        Person.h
        Measurement.cpp
        Measurement.h
        BMI.cpp
        BMI.h
        BP.cpp
        BP.h
        UI.cpp
        UI.h
        main.cpp
        validator.h
        validator.cpp)
