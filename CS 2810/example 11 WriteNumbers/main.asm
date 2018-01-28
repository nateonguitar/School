TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	intVal DWORD 35

.code
main PROC
	MOV eax, intVal
	CALL WriteBin
	CALL Crlf
	CALL WriteDec
	CALL Crlf
	CALL WriteHex
	CALL Crlf

	CALL WaitMsg

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here

;;;;;;;;;;;;;
END main ;last line, put PROCs before this