
#include <stdio.h>
#include<math.h>

void menu()
///function that prints the menu
{
    printf("1. Pascal Pyramid\n"
           "2. Longest contiguous subsequence of prime numbers\n"
           "3 Compute the approximated value of square root of a positive real number. The precision is provided by the user.\n"
           "0. Exit");
}

int prime_number(int x)
/// verify if a number is prime or not
/// \param x
/// \return true if the number is prime, false otherwise
{
    if(x < 2 || (x % 2 == 0 && x != 2))
        return 0;
    for(int d=3; d*d<=x; d+=2)
        if(x % d == 0)
            return 0;
    return 1;
}


void longest_sub(int n, int a[], int b[])
/// searches for the longest subsequence that contains only prime numbers
/// \param n dim of the array
/// \param a the array where we search for the subsequence
/// \param b we save in this array the starting pos and the ending pos of the longest subsequence
{
    int nr = 0, maxi = 0; // we use nr to count every element from a seq with prime numbers and save the longest one in maxi
    for(int i=0; i<n; i++)
        if(prime_number(a[i])) // if it is prime we increase the number of consecutive prime elements
            nr++;
        else
        {
            if(nr > maxi)  // if we have a longer sequence we save its starting and ending pos and modify the value of maxi
            {
                maxi = nr;
                b[1] = i;
                b[0] = i - nr;
            }
            nr = 0;
        }
    if(nr > maxi)  // we do the same thing as above for the last elements if they are prime
    {
        b[1] = n;
        b[0] = n - nr;
    }
}

void pascal_triangle(int n)
///function for pascal's triangle
/// \param n dimension of the triangle
{
    for(int m=1; m<=n; m++)
    {
        int comb = 1;  //used to repr C(m, k)
                      //the line of the triangle always starts with 1
        for(int k=1; k<=m; k++)
        {
            printf("%d ", comb);
            comb = comb * (m - k) / k;
        }
        printf("\n");

    }
}

int aproxim(int d)
/// this function creates a number used for division with d decimals
/// \param d
/// \return
{
    int p = 1;
    while(d)
    {
        p *= 10;
        d--;
    }
    return p;
}

float create_new_number(float x, int d)
/// creates the rounded number
/// \param x
/// \param d
/// \return
{

    float new_value = (int)(x * aproxim(d));
    new_value = new_value / aproxim(d);
    return new_value;
}


int main()
{
    int done = 1, choice;
    while(done) // loop for the menu, the user can choose the command and the program stops when he chooses to exit
    {
        menu();
        printf("\ncmd: ");
        scanf("%d", &choice);
        if(choice == 0)
            done = 0;
        else
            if(choice == 1)  //the user chose to print the pascal triangle and to do that we read the user inputs
                             //then use the previous function pascal_triangle
            {
                int n;
                printf("dim of the triangle: ");
                scanf("%d", &n);
                printf("\n");
                pascal_triangle(n);
            }
        else
            if(choice == 2) //the user chose to print the longest contiguous subsequence of prime numbers and to do that we read the user inputs
                            //then use the previous function longest_sub
            {
                int n;
                printf("dim of the list: ");
                scanf("%d", &n);
                int a[1000];
                printf("your list: ");
                for (int i = 0; i<n; i++)
                    scanf("%d", &a[i]);
                int b[2];
                longest_sub(n, a, b);
                printf("longest contiguous subsequence: ");
                for(int i = b[0]; i < b[1]; i++)
                    printf("%d ", a[i]);
                printf("\n");
            }
        else
            if(choice == 3)
            {
                float x;
                printf("number: ");
                scanf("%f", &x);
                printf("how many decimals? ");
                int d;
                float s = sqrt(x);
                scanf("%d", &d);
                printf("%f\n", s);

                printf("%g\n", create_new_number(s,d));

            }

        else
            printf("invalid command\n");
    }
    return 0;
}