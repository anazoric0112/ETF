#include "../h/Cache.hpp"
#include "../h/_console.hpp"
//cache metadata
int Cache::free_slab_off=0;
int Cache::full_slab_off=sizeof(uint64*);
int Cache::other_slab_off=sizeof(uint64*)*2;
int Cache::size_off=sizeof(uint64*)*3;
int Cache::cst_off=sizeof(uint64*)*4;
int Cache::dst_off=sizeof(uint64*)*5;
int Cache::name_off=sizeof(uint64*)*5 + sizeof(uint64);
int Cache::error_off=sizeof(uint64*)*6 + sizeof(uint64);
int Cache::exists_off=sizeof(uint64*)*6 + sizeof(uint64)*2;
int Cache::requested_more_off=sizeof(uint64*)*6 + sizeof(uint64)*2+sizeof(uint32);
int Cache::one_cache_size= sizeof(uint64*)*6 + sizeof(uint64)*2+sizeof(uint32)*2;
int Cache::one_mb_size=sizeof(uint64*)*4;
int Cache::block_size=4096-2*sizeof(uint64*);

//slab metadata
int Cache::slab_obj_num_off=0;
int Cache::slab_block_num_off=sizeof(uint64);
int Cache::slab_next_off=sizeof(uint64)*2;
int Cache::slab_data_off=sizeof(uint64)*2+sizeof(uint64*);

//memory parts metadata
int Cache::mem_buffs_cnt=13;
int Cache::mem_buffs_starting_size=5;

int Cache::cache_cnt=0;
bool Cache::initialised=false;
uint64** Cache::metadata_block=nullptr;
uint64** Cache::cache_start=nullptr;
uint64** Cache::mem_buffs_start=nullptr;


