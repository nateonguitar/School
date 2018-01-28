TITLE 					(main.asm)

INCLUDE Irvine32.inc
.data
numbers DWORD 87654321h, 45346894h, 00193492h
buffer BYTE 9 DUP(0)			; 8 digits plus null character

.code
main PROC

	mov	esi, OFFSET numbers
	mov	ecx, LENGTHOF numbers	; counter
L1:
	mov	eax, [esi]			; packed decimal number
	; pointer to buffer
	call PackedToAsc			; convert to ASCII digits
	call WriteString			; display string of digits
	call Crlf

	add	esi, TYPE numbers		; next number
	loop L1
	
	CALL WaitMsg

	exit
main ENDP

;;;;;;;;;;;;;; define new prodecures here
;----------------------------------------------------------------
PackedToAsc PROC
;
; procedure that converts a 4-byte packed decimal number
; to a string of ASCII decimal digits
; Receives: EAX = packed decimal number
; Returns: String of ASCII digits in buffer pointed by EDX
;------------------------------------------------------------------

	pushad			; save general registers to stack

	mov	ecx,8		; 8 decimals digits (2 digits per byte)
L2:
	MOV	ebx,eax			; make a copy of number
	AND	ebx,0F0000000h	; keep 4 highest bits (one decimal digit)
	ROL ebx, 4			; move to the lower 4 positions
	OR bl, 30h			; convert digit to ASCII character
	MOV [ebx], bl		; save to buffer

	INC edx				; next byte in buffer
	
	SHL eax, 4			; discard highest 4 bits (already copied)
	loop L2				; next digit

	popad				; restore registers from stack
	ret

PackedToAsc ENDP
;;;;;;;;;;;;;
END main ;last line, put PROCs before this