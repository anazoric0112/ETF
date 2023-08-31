.global _ZN7_thread13switchContextEPNS_7ContextES1_
.type _ZN7_thread13switchContextEPNS_7ContextES1_, @function
_ZN7_thread13switchContextEPNS_7ContextES1_:
    sd ra, 0*8(x10)
    sd sp, 1*8(x10)
    ld ra, 0*8(x11)
    ld sp, 1*8(x11)

    ret