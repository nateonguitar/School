TITLE 					(main.asm)

INCLUDE Irvine32.inc
CHAR_VALUE = 'X' ; set global constant value

.data
	declairClass BYTE "CS2810 Fall Semester 2015",0
	declairAssignment BYTE "Assembler Assignment #2",0
	declairName BYTE "Nathan Brooks",0

	count DWORD ?
	color DWORD 0
	row BYTE 0

.code
main PROC
	CALL Clrscr
	
	; clear the registers
	XOR eax, eax
	XOR ebx, ebx
	XOR ecx, ecx
	XOR edx, edx
	XOR esi, esi
	XOR edi, edi
	XOR ebp, ebp

	; move the cursor and write "CS2810 Fall Semester 2015"
	MOV dh, 1
	MOV dl, 6
	CALL Gotoxy
	MOV edx, OFFSET declairClass
	CALL WriteString

	; move the cursor and write "Assembler Assignment #2"
	MOV dh, 2
	MOV dl, 6
	CALL Gotoxy
	MOV edx, OFFSET declairAssignment
	CALL WriteString

	; move cursor and write "Nathan Brooks"
	MOV dh, 3
	MOV dl, 6
	CALL Gotoxy
	MOV edx, OFFSET declairName
	CALL WriteString

	; set the outer loop count
	MOV ecx, 16
				
L1:
		; ecx will be decremented, 
		; so lets make count = that decremented previous count
		MOV count, ecx

		; set the inner loop count
		MOV ecx, 16		
L2:

		; set text color
		MOV eax, color
		CALL SetTextColor

		; increment color for next pass
		; will be set to 256 on the final pass
		; but won't be used
		INC eax
		MOV color, eax


		; move cursor and write "X"
		; will use the current loop count to move the cursor
		; to the correct position
		; will use eax to manipulate
		; count is 4 bytes so need to use the whole eax
		; for example eax will = 00 00 00 16
		;          and al will =          16
		; al will still be set with 22d correctly (16h)
		; and the rest of eax will be zeroed out

		; final "X" will be at collumn 22
		; 22 - ecx (ecx is the reverse counting register) 
		; this will place the cursor at the correct position
		MOV eax, 22
		SUB eax, ecx

		; only need dl but the size is miss-matched
		; dh will be reset in just a second
		; but dl is correct now
		MOV edx, eax

		; move dh to the correct row
		MOV al, row
		ADD al, 5
		MOV dh, al
	
		CALL Gotoxy

		MOV al, CHAR_VALUE
		CALL WriteChar


	; repeat inner loop
	LOOP L2

	; restore outer loop count
	MOV ecx, count

	; increment row for the next pass
	MOV al, row
	INC al
	MOV row, al

	; repeat outer loop
	LOOP l1
	
	MOV eax, 8 + (black * 16)
	CALL SetTextColor

	MOV dh, 21
	MOV dl, 6
	CALL Gotoxy
	CALL WaitMsg
	exit
main ENDP

END main