//done
void Cache::init(){
    metadata_block=(uint64**)BuddyAllocator::alloc(1);
    mem_buffs_start=metadata_block;
    cache_start=(uint64**)((uint8*)metadata_block+one_mb_size*mem_buffs_cnt);
    cache_cnt=(block_size-one_mb_size*mem_buffs_cnt)/one_cache_size;

    for (int i=0;i<cache_cnt;i++){
        uint64* cache=get_cache_addr(i);
        write_exists(cache,0);
    }
    for (int i=mem_buffs_starting_size,pow=32;i<mem_buffs_starting_size + mem_buffs_cnt;i++){
        write_size(get_mem_buff(i),pow); pow*=2;
    }
//    print_int((uint64)metadata_block-(uint64)HEAP_START_ADDR);
//    _console::putBuffer('\n');
//    print_int((uint64)mem_buffs_start-(uint64)HEAP_START_ADDR); _console::putBuffer('\n');
//    print_int((uint64)cache_start-(uint64)HEAP_START_ADDR); _console::putBuffer('\n');
//    print_int(cache_cnt); _console::putBuffer('\n');
    initialised=true;
}
//done
void *Cache::create(const char *nam, size_t size, void (*ctor)(void *), void (*dtor)(void *)) {
    if (size<=0) return nullptr;

    for (int i=0;i<cache_cnt;i++){
        uint64* cache= get_cache_addr(i);
        if (get_exists(cache)) continue;

        write_free_slab(cache,nullptr);
        write_full_slab(cache,nullptr);
        write_other_slab(cache,nullptr);
        write_cst(cache, ctor);
        write_dst(cache,dtor);
        write_size(cache,size);
        write_exists(cache,1);
        write_error(cache,0);
        write_name(cache,nam);
        write_req(cache,0);
        add_slab_to(cache,0,nullptr,true);
        return cache;
    }
    return nullptr; //no free caches
}
//done
void *Cache::obj_alloc(uint64 *c) {
    if (!c) return nullptr;

    void* obj=get_available_object(c);
    if (obj) return obj;

    add_slab_to(c, 1, nullptr);
    write_req(c,1);
    obj=get_available_object(c);
    return obj;
}
//done
void Cache::obj_free(uint64 *c, void *obj) {
    if (!c || !obj){
        if (c) write_error(c,-1);
        return;
    }
    uint64* slab_head= get_other_slab(c);
    while (slab_head){
        if (is_in_slab(slab_head,obj)) {
            erase_from_slab(c,slab_head,obj);
            //empty now?
            if (get_slab_obj_num(slab_head)==0){
                remove_slab_from(c,1,slab_head);
                add_slab_to(c,0,slab_head);
            }
            return;
        }
        slab_head=get_slab_next(slab_head);
    }
    slab_head= get_full_slab(c);
    while(slab_head){
        if (is_in_slab(slab_head,obj)) {
            erase_from_slab(c,slab_head,obj);
            //not full anymore
            remove_slab_from(c,2,slab_head);
            add_slab_to(c,1,slab_head);
            return;
        }
        slab_head=get_slab_next(slab_head);
    }
}
//done
void Cache::destroy(uint64 *c) {
    if (!c) return;

    //deallocate all slabs and objects from them
    uint64* slab_head= get_other_slab(c);
    while(slab_head){
        erase_all_slab(c,slab_head);
        uint64* temp=slab_head;
        slab_head=get_slab_next(slab_head);
        remove_slab_from(c,1,temp);
        BuddyAllocator::dealloc(temp);
    } slab_head= get_full_slab(c);
    while(slab_head){
        erase_all_slab(c,slab_head);
        uint64* temp=slab_head;
        slab_head=get_slab_next(slab_head);
        remove_slab_from(c,2,temp);
        BuddyAllocator::dealloc(temp);
    } slab_head= get_free_slab(c);
    while (slab_head){
        uint64* temp=slab_head;
        slab_head=get_slab_next(slab_head);
        remove_slab_from(c,0,temp);
        BuddyAllocator::dealloc(temp);
    }

    //mark cache as non-existant
    write_free_slab(c,nullptr);
    write_other_slab(c,nullptr);
    write_full_slab(c,nullptr);
    write_exists(c,0);
}
//done
int Cache::shrink(uint64 *c) {
    if (!c) return -1;
    int cnt=0;
    if (!get_requested(c)){
        //dealocirati sve prazne slabove
        uint64* slab_head= get_free_slab(c);
        while (slab_head){
            uint64* temp=slab_head;
            slab_head=get_slab_next(slab_head);
            remove_slab_from(c,0,temp);
            BuddyAllocator::dealloc(temp);
            cnt++;
        }
        write_free_slab(c,nullptr);

    }
    write_req(c,0);
    return cnt;
}
//done
void* Cache::buff_alloc(size_t size) {
    uint64 comp_size=1,cnt=mem_buffs_starting_size;
    for(uint64 i=0;i<cnt;i++) comp_size*=2;
    uint64 max_size=comp_size;
    for(int i=0;i<mem_buffs_cnt-1;i++) max_size*=2;
    if (size>max_size || size<=0) return nullptr;

    while (comp_size<size){
        comp_size*=2;
        cnt++;
    }

    uint64* mem_buff= get_mem_buff(cnt);
    //print_int((uint64)mem_buff);
    //print_str("\n");
    if (!mem_buff) return nullptr;

    void* obj=get_available_object(mem_buff);
    if (obj) return obj;
    add_slab_to(mem_buff, 1, nullptr, false);
    obj=get_available_object(mem_buff);
    return obj;

}
//done
void Cache::buff_free(void *obj) {
    if (!obj) return;
    for (int i=mem_buffs_starting_size;i<mem_buffs_starting_size+mem_buffs_cnt;i++){
        uint64* mem_buff= get_mem_buff(i);

        uint64* slab_head= get_other_slab(mem_buff);
        while (slab_head){
            if (is_in_slab(slab_head,obj)) {
                erase_from_slab(mem_buff,slab_head,obj,false);
                //maybe empty now?
                if (get_slab_obj_num(slab_head)==0) {
                    remove_slab_from(mem_buff, 1, slab_head);
                    add_slab_to(mem_buff, 0, slab_head, false);

                    //erase empty ones:
                    slab_head= get_free_slab(mem_buff);
                    while (slab_head){
                        uint64* temp=slab_head;
                        slab_head=get_slab_next(slab_head);
                        remove_slab_from(mem_buff,0,temp);
                        BuddyAllocator::dealloc(temp);
                    }
                    write_free_slab(mem_buff,nullptr);
                }
                return;
            }
            slab_head=get_slab_next(slab_head);
        }
        slab_head= get_full_slab(mem_buff);
        while(slab_head){
            if (is_in_slab(slab_head,obj)) {
                erase_from_slab(mem_buff,slab_head,obj,false);
                //not full anymore
                remove_slab_from(mem_buff,2,slab_head);
                add_slab_to(mem_buff,1,slab_head,false);
                return;
            }
            slab_head=get_slab_next(slab_head);
        }
    }

}

