bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fread              
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll    


; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    len equ 2
    n times len db 0 
    file_name db "nr.txt", 0
    access_mode db "r", 0
    file_descriptor dd -1
    text times 13 db 0
    new_file times 13 db 0
    nr db 0
    access_mode_2 db "w", 0
    file_descriptor_2 dd -1
    

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
        
            push dword [file_descriptor]
            push dword len
            push dword 1
            push n
            call [fread]
            add esp, 16
            
            push dword [file_descriptor]
            call [fclose]
            add esp, 45
        error_fopen:
        mov esi, n
        mov edi, 0
        lodsb
        mov bl, al
        lodsb
        cmp al, 0
        jne one_digit
            sub bl, byte "0"
            mov [nr], bl
            jmp here
        one_digit:
        sub bl, byte "0"
        shl bl, 4
        sub al, byte "0"
        add bl, al
        mov [nr], bl
        
        mov cl, [nr]
        mov ch, 0
        mov ax, cx
        mov cl,  byte 16
        div cl
        mov dl, ah
        mov cl, byte 10
        mul cl
        add al, dl
        mov cl, al
        
        mov bl, byte "0"
        mov al, byte "1"
        here:
        mov dl, 0
        nexxt:
        mov edi, 0
        cmp dl, 9
        ja two_digits
            add dl, "0"
            mov [new_file+edi], dl
            inc edi
            mov [new_file+edi], byte "o"
            inc edi
            mov [new_file+edi], byte "u"
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "p"
            inc edi
            mov [new_file+edi], byte "u"
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "."
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "x"
            inc edi
            mov [new_file+edi], byte "t"
            sub dl, byte "0"
            jmp to_end
        two_digits:
            
            mov [new_file+edi], al
            inc edi
            
            mov [new_file+edi], bl
            inc bl
            inc edi
            mov [new_file+edi], byte "o"
            inc edi
            mov [new_file+edi], byte "u"
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "p"
            inc edi
            mov [new_file+edi], byte "u"
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "."
            inc edi
            mov [new_file+edi], byte "t"
            inc edi
            mov [new_file+edi], byte "x"
            inc edi
            mov [new_file+edi], byte "t"
        
        to_end:
        cmp dl, 19
        jne jmp_here_1
            mov al, byte "2"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here_1:
        
            
        cmp dl, 29
        jne jmp_here_2
            mov al, byte "3"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here_2:
            
        cmp dl, 39
        jne jmp_here_3
            mov al, byte "4"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here_3:
            
        cmp dl, 49
        jne jmp_here_4
            mov al, byte "5"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here_4:
            
            
        cmp dl, 59
        jne jmp_here_5
            mov al, byte "6"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here_5:
            
            
        cmp dl, 69
        jne jmp_here6
            mov al, byte "7"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here6:
            
        cmp dl, 79
        jne jmp_here7
            mov al, byte "8"
            mov bl, byte "0"
            jmp jmp_here
        jmp_here7:
            
        cmp dl, 89
        jne jmp_here8
            mov al, byte "9"
            mov bl, byte "0"
            jmp jmp_here
        
        jmp_here8:
        
        
        jmp_here:
        
        inc dl
        pushad
        
        push access_mode_2
        push new_file
        call [fopen]
        add esp, 8
        
        mov [file_descriptor_2], eax
        
        push dword [file_descriptor_2]
        call [fclose]
        add esp, 4
        
        
        popad
        
        cmp dl, cl
        je end_files
        jmp nexxt
        
        end_files:
        
        
            

        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
