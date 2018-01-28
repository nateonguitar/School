TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
bigEndian BYTE 12h, 34h, 56h, 78h
littleEndian DWORD ?

.code
main PROC
	MOV eax, white + (blue * 16)
	CALL SetTextColor
	CALL Clrscr

	MOV eax, DWORD PTR bigEndian

	CALL DumpRegs
	; change colors
	MOV eax, white + (blue * 16)
	; SetTextColor looks at the eax value
	CALL SetTextColor

	CALL DumpRegs
	; change colors
	MOV eax, green + (brown * 16)
	; SetTextColor looks at the eax value
	CALL SetTextColor

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