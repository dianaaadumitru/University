#pragma once
#include "repo.h"

class Service
{
private:
    Repo repo;

public:
    Service(const Repo& r): repo{r} {}

    Repo get_repo() const { return repo; }

    bool add_gene_service(const std::string& organism, const std::string& name, const std::string& sequence);

    std::vector<Gene> get_all_genes_service();

    std::vector<Gene> sort_genes();

    std::vector<Gene> filter_genes(std::string& seq);
};
