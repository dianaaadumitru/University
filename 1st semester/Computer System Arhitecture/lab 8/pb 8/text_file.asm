bits 32

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
    file_name db "abc.txt", 0   ; filename to be read
    access_mode db "r", 0       ; file access mode:
                                ; r - opens a file for reading. The file must exist. 
    file_descriptor dd -1       ; variable to hold the file descriptor
    len equ 30                  ; maximum number of characters to read
    s times len db 0            ; string to hold the text which is read from file
    end_s db -1
    letters times 26 db 0       ;frequency string for every capital letter
    end_l db -1
    l_max db 0                  ;most frequent letter
    fr_max db 0                 ;its frequency
    format_l db "%c", 0
    format_f db "%d", 0

; our code starts here
segment code use32 class=code
    ;A text file is given. Read the content of the file, determine the uppercase letter with the highest frequency and display the letter along with its frequency on the screen. The name of text file is defined in the data segment.
    
    start:
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

        ; read the text from file using fread()
        ; after the fread() call, EAX will contain the number of chars we've read 
        ; eax = fread(text, 1, len, file_descriptor)
        push dword [file_descriptor]
        push dword len
        push dword 1
        push dword s        
        call [fread]
        add esp, 4*4
        
        mov ebx, len
        sub ebx, eax    
        mov eax, len
        sub eax, ebx    ;the length of the read text

        mov ecx, eax    ; we put the length len in ECX in order to make the loop
        mov esi, s      ;in eds:esi we will store the FAR address of the string "s"
        cld             ;parse the string from left to right(DF=0)
        jecxz the_end
            start_loop:
                lodsb       ;in eax we will have the current doubleword from the string
                cmp al, 'A'
                jl too_small
                    cmp al, 'Z'
                    jg too_big      ;verify if it's a big letter and if it is not it goes to the next letter from the string
                        sub al, 'A' ;we calculate the position into the frequency string
                        mov edi, eax
                        mov ebx, [letters + edi]
                        inc ebx     ;we increase the frequency of the letter
                        add al, 'A'
                        mov dl, [fr_max]
                        cmp bl, dl
                        jl jump1
                            mov [l_max], al     ;we save in l_max the last letter with the highest frequency
                            mov [fr_max], bl    ;we save in fr_max the frequency of last letter with the highest frequency
                        jump1:
                        mov [letters + edi], ebx    ;we mov the frequencyof the current leter in the string letters
                    too_big:
                too_small:
            loop start_loop
        the_end:
                        
        mov al, [l_max]
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax     ;we will display the letter stored in eax
         
        ; place parameters on the stack from right to left
        push eax
        push dword format_l     
        call [printf]   ; call function printf for printing
        add esp, 4 * 2  ; free parameters on the stack; 4 = size of dword; 2 = number of parameters
        
        mov al, [fr_max]
        mov ah, 0
        mov dx, 0
        push dx
        push ax
        pop eax     ;we will display the frequency stored in ebx

        ; place parameters on the stack from right to left
        push eax
        push dword format_f
        call [printf]   ; call function printf for printing
        add esp, 4 * 2  ; free parameters on the stack; 4 = size of dword; 2 = number of parameters
      
        ; call fclose() to close the file
        ; fclose(file_descriptor)
        push dword [file_descriptor]
        call [fclose]
        add esp, 4

      error_fopen:
    
        ; exit(0)
        push dword 0
        call [exit]