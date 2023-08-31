//
// Created by os on 1/14/23.
//

#include "../h/BuddyAllocator.hpp"
#include "../h/_console.hpp"

bool BuddyAllocator::initialised=false;
uint64** BuddyAllocator::pointers= nullptr;
uint8* BuddyAllocator::start=nullptr;
uint8* BuddyAllocator::end=nullptr;
int BuddyAllocator::pointers_count=0;
int BuddyAllocator::sizeoff=0;
int BuddyAllocator::nextoff=sizeof(uint64);
int BuddyAllocator::blockoff=sizeof(uint64)+sizeof(uint64*);
int BuddyAllocator::block_size=4096;


//size_data=8388608
void BuddyAllocator::init(void* start_ptr,int blk){
    uint8* end_ptr=(uint8*)start_ptr+blk*block_size;
    uint64 size=(uint64)((uint8*)end_ptr-(uint8*)start_ptr);
    size/=block_size;

    int log2=BuddyAllocator::log2(size);
    uint64 size_data=pow2(log2)*block_size;
    int size_meta=log2*sizeof(uint64);
    if (size_meta+size_data>size*block_size) {
        log2--;
        size_data/=2;
        size_meta=log2*sizeof(uint64);
    }

    pointers=(uint64**)start_ptr;
    pointers_count=log2;
    for (int i=0;i<=pointers_count;i++) {
        if (i<pointers_count)
            pointers[i] = (uint64 *) nullptr;
        else {
            pointers[i] = (uint64 *) (pointers + pointers_count+1);
            write_size(pointers[i],log2);
            write_next(pointers[i], nullptr);
            start=(uint8*)pointers[i];
            end=start+size_data;
        }
    }
    initialised = true;
}

void* BuddyAllocator::alloc(uint64 size) {
    if (!size) return nullptr;
    size+=sizeof(uint64)+sizeof(uint64*);
    uint64 init_size=size;
    if (size%block_size!=0) size=size/block_size+1;
    else size=size/block_size;
    int all_size= log2(size);
    if (pow2(all_size)*block_size<init_size) all_size++;
    int pom=all_size;
    while(pom<=pointers_count && pointers[pom]==nullptr) pom++;
    if (pom==pointers_count+1) return nullptr; //no more space

    while (pom>all_size){
        uint64* block=pointers[pom];
        removeBlock(block);
        write_size(block, get_size(block)-1);
        uint64* buddy= (uint64*)get_buddy(block);
        write_size(buddy, get_size(block));

        addBlock(block);
        addBlock(buddy);
        pom--;
    }
    uint64* ret=pointers[pom];
    removeBlock(ret);
    return get_block_address(ret);
}

void BuddyAllocator::dealloc(void *block) {
    if ((uint8*)block<start || (uint8*)block>=end) return;

    uint64* block_start=(uint64*)((uint8*)block-blockoff);
    addBlock(block_start);
    try_merge(block_start);
}

//prima adresu gde je pocetak size
void BuddyAllocator::addBlock(void* block){
    uint64 size= get_size(block);
    uint64* curr=pointers[size];
    uint64* prev=nullptr;
    if (curr==nullptr || curr>block){
        pointers[size]=(uint64*)block;
        write_next(block, curr);
    } else if (get_next(curr)==nullptr){
        write_next(curr,(uint64*)block);
        write_next(block,(uint64*)nullptr);
    }
    else {
        while (get_next(curr) != nullptr && curr < (uint64*)block) {
            prev = curr;
            curr = get_next(curr);
        }
        if (get_next(curr)==nullptr && curr<(uint64*)block){
            write_next(curr,(uint64*)block);
            write_next(block,(uint64*)nullptr);
            return;
        }
        write_next(prev,(uint64*)block);
        write_next(block,curr);
    }
}

