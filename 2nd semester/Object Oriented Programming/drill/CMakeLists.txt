cmake_minimum_required(VERSION 3.19)
project(drill)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(QT_VERSION 5)
set (CMAKE_PREFIX_PATH C:\\Qt\\5.15.2\\mingw81_32\\lib\\cmake)
find_package(Qt5Widgets REQUIRED)

add_executable(drill
        main.cpp
        Controller.cpp
        Controller.h
        gui.cpp
        gui.h
        Idea.cpp
        Idea.h
        Model.cpp
        Model.h
        Repository.cpp
        Repository.h
        Testing.cpp
        Testing.h
        Utils.h
        Utils.cpp
        Writer.cpp
        Writer.h)

target_link_libraries(drill Qt5::Widgets)