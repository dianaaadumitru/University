bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf, scanf               
import exit msvcrt.dll    
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
        file_name db "abcd.txt", 0
        file_descriptor dd -1
        access_mode db "w", 0
        len equ 50
        text times len db 0
        n dd 0
        format db "%d", 0
        sixteen db 16
        two db 2
        ten db 10
    

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
       je errorr
            mov edi, 0
            here:
            push n
            push format
            call [scanf]
            add esp, 8
            
            mov eax, [n]
            cmp eax, 0
            je end_read
                cmp al, 9
                ja more_digits
                    add al, byte "0"
                    mov [edi+text], al
                    inc edi
                    mov [edi+text], byte " "
                    inc edi
                    jmp here_2
                more_digits:
                mov ax, [n]
                mov dx, [n+2]
                
                div word [ten]
                add al, byte "0"
                add dl, byte "0"
                mov [edi+text], al
                inc edi
                mov [edi+text], dl
                inc edi
                mov [edi+text], byte " "
                    inc edi
                
                here_2:
                
                
                sub al, byte "0"
                mov eax, [n]
                cmp al, 15
                ja more_digits_2
                    cmp al, 9
                    ja same
                        add al, "0"
                        jmp here_3
                    same:
                    cmp al, 10
                    jne no_1
                        mov al, "A"
                        jmp here_3
                    no_1:
                    cmp al, 11
                    jne no_2
                        mov al, "B"
                        jmp here_3
                    no_2:
                    
                    cmp al, 12
                    jne no_3
                        mov al, "C"
                        jmp here_3
                    no_3:
                    
                    cmp al, 13
                    jne no_4
                        mov al, "D"
                        jmp here_3
                    no_4:
                    
                    cmp al, 14
                    jne no_5
                        mov al, "E"
                        jmp here_3
                    no_5:
                    
                    cmp al, 15
                    jne no_6
                        mov al, "F"
                        jmp here_3
                    no_6:
                    
                    here_3:
                    mov [edi+text], al
                    inc edi
                    mov[edi+text], byte " "
                    inc edi
                    jmp here_4
                more_digits_2:
                mov ax, [n]
                mov dx, [n+2]
                mov bx, 16
                div bx
                cmp al, 9
                    ja same1
                        add al, "0"
                        jmp here_31
                    same1:
                    cmp al, 10
                    jne no_11
                        mov al, "A"
                        jmp here_31
                    no_11:
                    cmp al, 11
                    jne no_21
                        mov al, "B"
                        jmp here_31
                    no_21:
                    
                    cmp al, 12
                    jne no_31
                        mov al, "C"
                        jmp here_31
                    no_31:
                    
                    cmp al, 13
                    jne no_41
                        mov al, "D"
                        jmp here_31
                    no_41:
                    
                    cmp al, 14
                    jne no_51
                        mov al, "E"
                        jmp here_31
                    no_51:
                    
                    cmp al, 15
                    jne no_61
                        mov al, "F"
                        jmp here_31
                    no_61:

                    here_31:
                    
                    cmp dl, 9
                    ja same2
                        add al, "0"
                        jmp here_32
                    same2:
                    cmp dl, 10
                    jne no_12
                        mov dl, "A"
                        jmp here_32
                    no_12:
                    cmp dl, 11
                    jne no_22
                        mov dl, "B"
                        jmp here_32
                    no_22:
                    
                    cmp dl, 12
                    jne no_32
                        mov dl, "C"
                        jmp here_32
                    no_32:
                    
                    cmp dl, 13
                    jne no_42
                        mov dl, "D"
                        jmp here_32
                    no_42:
                    
                    cmp dl, 14
                    jne no_52
                        mov dl, "E"
                        jmp here_32
                    no_52:
                    
                    cmp dl, 15
                    jne no_62
                        mov dl, "F"
                        jmp here_32
                    no_62:
                    here_32:
                    mov [text+edi], al
                    inc edi
                    mov [text+edi], dl
                    inc edi
                    mov [text+edi], byte " "
                    inc edi
                here_4:
                
                mov cl, 0
                mov ax, [n]
                mov dx, [n+2]
                
                conv2:
                    mov bx, 2
                    div bx
                    cmp dx, 1
                    jne not_1
                        inc cl
                    
                    not_1:
                    cmp ax, 0
                    je end_nr
                    mov dx, 0
                    
                jmp conv2
                end_nr:
                add cl, byte "0"
                mov [edi+text], cl
                inc edi
                mov [edi+text], byte " "
                inc edi 
                
                mov [edi+text], byte " "
                inc edi 
                
                mov [edi+text], byte " "
                inc edi 
                
                
                
            jmp here
            end_read:
            
            push text
            push dword [file_descriptor]
            call [fprintf]
            add esp, 8
       
            push dword [file_descriptor]
            call [fclose]
            add esp, 4
       errorr:
        
    
        ; exit(0)
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
