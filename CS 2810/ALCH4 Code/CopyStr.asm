; Copying a String (CopyStr.asm)

; This program copies a string.
INCLUDE Irvine32.inc

.data
source  byte  "This is the source string",0
target  byte  SIZEOF source DUP(0),0

.code
main proc

	mov  esi,0				; index register
	mov  ecx,SIZEOF source	; loop counter
L1:
	mov  al,source[esi]		; get a character from source
	mov  target[esi],al		; store it in the target
	inc  esi				; move to next character
	loop L1					; repeat for entire string

	;invoke ExitProcess,0
	CALL WaitMsg  ;Print Wait Message VS2013, might print this twice 
	exit
main endp
end main
