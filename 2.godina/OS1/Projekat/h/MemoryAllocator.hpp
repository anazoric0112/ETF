#ifndef _memory_allocator_
#define _memory_allocator_


#include "../lib/hw.h"
#include "Handler.hpp"
#include "syscall_c.hpp"


class MemoryAllocator{
private:
    static uint8* free;
    //size next where
    //size - 64, next - uint8*
    static int sizeoff, nextoff, blockoff;

    static bool initialized;

    static void init();
public:

    static void* allocate(int all_size);
    static int deallocate(uint8* block);
};


#endif