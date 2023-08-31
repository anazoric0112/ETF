from heap import MinHeap

class Path:
    def __init__(self,path,visit,distance):
        self.path=[i for i in path]
        self.visit=[i for i in visit]
        self.dist=distance

    def __str__(self):
        return self.path.__str__() + " " + self.visit.__str__() + " " + str(self.dist)

    def __gt__(self, other):
        if self.dist>other.dist:
            return  True
        elif self.dist==other.dist:
            if len(self.path)<len(other.path):
                return True
            elif len(self.path) == len(other.path):
                j=0
                while self.path[j]==other.path[j]: j+=1;
                if self.path[j] > other.path[j]: return True
        return False

    def __lt__(self, other):
        if self.dist < other.dist:
            return True
        elif self.dist == other.dist:
            if len(self.path) > len(other.path):
                return True
            elif len(self.path) == len(other.path):
                j = 0
                while self.path[j] == other.path[j]: j += 1;
                if self.path[j] < other.path[j]: return True
        return False


class PathH (Path):
    def __init__(self,path,visit,distance,heuristic):
        super().__init__(path,visit,distance)
        self.h=heuristic

    def __gt__(self, other):
        if self.dist + self.h > other.dist + other.h:
            return True
        elif self.dist + self.h == other.dist + other.h:
            if len(self.path) < len(other.path):
                return True
            elif len(self.path) == len(other.path):
                j = 0
                while self.path[j] == other.path[j]:
                    j += 1
                    if j==len(self.path): return True
                if self.path[j] > other.path[j]: return True
        return False

    def __lt__(self, other):
        if self.dist + self.h < other.dist + other.h:
            return True
        elif self.dist + self.h == other.dist + other.h:
            if len(self.path) > len(other.path):
                return True
            elif len(self.path) == len(other.path):
                j = 0
                while self.path[j] == other.path[j]:
                    j += 1
                    if j == len(self.path): return True
                if self.path[j] < other.path[j]: return True
        return False

    def __str__(self):
        return Path.__str__(self) + ", " + str(self.h)


class Coin:
    def __init__(self, c, dist):
        self.c=c
        self.dist=dist
    def __lt__(self, other):
        return self.dist<other.dist
    def __gt__(self, other):
        return self.dist>other.dist
    def __str__(self):
        return f"{self.c}-{self.dist}"

def prim(coin_distance, coins):
    if len(coins)==0: return 0
    visited=[False]*(max(coins)+1)
    num=len(coins)
    count=0
    dist=0
    branches=MinHeap()
    branches.insert_path(Coin(coins[0],0))
    while count<num:
        curr=branches.get_path()
        if visited[curr.c]: continue
        visited[curr.c]=True
        count+=1
        dist+=curr.dist
        for coin in coins:
            if visited[coin]: continue
            c=Coin(coin,coin_distance[curr.c][coin])
            branches.insert_path(c)
    return dist