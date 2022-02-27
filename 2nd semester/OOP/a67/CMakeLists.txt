cmake_minimum_required(VERSION 3.17)
project(a67_913_Dumitru_Diana)

set(CMAKE_CXX_STANDARD 14)

include_directories(domain)
include_directories(dynamic_vector)
include_directories(iterator)
include_directories(repo)
include_directories(service)
include_directories(tests)
include_directories(ui)

add_executable(a67_913_Dumitru_Diana
        domain/domain.cpp
        domain/domain.h
        dynamic_vector/array.cpp
        dynamic_vector/array.h
        iterator/iterator.cpp
        iterator/iterator.h
        repo/repo.cpp
        repo/repo.h
        repo/FileRepo.cpp
        repo/FileRepo.h
        repo/IFileRepo.h
        repo/HTMLRepo.h
        repo/HTMLRepo.cpp
        repo/CSVRepo.cpp
        repo/CSVRepo.h
        service/service.cpp
        service/service.h
        tests/tests.cpp
        tests/tests.h
        ui/UI.cpp
        ui/UI.h
        main.cpp
        validator/validator.cpp
        validator/validator.h
        utils/utils.cpp
        utils/utils.h)
