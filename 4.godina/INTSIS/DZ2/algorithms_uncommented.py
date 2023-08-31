import math

class Algorithm:
    def get_algorithm_steps(self, tiles, variables, words):
        pass

class Backtrack(Algorithm):

    def __init__(self):
        self.table=[]
        self.n=0; self.m=0
        self.h=[]; self.v=[]
        self.indexes=dict()
        self.domain=dict()
        self.vars=[]
        self.word_index=dict()
        self.backtrack_domain=dict()

    def init_table(self,tiles):
        for row in tiles:
            table_row = []
            for field in row:
                if field:
                    table_row.append('/')
                else:
                    table_row.append('')
            self.table.append(table_row)
        self.n=len(self.table)
        self.m=len(self.table[0])

    def init_indexes(self,variables):
        for var in variables:
            num=int(var[:-1])
            x=num//self.m
            y=num%self.m
            self.indexes[var]=[x,y]

    def init_domain(self,vars,words):
        for v in vars.keys():
            list_of_words=[]
            for word in words:
                if vars[v]==len(word):
                    list_of_words.append(word)
            self.domain[v]=list_of_words

    def init_all(self,tiles,vars,words):
        self.vars = vars
        self.init_table(tiles);
        self.init_indexes(vars.keys())
        self.init_domain(vars, words)

    def get_algorithm_steps(self, tiles, vars, words):
        self.init_all(tiles,vars,words)
        sol=[]
        q=[i for i in vars.keys()]
        self.backtrack(q,sol,0,self.backtrack_domain)
        return sol

    def backtrack(self,vars,sol,n,domain=[]):
        if n==len(vars): return True
        next=vars[n]
        any_fit=False
        for i in range(len(self.domain[next])):
            word=self.domain[next][i]
            if self.fits(word,next):
                any_fit=True
                sol.append([next,i,self.domain])
                index_list=self.write_word(next,word)
                if self.backtrack(vars,sol,n+1):
                    return True
                sol.append([next,None,self.domain])
                self.erase_word(index_list)
        if not any_fit:
            sol.append([next,None,self.domain])
        return False

    def write_word(self,var,word):
        x,y=self.indexes[var]
        ret=[]
        for i in range(self.vars[var]):
            if self.table[x][y]=="":
                self.table[x][y]=word[i]
                ret.append([x,y])
            if var[-1]=='v': x+=1
            else: y+=1
        return ret

    def erase_word(self, index_list):
        for x,y in index_list:
            self.table[x][y]=""

    def fits(self,word,var):
        x,y=self.indexes[var]
        for c in word:
            if self.table[x][y]!=c and self.table[x][y]!="":
                return False
            if var[-1]=='v':
                if x==self.n: y+=1; x=0
                else: x+=1
            else:
                if y==self.m: x+=1; y=0
                else: y+=1
        return True

class ForwardChecking(Backtrack):

    def __init__(self):
        super().__init__()
        self.used=dict()

    def init_all(self,tiles,vars,words):
        super().init_all(tiles,vars,words)
        for v in self.vars:
            self.used[v]=False
        self.backtrack_domain=self.deep_copy(self.domain)

    def backtrack(self,vars,sol,n,domain):
        if n==0:
            self.arc_consistency(vars,domain)
        if n==len(vars): return True
        next=self.most_constrained(vars,domain)
        self.sort_var_domain(next,vars,domain)
        any_fit=False
        for i in range(len(domain[next])):
            word=domain[next][i]
            if self.fits(word,next):
                any_fit=True
                index_list=self.write_word(next,word)
                new_domain=self.deep_copy(domain)
                new_domain[next]=[word]
                self.fix(new_domain)
                self.used[next] = True

                # if not self.arc_consistency(vars,new_domain):
                #     sol.append([next, None, domain])
                #     self.used[next]=False
                #     self.erase_word(index_list)
                #     continue

                sol.append([next, new_domain[next].index(word), new_domain])
                if self.backtrack(vars,sol,n+1,new_domain):
                    return True

                sol.append([next,None,domain])
                self.used[next] = False
                self.erase_word(index_list)
        if not any_fit:
            sol.append([next, None, domain])
        return False

    def arc_consistency(self,vars,domains):
        return True

    def most_constrained(self,vars,domain):
        chosen=0
        min_constraints=math.inf
        for i in range(len(vars)):
            if self.used[vars[i]]: continue
            l=len(domain[vars[i]])
            if l<min_constraints or (l==min_constraints and self.vars[vars[i]]>self.vars[chosen]):
                chosen=vars[i]
                min_constraints=l
        return chosen

    def sort_var_domain(self,var,vars,domains):
        help_dict=dict()
        domain=domains[var]
        for i in range(len(domain)):
            help_dict[domain[i]]=0
            before=0
            for v in vars:
                if self.used[v] or not self.intersection(v,var) or v==var: continue
                for val in domains[v]:
                    if self.fits(val,v): before+=1
            ind=self.write_word(var,domain[i])
            after=0
            for v in vars:
                if self.used[v] or not self.intersection(v,var) or v==var: continue
                for val in domains[v]:
                    if self.fits(val,v): after+=1
            self.erase_word(ind)
            help_dict[domain[i]]=before-after

        for i in range(len(domain)):
            for j in range(i+1,len(domain)):
                if help_dict[domain[i]]>help_dict[domain[j]]:
                    t=domain[i]
                    domain[i]=domain[j]
                    domain[j]=t

    def intersection(self,var1,var2):
        if var1[-1]=="v":
            t=var1; var1=var2; var2=t
        x1,y11=self.indexes[var1]
        x21,y2=self.indexes[var2]
        y12=y11+self.vars[var1]
        x22=x21+self.vars[var2]
        return x1>=x21 and x1<=x22 and y2>=y11 and y2<=y12

    def deep_copy(self,domain):
        new_domain=dict()
        for key in domain:
            new_domain[key]= [i for i in domain[key]]
        return new_domain

    def fix(self,domain):
        for key in domain:
            to_kick=[]
            for i in range(len(domain[key])-1,-1,-1):
                if not self.fits(domain[key][i],key):
                    to_kick.append(i)
            for i in to_kick:
                domain[key].pop(i)

class ArcConsistency(ForwardChecking):

    def arc_consistency(self,vars,domains):
        arc_list=self.get_arcs(vars)
        while len(arc_list):
            x,y=arc_list.pop(0)
            x_del=[]
            for x_val in domains[x]:
                no_y_value= True
                for y_val in domains[y]:
                    ind=self.write_word(x,x_val)
                    flag=self.fits(y_val,y)
                    self.erase_word(ind)
                    if flag:
                        no_y_value=False
                        break
                if no_y_value:
                    x_del.append(x_val)
            if len(x_del)>0:
                domains[x]=[i for i in domains[x] if i not in x_del]
                if len(domains[x])==0:
                    return False
                for var in vars:
                    if var!=x and self.intersection(x,var):
                        arc_list.append([var,x])
        return True

    def get_arcs(self,vars):
        arc_list=[]
        for var in vars:
            for var2 in vars:
                if self.intersection(var, var2):
                    arc_list.append([var,var2])
        return arc_list