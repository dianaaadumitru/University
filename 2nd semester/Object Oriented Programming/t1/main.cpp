#include <iostream>
#include <string>
#include <assert.h>
#include "ui.h"

using namespace std;

void test_add()
{
    Repo repo{};
    Gene g1{"E_Coli_K12", "yqgE", "ATGACATCATCATTG"};
    bool ok = repo.add_gene(g1);
    assert(ok == true);
    bool ok1 = repo.add_gene(g1);
    assert(ok1 == false);
    assert(repo.get_all_genes().size() == 1);
}

int main()
{
    test_add();
    Repo repo{};
    Gene g1{"E_Coli_K12", "yqgE", "ATGACATCATCATTG"};
    Gene g2{"M_tuberculosis", "ppiA", "TCTTCATCATCATCGG"};
    Gene g3{"Mouse","Col2a1" , "TTAAAGCATGGCTCTGTG"};
//    Gene g4{E_Coli_ETEC | yqgE | GTGACAGCGCCCTTCTTTCCACG};
//    Gene g5{E_Coli_K12 | hokC | TTAATGAAGCATAAGCTTGATTTC};
    repo.add_gene(g1);
    repo.add_gene(g2);
    repo.add_gene(g3);

    Service service{repo};
    UI ui{service};
    ui.run();
}
