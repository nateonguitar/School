TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
	hello BYTE 'hello there',0
	wordVal WORD 8

.code
main PROC
	CALL Clrscr

	MOV ax, wordVal
	AND ax, 1b			; sets the zero flag if the result of the operation = 0
	JZ EvenValue		; if it's true it will skip the wait message

	CALL WaitMsg

EvenValue:
	MOV edx, OFFSET hello
	CALL WriteString

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here

;;;;;;;;;;;;;
END main ;last line, put PROCs before this