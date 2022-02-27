
#pragma once
#include <iostream>

class Gene
{
private:
    std::string organism;
    std::string name;
    std::string sequence;

public:
    // default constructor for a gene
    Gene();
    // constructor with parameters
    Gene(const std::string& organism, const std::string& name, const std::string& sequence);

    std::string get_organism() const;
    std::string get_name() const;
    std::string get_sequence() const;

};