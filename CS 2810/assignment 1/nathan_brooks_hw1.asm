TITLE nathan_brooks_hw1

INCLUDE Irvine32.inc
.data
	declairClass BYTE "CS2810 Fall Semester 2014",0
	declairAssignment BYTE "Assembler Assignment #1",0
	declairName BYTE "Nathan Brooks",0

	VarA SDWORD 40
	VarB SDWORD 30
	VarC SDWORD 20
	VarD SDWORD 10

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

	; move the cursor and write
	MOV dh, 2
	MOV dl, 12
	CALL Gotoxy
	MOV	 edx,OFFSET declairClass
	CALL WriteString
	
	;move the cursor and write
	MOV dh, 3
	MOV dl, 12
	CALL Gotoxy
	MOV edx,OFFSET declairAssignment
	CALL WriteString

	;move the cursor and write
	MOV dh, 4
	MOV dl, 12
	CALL Gotoxy
	MOV edx, OFFSET declairName
	CALL WriteString

	; do this math
	; F = (A + B) – (C + D) 
	; F = AB+CD+-

	; AB+
	MOV eax, VarA
	ADD eax, VarB

	; CD+
	MOV edx, VarC
	ADD edx, VarD

	; subtract the results from the previous two operations
	SUB eax, edx


	;move the cursor and write
	MOV dh, 6
	MOV dl, 0
	CALL Gotoxy
	CALL DumpRegs
	CALL WaitMsg


	exit
main ENDP

END main