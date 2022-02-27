cmake_minimum_required(VERSION 3.17)
project(a23_913_Dumitru_Diana C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(a23_913_Dumitru_Diana
        holiday.h
        holiday.c
        array.h
        array.c
        repo.c
        repo.h
        service.h
        service.c
        UI.h
        UI.c
        main.c
        tests.c
        tests.h)
