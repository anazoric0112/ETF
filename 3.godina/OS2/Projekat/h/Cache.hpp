#ifndef _cache_hpp_
#define _cache_hpp_

#include "../lib/hw.h"
#include "../h/BuddyAllocator.hpp"

class Cache{
    //constructor ptr, destructor ptr, size, errors, name, free slabs ptr, taken, other - 1 cache attributes
    //free_slab, taken, other - 1 mem buff attributes
private:

    typedef void (*f_ptr)(void*) ;

    static bool initialised;

    static int free_slab_off, full_slab_off, other_slab_off;
    static int cst_off, dst_off, size_off, error_off, name_off,exists_off, requested_more_off;
    static int slab_obj_num_off, slab_block_num_off, slab_next_off, slab_data_off;
    //const:
    static uint64** metadata_block;
    static uint64** cache_start;
    static uint64** mem_buffs_start;
    static int block_size;
    static int mem_buffs_cnt,mem_buffs_starting_size,one_mb_size;
    static int one_cache_size,cache_cnt;

    //get and set methods
    static uint64* get_mem_buff(int size){
        return (uint64*)((uint8*)mem_buffs_start+(size-mem_buffs_starting_size)*one_mb_size);
    }
    static uint64* get_cache_addr(int i){
        return (uint64*)((uint8*)cache_start+one_cache_size*i);
    }
    static uint64* get_free_slab(void* cache){
        return *(uint64**)((uint8*)cache+free_slab_off);
    }
    static uint64* get_full_slab(void* cache) {
        return *(uint64**)((uint8*)cache+full_slab_off);
    }
    static uint64* get_other_slab(void* cache) {
        return *(uint64**)((uint8*)cache+other_slab_off);
    }
    static f_ptr get_cst(void* cache){
        return (f_ptr)(*(uint64*)((uint8*)cache+cst_off));
    }
    static f_ptr get_dst(void* cache){
        return (f_ptr)(*(uint64*)((uint8*)cache+dst_off));
    }
    static char* get_name(void* cache) {
        return *(char**)((uint8*)cache+name_off);
    }
    static int get_size(void* cache) {
        return *(int*)((uint8*)cache+size_off);
    }
    static int get_error(void* cache) {
        return *(int*)((uint8*)cache+error_off);
    }
    static int get_exists(void* cache){
        return *(int*)((uint8*)cache+exists_off);
    }
    static int get_requested(void* cache){
        return *(int*)((uint8*)cache+requested_more_off);
    }

    static void write_free_slab(void* cache,void*slab){
        uint64** write_ptr=(uint64**)((uint8*)cache+free_slab_off);
        *write_ptr=(uint64*)slab;
    }
    static void write_full_slab(void* cache,void*slab){
        uint64** write_ptr=(uint64**)((uint8*)cache+full_slab_off);
        *write_ptr=(uint64*)slab;
    }
    static void write_other_slab(void* cache,void*slab){
        uint64** write_ptr=(uint64**)((uint8*)cache+other_slab_off);
        *write_ptr=(uint64*)slab;
    }
    static void write_cst(void* cache,void(*c)(void*)){
        f_ptr* wr=(f_ptr*)((uint8*)cache+cst_off);
        *wr=(f_ptr)c;
    }
    static void write_dst(void* cache,void(*d)(void*)){
        f_ptr* wr=(f_ptr*)((uint8*)cache+dst_off);
        *wr=(f_ptr)d;
    }
    static void write_name(void* cache,const char* name){
        const char** write_addr=(const char**)((uint8*)cache+name_off);
        *write_addr=name;
    }
    static void write_size(void* cache,int size){
        int* write_addr=(int*)((uint8*)cache+size_off);
        *write_addr=size;
    }
    static void write_error(void* cache,int error){
        int* write_addr=(int*)((uint8*)cache+error_off);
        *write_addr=error;
    }
    static void write_exists(void* cache,int exists){
        int* write_addr=(int*)((uint8*)cache+exists_off);
        *write_addr=exists;
    }
    static void write_req(void* cache,int req){
        int* write_addr=(int*)((uint8*)cache+requested_more_off);
        *write_addr=req;
    }

    static int get_slab_obj_num(void* slab){
        return *(int*)((uint8*)slab+slab_obj_num_off);
    }
    static int get_slab_blocks_num(void* slab){
        return *(int*)((uint8*)slab+slab_block_num_off);
    }
    static uint64* get_slab_next(void* slab){
        return *(uint64**)((uint8*)slab+slab_next_off);
    }
    static void* get_slab_data(void* slab){
        return (void*)((uint8*)slab+slab_data_off);
    }
    static void* get_object_from_slab(void* cache, void* slab,int i){
        uint8* base=(uint8*)get_slab_data(slab);
        return (void*)(base + (get_size(cache)+sizeof(int))*i + sizeof(int));
    }
    static int get_object_exists(void* cache, void* slab, int i){
        uint8* base=(uint8*)get_slab_data(slab);
        return *(int*)(base + (get_size(cache)+sizeof(int))*i);
    }

    static void write_slab_obj_num(void* slab, int num){
        int* write_addr=(int*)((uint8*)slab+slab_obj_num_off);
        *write_addr=num;
    }
    static void write_slab_blocks_num(void* slab, int num){
        int* write_addr=(int*)((uint8*)slab+slab_block_num_off);
        *write_addr=num;
    }
    static void write_slab_next(void* slab, void* next){
        uint64** write_addr=(uint64**)((uint8*)slab+slab_next_off);
        *write_addr=(uint64*)next;
    }
    static void write_object_exists(void* cache,void* slab, int i, int ex){
        uint8* base=(uint8*)slab + slab_data_off;
        int* write_addr=(int*)(base + (get_size(cache)+sizeof(int))*i);
        *write_addr=ex;
    }

    static void* get_available_object(void* cache);
    static void* find_available_in_slab(void* slab, void* c);
    static bool is_in_slab(void* slab,void* obj);
    static void erase_from_slab(void* c,void* slab,void* obj, bool do_dst_cst=true);
    static void erase_all_slab(void* c,void* slab);

    static void add_slab_to(void* c,int where,void* slab, bool cst=true); //0-free,1-other,2-full
    static void remove_slab_from(void* c,int where, void* slab); //0-free,1-other,2-full

    static int print_int(uint64 x);
    static int print_str(const char* str);
    static int print_str(char* str);
    static void print_blank(int field,int i);
public:
    static void init();

    static void* create(const char* nam, size_t  size, void (*ctor)(void*), void (*dtor)(void*));
    static void destroy(uint64* c);
    static int shrink(uint64* c);

    static void* obj_alloc(uint64* c);
    static void obj_free(uint64* c,void* obj);

    static void* buff_alloc(size_t size);
    static void buff_free(void* obj);

    static void print_info(uint64* c);
    static void print_slabs_info(uint64* c);
    static void print_slabs_info(int c);
    static int error(uint64* c);
};


#endif
