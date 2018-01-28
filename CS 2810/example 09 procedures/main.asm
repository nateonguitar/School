TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	myArray DWORD 10h, 20h, 30h
	theSum DWORD ?

.code
main PROC
	CALL Clrscr

	CALL ArraySum
	CALL DumpRegs
	

	CALL WaitMsg

	

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here
ArraySum PROC
	MOV esi, 0					; index
	MOV eax, 0					; sum
	MOV ecx, LENGTHOF myArray	; set counter

L1:
	ADD eax, myArray[esi]		; move index
	ADD esi, 4
	LOOP L1

	MOV theSum, eax
	ret
ArraySum ENDP
;;;;;;;;;;;;;
END main ;last line, put PROCs before this