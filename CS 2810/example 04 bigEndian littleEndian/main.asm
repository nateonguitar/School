TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
bigEndian BYTE 12h, 34h, 56h, 78h
littleEndian DWORD ?

.code
main PROC
	CALL Clrscr

	MOV eax, DWORD PTR bigEndian

	; get most significant byte of bigEndian
	MOV ah, BYTE PTR bigEndian

	; get 2nd
	MOV al, BYTE PTR bigEndian+1

	; get the high word
	MOV WORD PTR littleEndian+2, ax

	; get 3rd
	MOV ah, byte PTR bigEndian+2

	; get 4th
	MOV al, BYTE PTR bigEndian+3

	; get the low word
	MOV WORD ptr littleEndian, ax
	MOV eax, DWORD PTR littleEndian


	CALL DumpRegs
	

	CALL WaitMsg


	exit
main ENDP

END main