cmake_minimum_required(VERSION 3.17)
project(a45_913_Dumitru_Diana)

set(CMAKE_CXX_STANDARD 14)

add_executable(a45_913_Dumitru_Diana
        domain/domain.h
        domain/domain.cpp
        dynamic_vector/array.h
        dynamic_vector/array.cpp
        repo/repo.h
        repo/repo.cpp
        service/service.h
        service/service.cpp
        main.cpp
        tests/tests.cpp
        tests/tests.h
        iterator/iterator.cpp
        iterator/iterator.h
        ui/UI.cpp
        ui/UI.h)

