#include <stdio.h>

// the function declared in convert.asm
int convert(int a);
//Show for each number from 32 to 126 the value of the number (in base 8) and the character with that ASCII code
int main()
{
    int a = 32;
    while ( a <= 126)
    {
        printf("Number: %d ", convert(a));
        printf("Character: %c ", a);
        printf("\n");
        a += 1;
    }
    return 0;
}