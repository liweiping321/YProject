package com.road.fire.fighting.util.collision;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/5/16.
 */
public class Circle
{
    public int x;
    public int y;
    public int radius;

    private List<Vector> edges = new ArrayList<>();

    public Circle(int x, int y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void buildEdges(Vector point)
    {
        edges.clear();
        if (point == null)
        {
            return;
        }
        edges.add(Vector.operatorCut(Vector.fromPoint(x, y), point));
    }

    public List<Vector> getEdges()
    {
        return edges;
    }
}
