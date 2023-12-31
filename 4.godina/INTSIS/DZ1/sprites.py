import math
import random

import pygame
import os
import config
from itertools import permutations
from paths import Path
from paths import PathH
from paths import prim
from heap import MinHeap


class BaseSprite(pygame.sprite.Sprite):
    images = dict()

    def __init__(self, x, y, file_name, transparent_color=None, wid=config.SPRITE_SIZE, hei=config.SPRITE_SIZE):
        pygame.sprite.Sprite.__init__(self)
        if file_name in BaseSprite.images:
            self.image = BaseSprite.images[file_name]
        else:
            self.image = pygame.image.load(os.path.join(config.IMG_FOLDER, file_name)).convert()
            self.image = pygame.transform.scale(self.image, (wid, hei))
            BaseSprite.images[file_name] = self.image
        # making the image transparent (if needed)
        if transparent_color:
            self.image.set_colorkey(transparent_color)
        self.rect = self.image.get_rect()
        self.rect.topleft = (x, y)


class Surface(BaseSprite):
    def __init__(self):
        super(Surface, self).__init__(0, 0, 'terrain.png', None, config.WIDTH, config.HEIGHT)


class Coin(BaseSprite):
    def __init__(self, x, y, ident):
        self.ident = ident
        super(Coin, self).__init__(x, y, 'coin.png', config.DARK_GREEN)

    def get_ident(self):
        return self.ident

    def position(self):
        return self.rect.x, self.rect.y

    def draw(self, screen):
        text = config.COIN_FONT.render(f'{self.ident}', True, config.BLACK)
        text_rect = text.get_rect(center=self.rect.center)
        screen.blit(text, text_rect)


class CollectedCoin(BaseSprite):
    def __init__(self, coin):
        self.ident = coin.ident
        super(CollectedCoin, self).__init__(coin.rect.x, coin.rect.y, 'collected_coin.png', config.DARK_GREEN)

    def draw(self, screen):
        text = config.COIN_FONT.render(f'{self.ident}', True, config.RED)
        text_rect = text.get_rect(center=self.rect.center)
        screen.blit(text, text_rect)


class Agent(BaseSprite):
    def __init__(self, x, y, file_name):
        super(Agent, self).__init__(x, y, file_name, config.DARK_GREEN)
        self.x = self.rect.x
        self.y = self.rect.y
        self.step = None
        self.travelling = False
        self.destinationX = 0
        self.destinationY = 0

    def set_destination(self, x, y):
        self.destinationX = x
        self.destinationY = y
        self.step = [self.destinationX - self.x, self.destinationY - self.y]
        magnitude = math.sqrt(self.step[0] ** 2 + self.step[1] ** 2)
        self.step[0] /= magnitude
        self.step[1] /= magnitude
        self.step[0] *= config.TRAVEL_SPEED
        self.step[1] *= config.TRAVEL_SPEED
        self.travelling = True

    def move_one_step(self):
        if not self.travelling:
            return
        self.x += self.step[0]
        self.y += self.step[1]
        self.rect.x = self.x
        self.rect.y = self.y
        if abs(self.x - self.destinationX) < abs(self.step[0]) and abs(self.y - self.destinationY) < abs(self.step[1]):
            self.rect.x = self.destinationX
            self.rect.y = self.destinationY
            self.x = self.destinationX
            self.y = self.destinationY
            self.travelling = False

    def is_travelling(self):
        return self.travelling

    def place_to(self, position):
        self.x = self.destinationX = self.rect.x = position[0]
        self.y = self.destinationX = self.rect.y = position[1]

    # coin_distance - cost matrix
    # return value - list of coin identifiers (containing 0 as first and last element, as well)
    def get_agent_path(self, coin_distance):
        pass


class ExampleAgent(Agent):
    def __init__(self, x, y, file_name):
        super().__init__(x, y, file_name)

    def get_agent_path(self, coin_distance):
        path = [i for i in range(1, len(coin_distance))]
        random.shuffle(path)
        return [0] + path + [0]


class Aki(Agent):
    def __init__(self, x, y, file_name):
        super().__init__(x, y, file_name)

    def get_agent_path(self, coin_distance):
        curr = 0; path = [0];
        count = 1
        num = len(coin_distance)
        visited = [False] * num
        visited[curr] = 1
        while count < num:
            minpath = math.inf
            next = curr
            for i in range(0, len(coin_distance[curr])):
                if coin_distance[curr][i] < minpath and not visited[i]:
                    next = i;
                    minpath = coin_distance[curr][i]
            path.append(next)
            count += 1
            visited[next] = True
            curr = next
        path.append(0)
        return path


class Jocke(Agent):
    def __init__(self, x, y, file_name):
        super().__init__(x, y, file_name)

    def get_agent_path(self, coin_distance):
        perms = permutations([i for i in range(1, len(coin_distance))])
        best_path = []
        best_distance = math.inf

        for perm in perms:
            curr_path = [0]
            curr_coin = 0
            curr_distance = 0
            for coin in perm:
                curr_distance += coin_distance[curr_coin][coin]
                curr_coin = coin
                curr_path.append(curr_coin)
                if best_distance < curr_distance: break
            curr_distance += coin_distance[curr_coin][0]
            if best_distance > curr_distance:
                best_path = curr_path
                best_distance = curr_distance
        best_path.append(0)
        return best_path


class Uki(Agent):
    def __init__(self, x, y, file_name):
        super().__init__(x, y, file_name)

    def get_agent_path(self, coin_distance):
        num = len(coin_distance)
        paths = MinHeap()
        paths.insert_path(Path([0], [i for i in range(num)], 0))
        while True:
            curr_path = paths.get_path()
            if len(curr_path.path) == num + 1: break
            for i in curr_path.visit:
                if len(curr_path.path) != num and i == 0: continue
                new_path = Path(curr_path.path, curr_path.visit, curr_path.dist)
                new_path.dist += coin_distance[curr_path.path[-1]][i]
                new_path.visit.remove(i)
                new_path.path.append(i)
                paths.insert_path(new_path)
        return curr_path.path


class Micko(Agent):
    def __init__(self, x, y, file_name):
        super().__init__(x, y, file_name)

    def get_agent_path(self,coin_distance):
        mst = dict()
        num = len(coin_distance)
        paths = MinHeap()
        paths.insert_path(PathH([0], [i for i in range(num)], 0, 0))
        while True:
            curr_path = paths.get_path()
            if len(curr_path.path) == num + 1: break
            for i in curr_path.visit:
                if len(curr_path.path) != num and i == 0: continue

                v = ""
                vv = []
                for j in curr_path.visit:
                    if j == i: continue
                    v = v + str(j) + ","
                    vv.append(j)
                if not v in mst: mst[v] = prim(coin_distance, vv)

                new_path = PathH(curr_path.path, curr_path.visit, curr_path.dist, mst[v])
                new_path.dist += coin_distance[curr_path.path[-1]][i]
                new_path.visit.remove(i)
                new_path.path.append(i)
                paths.insert_path(new_path)

        return curr_path.path
