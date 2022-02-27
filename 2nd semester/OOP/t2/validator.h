#pragma once
#include <exception>
#include <string>


class ValidationException : public std::exception {
private:
    std::string message;

public:
    ValidationException(std::string message) : message(message) {}

    const char* which_message()
    {
        return message.c_str();
    }
};