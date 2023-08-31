
class MinHeap:
    def __init__(self):
        self.h = []

    def get_path(self):
        i = 0
        self.h[-1],self.h[0]=self.h[0],self.h[-1]
        ret = self.h.pop()
        if len(self.h) == 1: return ret
        if len(self.h) == 2 and self.h[0] > self.h[1]:
            self.h[1], self.h[0] = self.h[0], self.h[1]
        while i < len(self.h) and ((len(self.h) > 2 * i + 1 and self.h[i] > self.h[2 * i + 1]) or (
                len(self.h) > 2 * i + 2 and self.h[i] > self.h[2 * i + 2])):
            if len(self.h) == 2 * i + 2 or self.h[2 * i + 1] < self.h[2 * i + 2]:
                self.h[i], self.h[2 * i + 1] = self.h[2 * i + 1], self.h[i]
                i = 2 * i + 1
            else:
                self.h[i], self.h[2 * i + 2] = self.h[2 * i + 2], self.h[i]
                i = 2 * i + 2
        return ret

    def insert_path(self, path):
        i = len(self.h)+1
        self.h.append(path)
        while i // 2 > 0 and self.h[i // 2 - 1] > self.h[i - 1]:
            self.h[i-1], self.h[i//2 - 1] = self.h[i//2 - 1], self.h[i-1]
            i = i // 2