void BuddyAllocator::try_merge(void *block) {
    int size= get_size(block);
    uint64* prev=get_prev(block);
    uint8* buddy= get_buddy(block);
    if (buddy==(uint8*) get_next(block)){
        removeBlock(block);
        removeBlock(get_next(block));
        write_size(block,size+1);
        addBlock(block);
        try_merge(block);
    }else if (buddy==(uint8*)prev){
        removeBlock(block);
        removeBlock(prev);
        write_size(prev,size+1);
        addBlock(prev);
        try_merge(prev);
    }
}

//prima adresu gde je pocetak size
void BuddyAllocator::removeBlock(void* block){
    uint64 size= get_size(block);
    uint64* curr=pointers[size];
    uint64* prev=nullptr;
    while (curr!=(uint64*)block){
        if (!curr) return; //not found, error

        prev=curr;
        curr= get_next(curr);
    } //curr je sada block
    if (prev) write_next(prev, get_next(curr));
    else pointers[size]= get_next(curr);
}


int BuddyAllocator::get_size(void *block) {
    return *(uint64*)((uint8*)block+sizeoff);
}

uint64* BuddyAllocator::get_next(void *block) {
    return *(uint64**)((uint8*)block+nextoff);
}

uint64* BuddyAllocator::get_prev(void *block) {
    int size= get_size(block);
    uint64* ptr=pointers[size];
    uint64* prev= nullptr;
    while (ptr && ptr!=block){
        prev=ptr;
        ptr= get_next(ptr);
    } return prev;
}

void* BuddyAllocator::get_block_address(void *block){
    blockoff=sizeof(uint64)+sizeof(uint64*);
    return (void*)((uint8*)block+blockoff);
};

void BuddyAllocator::write_size(void *block, uint64 size) {
    uint64* write_addr=(uint64*)((uint8*)block+sizeoff);
    *write_addr=size;
}

void BuddyAllocator::write_next(void *block, uint64 *next) {
    uint64** write_addr=(uint64**)((uint8*)block+nextoff);
    *write_addr=next;
}

uint8* BuddyAllocator::get_buddy(void *block) {
    uint64 size= get_size(block);
    uint64 level_size=block_size;
    for (uint64 i=0;i<size;i++) level_size*=2;
    int index=(uint64)((uint64)block-(uint64)start)/level_size;
    uint8* buddy=nullptr;
    if (index%2){
        buddy=(uint8*)block-level_size;
    }else {
        buddy=(uint8*)block+level_size;
    } return buddy;
}

uint64 BuddyAllocator::pow2(uint64 x) {
    uint64 y=1;
    for (uint64 i=0;i<x;i++) y*=2;
    return y;
}
//returns floor of log2
uint64 BuddyAllocator::log2(uint64 x) {
    uint64 i=0,size=sizeof(uint64)*8-1;
    uint64 mask =((uint64)1)<<size;
    while(!(mask&x)){
        mask>>=1; i++;
    } return size-i;
}

void BuddyAllocator::print_int(uint64 x){
    if (x==0) {_console::putBuffer('0'); return;}
    uint64 y=0; int zc=0;
    while (x>0){
        y*=10;
        y+=x%10;
        x=x/10;
        if (y==0) zc++;
    }
    while (y>0){
        char c=y%10+'0';
        _console::putBuffer(c);
        y=y/10;
    }
    for(int i=0;i<zc;i++) _console::putBuffer('0');
}

void BuddyAllocator::print_buddy() {
    for (uint64 i=0;i<=(uint64)pointers_count;i++){
        print_int(i); _console::putBuffer(':'); _console::putBuffer('\n');
        uint64* ptr=pointers[i];
        while (ptr){
            _console::putBuffer('\t');
            print_int(((uint64)ptr-(uint64)start)/(block_size* pow2(i))); _console::putBuffer(' ');
            //print_int(get_size(ptr)); _console::putBuffer(' ');
            if (get_next(ptr)==nullptr) print_int(0);
            else print_int(((uint64)get_next(ptr)-(uint64)start)/(block_size* pow2(i)));
            _console::putBuffer('\n');
            ptr= get_next(ptr);
        }
    }
}