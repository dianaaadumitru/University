#include <iostream>
#include <string>
#include "ui/UI.h"
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include "tests/tests.h"

using namespace std;

int main() {
{
//    Tests tests;
//    tests.test_all();

    Repo repo{};
    Tutorial t1{"Pointers", "Jeff", Duration{16, 58}, 200, "https://www.youtube.com/watch?v=DTxHyVn0ODg"};
    Tutorial t2{"Classes", "Ryan", Duration{8, 41}, 167, "https://www.youtube.com/watch?v=2BP8NhxjrO0"};
    Tutorial t3{"Classes vs Structs", "Peter", Duration{8, 31}, 150,
                "https://www.youtube.com/watch?v=fLgTtaqqJp0&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&start_radio=1&t=4"};
    Tutorial t4{"Templates", "Ryan", Duration{17, 57}, 312,
                "https://www.youtube.com/watch?v=I-hZkUa9mIs&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=4"};
    Tutorial t5{"References", "Jane", Duration{10, 12}, 223,
                "https://www.youtube.com/watch?v=IzoFn3dfsPA&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=5"};
    Tutorial t6{"Headers", "Alex", Duration{15, 9}, 462,
                "https://www.youtube.com/watch?v=9RJTQmK0YPI&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=11"};
    Tutorial t7{"Dynamic arrays", "Ryan", Duration{14, 13}, 165,
                "https://www.youtube.com/watch?v=PocJ5jXv8No&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=16"};
    Tutorial t8{"Iterators", "Richard", Duration{17, 8}, 76,
                "https://www.youtube.com/watch?v=SgcHcbQ0RCQ&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=26"};
    Tutorial t9{"Libraries", "Ryan", Duration{18, 34}, 111,
                "https://www.youtube.com/watch?v=or1dAmUO8k0&list=RDCMUCQ-W1KE9EYfdxhL6S4twUNw&index=24"};
    Tutorial t10{"Data Structures and Algorithms", "Ryan", Duration{10, 31}, 87,
                 "https://www.youtube.com/watch?v=vA4QG1PlTRI"};

    repo.add_tutorial(t1);
    repo.add_tutorial(t2);
    repo.add_tutorial(t3);
    repo.add_tutorial(t4);
    repo.add_tutorial(t5);
    repo.add_tutorial(t6);
    repo.add_tutorial(t7);
    repo.add_tutorial(t8);
    repo.add_tutorial(t9);
    repo.add_tutorial(t10);

    Service service{repo};
    UI ui{service};
    ui.run();
}

   int x = _CrtDumpMemoryLeaks();
   cout<<"Memory leaks: "<<x;
    return 0;
}



















