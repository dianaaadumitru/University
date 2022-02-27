bits 32 ; assembling for the 32 bits architecture

global start        

extern exit, fopen, fread, fclose, printf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fread msvcrt.dll
import fclose msvcrt.dll 
import printf msvcrt.dll   
import fprintf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    file_name db "abc.txt", 0
    ; filename to be read
    access_mode db "r", 0       ; file access mode:
                                ; r - opens a file for reading. The file must exist. 
    file_descriptor dd -1       ; variable to hold the file descriptor
    len equ 30                 ; maximum number of characters to read
    text times len db 0         ; string to hold the text which is read from file
    sum db 0
    prod dw 1
    maxi db 0 
    poz times len db -1
    format_1 db "sum: %d ", 0
    format_2 db "product: %d ", 0
    format_3 db "maxim: %d ", 0
    l db -1
    file_name_2 db "abc2.txt", 0    ; filename to be created
    access_mode_2 db "w", 0       ; file access mode:
                                  ; w - creates an empty file for writing
    file_descriptor_2 dd -1       ; variable to hold the file descriptor
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ; call fopen() to create the file
        ; fopen() will return a file descriptor in the EAX or 0 in case of error
        ; eax = fopen(file_name, access_mode)
        push dword access_mode     
        push dword file_name
        call [fopen]
        add esp, 4*2                ; clean-up the stack

        mov [file_descriptor], eax  ; store the file descriptor returned by fopen

        ; check if fopen() has successfully created the file (EAX != 0)
        cmp eax, 0
        je error_fopen
            push dword [file_descriptor]
            push dword len
            push dword 1
            push dword text
            call [fread]
            add esp, 4*4
            
            mov ebx, len
            sub ebx, eax    
            mov eax, len
            sub eax, ebx    ;in eax - the length of the read text
            
            mov dh, al
            
            mov ecx, eax    ; we put the length len in ECX in order to make the loop
            mov esi, text      ;in eds:esi we will store the FAR address of the string "text"
            cld             ;parse the string from left to right(DF=0)
            mov edi, 0

            jecxz the_end
            start_loop:
                lodsb       ;in eax we will have the current doubleword from the string
                cmp al, " "
                je space
                sub al, '0' ;transform from a character to a number
                mov dl, al  ;copy of the number
                mov dh, 0
                mov bl, byte [sum]
                add al, bl
                mov [sum], al
                
                
                mov al, dl
                mov bl, byte [maxi]
                cmp al, bl
                jl lower
                    mov [maxi], al
                    mov al, dh
                    sub al, cl
                    mov edi, 0
                    mov [poz + edi], al
                    jmp next
                lower:
                mov al, dl
                mov bl, byte [maxi]
                cmp al, bl
                je maxi_2
                    mov al, dh
                    sub al, cl
                    inc edi
                    mov [poz + edi], al
    
                maxi_2:
                next:
                mov al, dl
                mov ah, 0
                mov bx, [prod]
                mul bx
                mov [prod], ax
                space:
            loop start_loop
            the_end:

       error_fopen:
       
        mov al, [sum]
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax
        
        push eax
        push dword format_1
        call [printf]
        add esp, 4*2
        
        mov ax, [prod]
        mov dx, 0
        push dx
        push ax
        pop eax
        
        push eax
        push dword format_2
        call [printf]
        add esp, 4*2
        
        mov al, [maxi]
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax
        
        push eax
        push dword format_3
        call [printf]
        add esp, 4*2
        
        mov al, dl
        mov ah, 0
        mov bx, [prod]
        mul bx
        mov [prod], ax
        
        
        ; Sa se scrie sirul de numere in baza doi intr-un alt fisier.
        
                ; call fopen() to create the file
        ; fopen() will return a file descriptor in the EAX or 0 in case of error
        ; eax = fopen(file_name, access_mode)
        push dword access_mode_2     
        push dword file_name_2
        call [fopen]
        add esp, 4*2                ; clean-up the stack

        mov [file_descriptor_2], eax  ; store the file descriptor returned by fopen
        
        ; check if fopen() has successfully created the file (EAX != 0)
        cmp eax, 0
        je final

        ; write the text to file using fprintf()
        ; fprintf(file_descriptor, text)
        push dword text
        push dword [file_descriptor_2]
        call [fprintf]
        add esp, 4*2

        ; call fclose() to close the file
        ; fclose(file_descriptor)
        push dword [file_descriptor_2]
        call [fclose]
        add esp, 4

      final:
        ; exit(0)
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