void Cache::print_info(uint64 *c) {
    int slab_cnt=0;
    int block_cnt=0;
    int size_in_bytes= get_size(c);
    int actual_obj=0;
    char* name= get_name(c);

    uint64* slab_head= get_free_slab(c);
    if (!slab_head) slab_head= get_other_slab(c);
    if (!slab_head) slab_head= get_full_slab(c);
    int slab_blocks_num=get_slab_blocks_num(slab_head);
    int slab_obj_cnt=( slab_blocks_num* block_size - sizeof(uint64) * 2 - sizeof(uint64 *))
                     / (get_size(c) + sizeof(int));
    //counting slabs and objects in them
    slab_head=get_free_slab(c);
    while (slab_head){
        slab_cnt++;
        actual_obj+= get_slab_obj_num(slab_head);
        slab_head= get_slab_next(slab_head);
    } slab_head= get_other_slab(c);
    while(slab_head){
        slab_cnt++;
        actual_obj+= get_slab_obj_num(slab_head);
        slab_head= get_slab_next(slab_head);
    } slab_head= get_full_slab(c);
    while(slab_head){
        slab_cnt++;
        actual_obj+= get_slab_obj_num(slab_head);
        slab_head= get_slab_next(slab_head);
    }

    block_cnt=slab_cnt*slab_blocks_num;
    uint64 percentage=100*actual_obj/(slab_obj_cnt*slab_cnt);

    int field=30;
    const char* h1="Naziv";
    const char* h2="Velicina u B";
    const char* h3="Broj blokova";
    const char* h4="Broj ploca";
    const char* h5="Broj obj u 1 ploci";
    const char* h6="Popunjenost";
    print_blank(field, print_str(h1));
    field=20;
    print_blank(field, print_str(h2));
    print_blank(field, print_str(h3));
    print_blank(field, print_str(h4));
    print_blank(field, print_str(h5));
    print_blank(field, print_str(h6));
    _console::putBuffer('\n');
    field=30;
    print_blank(field, print_str(name));
    field=20;
    print_blank(field, print_int(size_in_bytes));
    print_blank(field, print_int(block_cnt));
    print_blank(field, print_int(slab_cnt));
    print_blank(field, print_int(slab_obj_cnt));
    print_int(percentage);
    _console::putBuffer('%');
    _console::putBuffer('\n');
}
//done
int Cache::error(uint64 *c) {
    return get_error(c);
}

