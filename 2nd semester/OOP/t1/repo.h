#pragma once
#include "gene.h"
#include <vector>

class Repo
{
private:
    std::vector<Gene> genes;

public:
    Gene search_for_gene(const std::string& organism, const std::string& name);

    bool add_gene(const Gene & gene);

    std::vector<Gene> get_all_genes();
};