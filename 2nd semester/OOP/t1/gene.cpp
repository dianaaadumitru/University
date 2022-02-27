
#include "gene.h"

Gene::Gene() : organism(""), name(""), sequence("") {}

Gene::Gene(const std::string &organism, const std::string& name, const std::string& sequence) {
    this->organism = organism;
    this->name = name;
    this->sequence = sequence;
}

std::string Gene::get_name() const {
    return this->name;
}

std::string Gene::get_organism() const {
    return this->organism;
}

std::string Gene::get_sequence() const {
    return this->sequence;
}