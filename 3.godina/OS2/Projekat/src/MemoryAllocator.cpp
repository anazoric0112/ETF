#include "../h/MemoryAllocator.hpp"

uint8* MemoryAllocator::free=nullptr;
int MemoryAllocator::sizeoff=0;
int MemoryAllocator::nextoff=sizeof(uint64);
int MemoryAllocator::blockoff=nextoff+sizeof(uint8*);
bool MemoryAllocator::initialized=false;



void* MemoryAllocator::allocate(int all_size){
    blockoff=16;
    //printString("alloc\n");
    if (!initialized) init();

    int blksize=MEM_BLOCK_SIZE;
    if (all_size< blksize) all_size= blksize;
    else if (all_size% blksize){
        all_size+= blksize - all_size % blksize;
    }

    uint8 *cur=free, *prev=nullptr;
    uint8** next;
    uint64* size;
    while (cur){
        size=(uint64*)cur;
        next=(uint8**)(cur+nextoff);
        if (*size>=(uint64)(all_size+blockoff)) break;
        prev=cur;
        cur=*next;
    }
    if(!cur) return nullptr;

    uint8* newblock=cur+blockoff+all_size;
    uint64* newsize=(uint64*)newblock;
    uint8** newnext=(uint8**)(newblock+nextoff);

    *newsize=(uint64)*size-all_size-blockoff;
    *size=(uint64)all_size;
    *newnext=*next;
    *next=newblock;

    if (prev) {
        uint8** prevnext=(uint8**)(prev+nextoff);
        *prevnext=newblock;
    }

    if (cur==free) free=newblock;
    uint64* freesize=(uint64*)(free+sizeoff);
    uint8** freenext=(uint8**)(free+nextoff);
    if((*freesize)==0){ free=*freenext;}

    return (void*)(cur+blockoff);
}

int MemoryAllocator::deallocate(uint8 *block) {
    blockoff=16;
    if (!initialized) init();

    if (!block) return -1;
    uint8* blockst=block-blockoff;
    uint64* size=(uint64*)blockst;
    uint8** next=(uint8**)(blockst+nextoff);
    uint8* nx = block+ *size;
    if (!free){
        free=blockst;

        return 0;
    }
    uint8 *cur=free, *prev=nullptr;
    uint64* cursize=(uint64*)cur;
    uint8* curend=(uint8*)(cur+ *cursize + blockoff-1);

    while (cur && blockst>cur ){
        if (blockst>=cur && blockst<=curend) return -1;
        uint8** curnext=(uint8**)(cur+nextoff);
        prev=cur;
        cur=*curnext;

        cursize=(uint64*)cur;
        curend=(uint8*)(cur+ *cursize + blockoff-1);
    }

    if(!prev){
        if (nx==free){
            uint64* freesize=(uint64*)free;
            uint8** freenext=(uint8**)(free+nextoff);
            *size+=(uint64)(blockoff+ *freesize);
            *next=*freenext;
            free=blockst;

            return 0;
        }
        *next=free;
        free=blockst;

        return 0;
    }

    uint64* prevsize=(uint64*)prev;
    uint8** prevnext=(uint8**)(prev+nextoff);
    uint8* prevend=(uint8*)(prev+blockoff+ *prevsize);

    uint64* nextsize=(uint64*)cur;
    uint8** nextnext=(uint8**)(cur+nextoff);

    if (prevend==blockst && cur==nx){
        *prevsize+=(uint64)(2*blockoff + *size + *nextsize);
        *prevnext=*nextnext;

        return 0;
    }
    else if (prevend==blockst){
        *prevsize+=(uint64)( *size+blockoff);

        return 0;
    }else if (cur==nx){
        *size+=(uint64)(blockoff+ *nextsize);
        *next=*nextnext;
        *prevnext=blockst;

        return 0;
    }

    *next=cur;
    *prevnext=blockst;
    return 0;
}

void MemoryAllocator::init() {
    uint64 offset=(uint64)((uint8*)HEAP_END_ADDR-(uint8*)HEAP_START_ADDR);
    offset=(offset/200 + offset*12/100)+1;
    offset+=(uint64)HEAP_START_ADDR;
    uint8* first=(uint8*)offset;

    MemoryAllocator::free=(uint8*)first;
    uint64* size=(uint64*)free;
    uint8** next=(uint8**)(free+nextoff);

    *size=(uint64)((uint8*)HEAP_END_ADDR-(uint8*)first-(uint8)blockoff);
    *next=0;
    MemoryAllocator::initialized=true;


}

