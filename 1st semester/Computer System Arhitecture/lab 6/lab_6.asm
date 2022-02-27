bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s dd 12345678h, 1A2C3C4Dh, 98FCDC76h
    len equ ($-s)/4   ;the length of the string (in doublewords)
    d times len db 0
    ten db 10   ;variabile used for determining the digits in base 10 of a number by successive divisions to 10
    
segment code use32 class=code
    start:
        ;A list of doublewords is given. Obtain the list made out of the low bytes of the high words of each doubleword from the given list with the property that these bytes are palindromes in base 10.
        ;Example:
        ;Given the string of doublewords:
        ;s DD 12345678h, 1A2C3C4Dh, 98FCDC76h
        ;obtain the string of bytes:
        ;d DB 2Ch, FCh.
        
        mov ecx, len    ; we put the length l in ECX in order to make the loop
        mov esi, s      ;in eds:esi we will store the FAR address of the string "s"
        cld             ;parse the string from left to right(DF=0)
        mov edi, 0
        jecxz the_end
            start_loop:
                lodsd       ;in eax we will have the curremt doubleword from the string
                shr eax, 16
                mov ah, 0   ;we are interested in the low byte (least significant) of this word (AL)
                mov dl, al  ;we store in dl the number in base 16
                mov bh, 1   ;we use bh to save in bl     the last digit of the number
                ;we know that a byte has maximum 3 digits so we compare the first with the second digit or the first with the third digit too see if the number is a palindrom
                transf:
                    div byte[ten]   ;divide the number by 10 in order to obtain the last digit; this digit will be in ah
                    cmp bh, 1
                    jne not_equal
                    mov bl, ah      ;we save the last digit of the number in bl
                    dec bh
                    not_equal:
                    cmp al, 0
                    jz end_tranfs   ;if the quotient (from al) is 0 it means we obtained all the digits and we can leave the loop "transf"
                                    ;else prepare the quotient for a new iteration 
                    mov ah, 0
                jmp transf          ;resume the loop in order to obtain a new digit.
                end_tranfs:
                cmp ah, bl          ;we compare the first digit with the last digit of the number to see if it is o palindrom
                jne not_palindrom
                mov [d+edi], dl     ;if it's a palindrom we add the representation in base 16 to the new string
                inc edi
                not_palindrom:  ;   we jump here if it is not a palindrom
            loop start_loop
        the_end:
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
