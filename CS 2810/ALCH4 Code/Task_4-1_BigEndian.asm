TITLE Program Template           (Task_4-1_BigEndian.asm)

; Program Description: Convert a doubleword from Big Endian order to Little Endian
; Author: You
; DueDate:
; Revisions:

INCLUDE Irvine32.inc

.data
bigEndian byte 12h,34h,56h,78h
littleEndian dword ?

.code
main proc
	; Display "BigEndian" which is reverse on x86
	mov eax, DWORD PTR bigEndian
	CALL DumpRegs ;
	
	mov	 ah,byte ptr bigEndian				; high byte
	mov  al,byte ptr bigEndian+1			; 2nd byte
	mov	 word ptr littleEndian+2,ax			; high word 
	mov  ah,byte ptr bigEndian+2			; 3rd byte
	mov	 al,byte ptr bigEndian+3			; 4th byte	
	mov	 word ptr littleEndian,ax			; low word
	; Display "LittleEndian" which is reverse on x86
	mov eax, DWORD PTR littleEndian 
	CALL DumpRegs ;

	CALL WaitMsg  ;Print Wait Message VS2013, might print this twice 
	exit
	
main endp
end main
