#ifndef _buddy_allocator_
#define _buddy_allocator_

#include "../lib/hw.h"

class BuddyAllocator{
private:
    static bool initialised;
    static uint64** pointers;
    static uint8* start, *end;
    static int pointers_count;
    static int blockoff,sizeoff,nextoff;
    static int block_size;

    static void addBlock(void* block);
    static void removeBlock(void* block);

    static int get_size(void* block);
    static uint64* get_next(void* block);
    static uint64* get_prev(void* block);
    static void* get_block_address(void* block);

    static void write_size(void* block, uint64 size);
    static void write_next(void* block, uint64* next);

    static uint8* get_buddy(void* block);
    static void try_merge(void*block);
    static uint64 pow2(uint64 x);
    static uint64 log2(uint64 x);
    static void print_int(uint64 x);
public:
    static void init(void* start,int blk);
    static void* alloc(uint64 size);
    static void dealloc(void* block);

    static void print_buddy();

    static int get_slab_size_in_blocks(void* slab){
        uint64* start=(uint64*)((uint8*)slab-blockoff);
        return pow2(get_size(start));
    }
};

#endif