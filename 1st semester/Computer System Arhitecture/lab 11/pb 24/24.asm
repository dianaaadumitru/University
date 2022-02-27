;Read a string s1 (which contains only lowercase letters). Using an alphabet (defined in the data segment), determine and display the string s2 obtained by substitution of each letter of the string s1 with the corresponding letter in the given alphabet.
;Example:
;The alphabet:  OPQRSTUVWXYZABCDEFGHIJKLMN
;The string s1: anaaremere
;The string s2: OBOOFSASFS


bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import printf msvcrt.dll                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

                          
extern alphabett
                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    s1 db "anaaremere", 0
    len equ $-s1
    alphabet db "OPQRSTUVWXYZABCDEFGHIJKLMN", 0
    l dd 0
    format db "%s", 0
    s2 times len db -1
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov eax, len
        dec eax
        mov [l], eax
       
        push alphabet
        push s1
        push eax
        push s2
        call alphabett
        
        push s2
        push format
        call [printf]
        add esp, 8
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
