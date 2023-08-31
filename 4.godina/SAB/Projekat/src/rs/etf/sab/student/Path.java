package rs.etf.sab.student;

import java.util.ArrayList;
import java.util.List;

public class Path implements  Comparable<Path>{
    public Integer dist_sum;
    public List<Integer[]> path;
    public Path(Integer dist, List<Integer[]>path){
        this.dist_sum =dist;
        this.path=path;
    }
    public List<Integer[]> add(Integer id, Integer dist){
        List<Integer[]> new_path=new ArrayList<>(path);
        Integer[] pair={id,dist};
        new_path.add(pair);
        return new_path;
    }
    @Override
    public int compareTo(Path o) {
        return dist_sum -o.dist_sum;
    }
    public int last(){
        return path.get(path.size()-1)[0];
    }
    public void subDays(int days){
        while (days>0 && path.size()>1){
            Integer[] p=path.remove(path.size()-1);
            if (days>=p[1]){
                days-=p[1];
            } else {
                p[1]-=days;
                days=0;
                path.add(p);
            }
        }
    }
}