#include "repo.h"

Gene Repo::search_for_gene(const std::string &organism, const std::string &name)
///search in the list of genes for a tutorial by its organism and name
///returns the tutorial if it exists or an empty gene otherwise

{
    std::vector<Gene> all_genes = this->genes;
    int size = all_genes.size();
    for (int i = 0; i < size; ++i) {
        Gene g = all_genes[i];
        if (g.get_organism() == organism && g.get_name() == name)
            return g;
    }
    return Gene{};
}

bool Repo::add_gene(const Gene &gene)
///add a new gene to the list
///returns true if the gene was added, false otherwise
{
    Gene g = search_for_gene(gene.get_organism(), gene.get_name());
    if (g.get_organism() != "" || g.get_name() != "")
        return false;
    this->genes.push_back(gene);
    return true;
}

std::vector<Gene> Repo::get_all_genes() {
    return this->genes;
}