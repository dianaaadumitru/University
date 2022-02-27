bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fopen, fclose, fprintf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import scanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll    

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "abcd.txt", 0
    access_mode db "w", 0
    file_descriptor dd -1
    n dd 0
    format db "%c", 0
    text times 50 db 0
    text2 times 50 db 0
    len db 0
    len2 db 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push access_mode
        push file_name
        call [fopen]
        add esp, 8
        
        mov [file_descriptor], eax
        
        cmp eax, 0
        je error_fopen
        mov edi, 0
        
        push dword n
        push format
        call [scanf]
        add esp, 4*2
        here:
        
        
        mov eax, [n]
        cmp eax, "!"
        je end_read
            sub eax, "0"
            mov [n], eax
            mov ax, [n]
            mov dx, [n+2]
            mov bx, 2
            div bx
            cmp dx, 0
            jne not_even
                mov bl, [len]
                inc bl
                mov [len], bl
                mov ecx, [n]
                add cl, "0"
                mov [text+edi], cl
                inc edi
            
            not_even:
                push dword n
        push format
        call [scanf]
        add esp, 4*2
        
        mov eax, [n]
        cmp eax, "!"
        je end_read

        push dword n
        push format
        call [scanf]
        add esp, 4*2

        jmp here
        
        end_read:
        mov bl, [len]
        mov [len2], bl
        mov esi, text
        mov edi, 0
        bubble_sort:
            
            mov bl, [len2]
            mov [len], bl
            mov cl, 1
            mov esi, text
            
            lodsb
            here3:
            mov bl, al
            lodsb
            cmp bl, al
            jae lower
                mov dl, bl
                mov bl, al
                mov al, dl
                mov cl, 0
                mov [text+edi], bl
                inc edi
                mov [text+edi], al
                dec edi
            lower:
                inc edi
                mov dl, [len]
                dec dl
                mov [len], dl
            mov dl, [len]
            cmp dl, 1
            jbe end_for
            jmp here3
            end_for:
            mov edi, 0
            cmp cl, 0
            jne end_sort
            
        
        jmp bubble_sort
        end_sort:
        
        push text
        push dword [file_descriptor]
        call [fprintf]
        add esp, 8
        
       
        push dword [file_descriptor]
        call [fclose]
        add esp, 4
        error_fopen:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
