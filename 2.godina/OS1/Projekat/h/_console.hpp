#ifndef PROJEKAT_BASE__CONSOLE_HPP
#define PROJEKAT_BASE__CONSOLE_HPP

#include "MemoryAllocator.hpp"
#include "Buffer.hpp"
#include "syscall_c.hpp"

class _console{

public:
    static void putBuffer(char c);
    static char getBuffer();

    static void printBuffer();
    static void inputBuffer();
    static void init();

    static int incount, outcount, getreq, putreq;

private:
    static const int DEFAULT_BUFFER_SIZE = 256;

    static _buffer bufferIn, bufferOut;

};


#endif //PROJEKAT_BASE__CONSOLE_HPP