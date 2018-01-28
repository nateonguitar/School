TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	intarray WORD 100h, 200h, 300h, 400h

.code
main PROC
	CALL Clrscr

	MOV eax, red + (white * 16)
	CALL setTextColor
	CALL Clrscr

	MOV edi, OFFSET intarray		; address of array
	MOV ecx, LENGTHOF intarray		; set loop counter
	MOV ax, 0

L1:
	ADD ax, [edi]					; add integer
	ADD edi, TYPE intarray			; point to the next integer

	loop L1

	CALL DumpRegs
	exit
main ENDP

END main