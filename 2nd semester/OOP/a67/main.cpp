#include <iostream>
#include <string>
#include "ui/UI.h"
#include "tests/tests.h"
#include <crtdbg.h>


using namespace std;

int main() {
/*
    Tests tests;
    tests.test_all();
    FileRepo* repo = new FileRepo

    Repo* repo = new Repo();
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


    repo->add_tutorial(t1);
    repo->add_tutorial(t2);
    repo->add_tutorial(t3);
    repo->add_tutorial(t4);
    repo->add_tutorial(t5);
    repo->add_tutorial(t6);
    repo->add_tutorial(t7);
    repo->add_tutorial(t8);
    repo->add_tutorial(t9);
    repo->add_tutorial(t10);

    Repo *watchlist = new Repo();
    auto* service = new Service(repo, watchlist);
    UI *ui = new UI{service};
    ui->run();
    delete ui;
*/

    try {
        IFileRepo *r = nullptr;//new FileRepo("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\a67-913-Dumitru-Diana\\tutorials.txt");
        IFileRepo *watch = nullptr; // = new FileRepo("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\a67-913-Dumitru-Diana\\watchlist.txt");
        r = new FileRepo("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\a67-913-Dumitru-Diana\\tutorials.txt");
        cout<<"CSV or HTML? (1. CSV, 2. HTML)\n";
        int c;
        cout<<">>";
        cin>>c;
        if (c == 1) {
            watch = new CSVRepo("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\a67-913-Dumitru-Diana\\watchlist.csv");
        }
        else{
            watch = new HTMLRepo("C:\\Users\\diana\\Desktop\\uni work\\2nd sem\\OOP\\a67-913-Dumitru-Diana\\watchlist.html");
        }
        auto *service = new Service(r, watch);


        UI *ui = new UI(service);
        ui->run();
        delete ui;
    } catch (RepoException& e) {
        std::cout << e.which_message()<<std::endl;
    }
    catch (ValidationException& e) {
        std::cout << e.which_message()<<std::endl;
    }

//    int x = _CrtDumpMemoryLeaks();
//    cout<<"Memory leaks: "<<x;
    return 0;
}



/*
 Never Gonna Give You Up,Rick Astley,3,32,90000,https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1&t=18
I gotta feeling,The Black Eyed Peas,4,49,65895,https://www.youtube.com/watch?v=Wnn6M1nNiKQ
Pump it,The Black Eyed Peas,3,46,78903,https://www.youtube.com/watch?v=ZaI2IlHwmgQ
Memories,David Guetta,3,28,68000,https://www.youtube.com/watch?v=NUVCQXMUVnI
Can't hold us,Macklemore,7,3,12345,https://www.youtube.com/watch?v=2zNSgSzhBfM
The Nights,Avicii,3,10,83748,https://www.youtube.com/watch?v=UtF6Jej8yb4
Toxic,Britney Spears,3,31,23361,https://www.youtube.com/watch?v=LOZuxwVk7TU
A lalala long,Bob Marley,3,45,93729,https://www.youtube.com/watch?v=-JhwxTen6yA
I Need A Dollar,Aloe Blacc,4,3,23746,https://www.youtube.com/watch?v=Y6CJ_LG4YI4
Wake Me Up Before You Go-Go,Wham,3,53,63721,https://www.youtube.com/watch?v=pIgZ7gMze7A&list=RDdQw4w9WgXcQ&index=2
 */



















