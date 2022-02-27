#include "service.h"
#include <cstring>

bool Service::add_gene_service(const std::string &organism, const std::string &name, const std::string &sequence)
///add a new gene to the list
///returns true if the gene was added, false otherwise
{
    Gene g{organism, name, sequence};
    return this->repo.add_gene(g);
}

std::vector<Gene> Service::get_all_genes_service() {
    return this->repo.get_all_genes();
}

std::vector<Gene> Service::sort_genes() {
    std::vector<Gene> all = this->repo.get_all_genes();
    for (int i = 0; i < all.size() - 1; ++i) {
        for (int j = i+1; j < all.size(); ++j) {
            if (all[i].get_sequence().length() < all[j].get_sequence().length())
            {
                Gene aux = all[i];
                all[i] = all[j];
                all[j] = aux;
            }

        }
    }
    return all;
}

std::vector<Gene> Service::filter_genes(std::string &seq) {
    std::vector<Gene> v = repo.get_all_genes();
    std::vector<Gene> n{};
    for (int i = 0; i < v.size(); ++i) {
        size_t found = v[i].get_sequence().find(seq);
        if (found != std::string::npos)
            n.push_back(v[i]);


    }
    return n;
}