//finds space for object in cache if there is
void *Cache::get_available_object(void *c) {
    if (!c) return nullptr;

    uint64* slab = get_other_slab(c);
    while (slab) {
        void* obj=(uint64*)find_available_in_slab(slab,c);
        if (obj) { //if its full now, it needs to be moved
            int max_obj = (get_slab_blocks_num(slab) * block_size - sizeof(uint64) * 2 - sizeof(uint64 *))
                          / (get_size(c) + sizeof(int));
            if (get_slab_obj_num(slab)==max_obj){
                remove_slab_from(c,1,slab);
                add_slab_to(c,2,slab);
            }
            return obj;
        }
        slab=get_slab_next(slab);
    }
    slab = get_free_slab(c);
    while (slab) {
        void* obj=(uint64*)find_available_in_slab(slab,c);
        if (obj) { //free slab is not free anymore, needs to be moved
            remove_slab_from(c,0,slab);
            add_slab_to(c,1,slab);
            return obj;
        }
        slab=get_slab_next(slab);
    }
    write_error(c,-1);
    return nullptr;
}
//increments obj_num and exists=1 and returns space for object in slab
void *Cache::find_available_in_slab(void *slab,void* c) {
    int slab_size_blocks = BuddyAllocator::get_slab_size_in_blocks(slab);
    int max_obj = (slab_size_blocks *block_size - sizeof(uint64) * 2 - sizeof(uint64 *))
                  / (get_size(c) + sizeof(int));
    for (int i = 0; i < max_obj; i++) {
        if (get_object_exists(c, slab, i)) continue;

        write_object_exists(c, slab, i, 1);
        write_slab_obj_num(slab,get_slab_obj_num(slab)+1);
        return get_object_from_slab(c, slab, i);
    }
    return nullptr;
}
//adds existing or new slab,modifies next, and obj and block num if it is new
void Cache::add_slab_to(void *c, int where, void*  slab,bool cst_do) {
    if (!c || where<0 || where>2) {
        if (c && cst_do) write_error(c,-2);
        return;
    }

    uint64* slab_head=nullptr;
    if (where==0) slab_head= get_free_slab(c);
    else if (where==1) slab_head= get_other_slab(c);
    else if (where==2) slab_head= get_full_slab(c);

    if (!slab) { //allocating new slab cuz we dont have it already
        uint64 min_wanted= get_size(c)*20;
        slab = (uint64 *) BuddyAllocator::alloc(min_wanted);
        if (!slab) {
            write_error(c,-2);
            return;
        }
        int blcks=BuddyAllocator::get_slab_size_in_blocks(slab);
        write_slab_blocks_num(slab, blcks);
        write_slab_obj_num(slab, 0);
        int max_obj = (get_slab_blocks_num(slab) *block_size - sizeof(uint64) * 2 - sizeof(uint64 *))
                      / (get_size(c) + sizeof(int));
        //void(cst*)(void*)=get_cst(c);
        f_ptr cst=get_cst(c);
        for (int i = 0; i < max_obj; i++) {
            write_object_exists(c, slab, i, 0);
            if (cst && cst_do) cst(get_object_from_slab(c,slab,i));
        }
    }
    write_slab_next(slab,slab_head);

    if (where==0) write_free_slab(c,slab);
    else if (where==1) write_other_slab(c,slab);
    else if (where==2) write_full_slab(c,slab);

}
//only removes it from the structure
void Cache::remove_slab_from(void *c, int where, void *slab) {
    if (!c || where<0 || where>2 || !slab) {
        if (c) write_error(c,-2);
        return;
    }

    uint64* slab_head=nullptr;
    if (where==0) slab_head= get_free_slab(c);
    else if (where==1) slab_head= get_other_slab(c);
    else if (where==2) slab_head= get_full_slab(c);
    if (!slab_head) {
        if (c) write_error(c,-2);
        return;
    }//error - cant remove

    uint64* prev=nullptr;
    while (slab_head){
        if (slab_head==slab) break;
        prev=slab_head;
        slab_head=get_slab_next(slab_head);
    }
    if(prev) write_slab_next(prev,get_slab_next(slab));
    else {
        if (where==0) write_free_slab(c,get_slab_next(slab));
        if (where==1) write_other_slab(c,get_slab_next(slab));
        if (where==2) write_full_slab(c,get_slab_next(slab));
    }
}
//checks based on pointer value
bool Cache::is_in_slab(void *slab, void *obj) {
    uint64 slab_start=(uint64 )slab;
    uint64 slab_end=(uint64)(slab_start+get_slab_blocks_num(slab)*4096);
    if ((uint64)obj>slab_start && (uint64)obj<slab_end) return true;
    return false;

}
//uses destructor, and constructor again
void Cache::erase_from_slab(void* c,void *slab, void *obj, bool do_dst_cst) {
    if (!c || !slab || !obj) {
        if (c) write_error(c,-2);
        return;
    }

    uint64* obj_start=(uint64*)((uint8*)obj-sizeof(int));
    if ((*(uint32*)obj_start)==0) {
        write_error(c,-1);
        return;
    }
    *obj_start=(uint32)0;

    if (do_dst_cst) {
        void (*dst)(void *) =get_dst(c);
        if (dst) dst(obj);
        void (*cst)(void *) =get_cst(c);
        if (cst) cst(obj);
    }
    write_slab_obj_num(slab,get_slab_obj_num(slab)-1);

}
//calls destructor for every object and marks as not existing
void Cache::erase_all_slab(void* c,void* slab){
    if (!c || !slab){
        if (c) write_error(c,-2);
        return;
    }
    int max_obj = (get_slab_blocks_num(slab) * block_size- sizeof(uint64) * 2 - sizeof(uint64 *))
                  / (get_size(c) + sizeof(int));
    //void(dst*)(void*)=get_dst(c);
    f_ptr dst=get_dst(c);
    for (int i = 0; i < max_obj; i++) {
        write_object_exists(c, slab, i, 0);
        if (dst) dst(get_object_from_slab(c,slab,i));
    }
}

