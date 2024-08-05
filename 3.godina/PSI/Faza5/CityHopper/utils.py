# Authors : Petar Vasiljevic 0007/2020

from .models import Town, Road, Shortestpath
from django.db import connections
import copy

def get_ind(i, j):
    return str(i) + "_" + str(j)
def calc_all_shortest_paths(request):
    towns = Town.objects.all()
    roads = Road.objects.all()

    n_towns = len(towns) + 1
    INF = 30000000

    dist = {}
    for town_i in towns:
        for town_j in towns:
            dist[get_ind(town_i.pk, town_j.pk)] = INF

    for road in roads:
        dist[get_ind(road.idto1.idto, road.idto2.idto)] = road.length

    for k in [town.pk for town in towns]:
        for i in [town.pk for town in towns]:
            for j in [town.pk for town in towns]:
                if dist[get_ind(i, j)] > dist[get_ind(i, k)] + dist[get_ind(k, j)]:
                    dist[get_ind(i, j)] = dist[get_ind(i, k)] + dist[get_ind(k, j)]

    request.session['shortest_path_matrix'] = dist
    return dist

def calc_shortest_paths(request):
    """
    Calculate shortest path between each pair of cities and insert them in Shortest Path table
    @param request: HttpRequest
    @return: HttpResponseRedirect to the homepage
    """
    Shortestpath.objects.all().delete()
    dist = calc_all_shortest_paths(request)

    connection = connections['default']
    cursor = connection.cursor()

    sql_query = "INSERT INTO shortestpath (idSP, shortest_path, idTo1, idTo2) VALUES "

    cnt = 1
    for i in dist.keys():
        src, dst = i.split("_")
        length = dist[i]

        values = "({}, {}, {}, {})".format(cnt, length, src, dst)
        sql_query += values + ", "
        cnt += 1

    sql_query = sql_query[:-2]

    cursor.execute(sql_query)
    # connection.commit()

    cursor.close()
