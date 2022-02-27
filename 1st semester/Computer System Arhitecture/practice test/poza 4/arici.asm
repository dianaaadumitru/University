bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import scanf msvcrt.dll 
import printf msvcrt.dll   


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    cuv times 30 db 0
    n dd 0
    format_cuv db "%s", 0
    format_n db "%d", 0
    text times 60 db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push cuv
        push format_cuv
        call [scanf]
        add esp, 8
        
        push n
        push dword format_n
        call [scanf]
        add esp, 8
        
        mov esi, cuv
        mov ecx, 30
        mov edi, 0
        mov ax, [n]
        mov dx, [n+2]
        mov bx, 2
        div bx
        cmp dx, 0
        je even_number
            jecxz the_end
                start_loop:
                    lodsb
                    cmp al, 0
                    je not_character
                    add al, 20
                    mov [text+edi], al
                    inc edi
                
                loop start_loop
            the_end:
            not_character:
            jmp here
        even_number:
        jecxz the_end1
            start_loop1:
                lodsb
                cmp al, "a"
                je vowel
                cmp al, "e"
                je vowel
                cmp al, "i"
                je vowel
                cmp al, "o"
                je vowel
                cmp al, "u"
                je vowel
                jmp not_vowel
                vowel:
                mov [text+edi], al
                inc edi
                mov bl, "p"
                mov [text+edi], bl
                inc edi
                mov [text+edi], al
                inc edi
                jmp here_2
                not_vowel:
                mov [text+edi], al
                inc edi
                here_2:
                
                loop start_loop1
            the_end1:
        
        here:
        push text
        push format_cuv
        call [printf]
        add esp, 8
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
