TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
word1 WORD 1000h,2000h,3000h,4000h,5000h
dword1 DWORD 10000h,20000h,30000h,40000h
 


.code
main PROC
	CALL Clrscr

	mov al,125
	sub al,-5


	exit
main ENDP

END main