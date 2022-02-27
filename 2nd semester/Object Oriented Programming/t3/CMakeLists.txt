cmake_minimum_required(VERSION 3.17)
project(t3_913_Dumitru_Diana)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
set(CMAKE_AUTOUIC ON)

set(QT_VERSION 5)
set (CMAKE_PREFIX_PATH C:\\Qt\\5.15.2\\mingw81_32\\lib\\cmake)
find_package(Qt5Widgets REQUIRED)

add_executable(t3_913_Dumitru_Diana
        main.cpp
        Repo.cpp
        Repo.h
        Service.cpp
        Service.h
        Weather.cpp
        Weather.h
        gui.h
        gui.cpp)

target_link_libraries(t3_913_Dumitru_Diana Qt5::Widgets)
