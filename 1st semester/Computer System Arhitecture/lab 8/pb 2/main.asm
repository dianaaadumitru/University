bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fread, fclose, printf
import exit msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
     file_name db "abc.txt", 0   ; filename to be read
    access_mode db "r", 0       ; file access mode:
                                ; r - opens a file for reading. The file must exist. 
    file_descriptor dd -1       ; variable to hold the file descriptor
    len equ 30                  ; maximum number of characters to read
    s times len db 0            ; string to hold the text which is read from file
    nr db -1
    format db "number fo const: %d ", 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword access_mode
        push dword file_name
        call [fopen]
        add esp, 4*2
        
        mov [file_descriptor], eax
        cmp eax, 0
        je error_fopen
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword s
            call [fread]
            add esp, 4*4
        
            mov bl, 0
            mov [nr], al
            mov ecx, eax
            mov esi, s
            cld
            
            jecxz the_end
                start_loop:
                    lodsb
                    cmp eax, 'a'
                    jne cons1
                        inc bl
                    cons1:
                    
                    cmp eax, 'e'
                    jne cons2
                        inc bl
                    cons2:
                    
                    cmp eax, 'i'
                    jne cons3
                        inc bl
                    cons3:
                    
                    cmp eax, 'o'
                    jne cons4
                        inc bl
                    cons4:
                    
                    cmp eax, 'u'
                    jne cons5
                        inc bl
                    cons5:
                    
                loop start_loop
            the_end:
        
        error_fopen:
        mov al, [nr]
        sub al, bl
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax
        
        push dword eax
        push format
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