int Cache::print_int(uint64 x){
    if (x==0) {_console::putBuffer('0'); return 0;}
    uint64 y=0; int zc=0;
    while (x>0){
        y*=10;
        y+=x%10;
        x=x/10;
        if (y==0) zc++;
    } int cnt=0;
    while (y>0){
        char c=y%10+'0';
        _console::putBuffer(c);
        y=y/10;
        cnt++;
    }
    for(int i=0;i<zc;i++) _console::putBuffer('0');
    return cnt+zc;
}
int Cache::print_str(const char*  str) {
    int i=0;
    while (str[i]!='\0') _console::putBuffer(str[i++]);
    return i;
}
int Cache::print_str(char *str) {
    return print_str((const char*) str);
}
void Cache::print_blank(int field,int i) {
    while ((i++)<field) _console::putBuffer(' ');
}

void Cache::print_slabs_info(uint64 *c) {
    uint64* free= get_free_slab(c),*full= get_full_slab(c),*other= get_other_slab(c);
    if (!free) print_str("free: nullptr\n");
    else {
        print_str("free:\n");
        while(free){
            print_int((uint64)free-(uint64)HEAP_START_ADDR);
            print_str(", ");
            print_int(get_slab_obj_num(free));
            print_str(" -> ");
            free= get_slab_next(free);
        }
        print_str("\n");
    }
    if (!other) print_str("other: nullptr\n");
    else {
        print_str("other:\n");
        while(other){
            print_int((uint64)other-(uint64)HEAP_START_ADDR);
            print_str(", ");
            print_int(get_slab_obj_num(other));
            print_str(" -> ");
            other= get_slab_next(other);
        }
        print_str("\n");
    }
    if (!full) print_str("full: nullptr\n");
    else {
        print_str("full:\n");
        while(full){
            print_int((uint64)full-(uint64)HEAP_START_ADDR);
            print_str(", ");
            print_int(get_slab_obj_num(full));
            print_str(" -> ");
            full= get_slab_next(full);
        }
        print_str("\n");
    }
}

void Cache::print_slabs_info(int c) {
    uint64* buff= get_mem_buff(c);
    print_slabs_info(buff);
}

