TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	

.code
main PROC
	CALL Clrscr

	MOV dl, 5		; 0005
	SHL dl, 1		; shifts all the bits to the left which multiplies by 2
					; 000A

	MOV dh, 80
	SHR dh, 1
	SHR dh, 2		; dividing by 2 then by 4


	MOV dh, -80		; 
	SAR dh, 1
	SAR dh, 2

	MOV al, 11110000b
	ROR al, 1


	MOV ax, 0B0h
	MOV bx, 9Dh
	SHLD ax, bx, 1

	CALL DumpRegs

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here

;;;;;;;;;;;;;
END main ;last line, put PROCs before this