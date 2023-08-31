# 1 "src/Interrupt.S"
# 1 "<built-in>"
# 1 "<command-line>"
# 31 "<command-line>"
# 1 "/usr/riscv64-linux-gnu/include/stdc-predef.h" 1 3
# 32 "<command-line>" 2
# 1 "src/Interrupt.S"
.extern _ZN7Handler9interruptEv

.align 4
.global _ZN7Handler10InterruptfEv
.type _ZN7Handler10InterruptfEv, @function
_ZN7Handler10InterruptfEv:

    addi sp, sp, -256
    .irp i, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31
    sd x\i, \i * 8(sp)
    .endr

    csrw sscratch, sp
    call _ZN7Handler9interruptEv
    #csrr sp, sscratch

    .irp i, 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31
    ld x\i, \i * 8(sp)
    .endr
    addi sp, sp, 256

    sret
