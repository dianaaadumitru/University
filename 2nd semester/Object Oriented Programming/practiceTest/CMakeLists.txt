cmake_minimum_required(VERSION 3.17)
project(practiceTest)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(QT_VERSION 5)
set (CMAKE_PREFIX_PATH C:\\Qt\\5.15.2\\mingw81_32\\lib\\cmake)
find_package(Qt5Widgets REQUIRED)

add_executable(practiceTest main.cpp
        Car.cpp
        Car.h
        Repo.h
        Repo.cpp
        Service.cpp
        Service.h
        gui.cpp
        gui.h)

target_link_libraries(practiceTest Qt5::Widgets)