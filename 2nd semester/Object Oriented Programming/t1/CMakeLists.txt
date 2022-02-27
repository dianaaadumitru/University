cmake_minimum_required(VERSION 3.17)
project(t1_913_Dumitru_Diana_1)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(t1_913_Dumitru_Diana_1
        gene.cpp
        gene.h
        repo.cpp
        repo.h
        service.cpp
        service.h
        ui.cpp
        ui.h
        main.cpp)
