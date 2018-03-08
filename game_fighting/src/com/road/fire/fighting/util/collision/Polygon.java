package com.road.fire.fighting.util.collision;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jianpeng.zhang
 * @since 2017/5/16.
 */
public class Polygon
{

    private List<Vector> points = new ArrayList<>();
    private List<Vector> edges = new ArrayList<>();

    /**
     * 点必须是顺时钟或逆时钟排序
     */
    public Polygon(List<Point> points1)
    {
        if (points != null)
        {
            for (Point point : points1)
            {
                points.add(Vector.fromPoint(point));
            }
            buildEdges();
        }
    }

    public Polygon()
    {

    }

    public void buildEdges()
    {
        Vector p1;
        Vector p2;
        Vector p3;
        edges.clear();
        boolean flag = true;
        for (int i = 0; i < points.size(); i++)
        {
            p1 = points.get(i);
            if (i + 1 >= points.size())
            {
                p2 = points.get(0);
            }
            else
            {
                p2 = points.get(i + 1);
            }

            // 过滤掉平行的边，以提高效率
            p3 = Vector.operatorCut(p2, p1);
            flag = true;
            for (Vector vector : edges)
            {
                if (vector.equals(p3))
                {
                    flag = false;
                    break;
                }
            }
            if (flag)
                edges.add(p3);
        }
    }

    // 边的矢量
    public List<Vector> getEdges()
    {
        return edges;
    }

    public List<Vector> getPoints()
    {
        return points;
    }

    public Vector Center()
    {
        float totalX = 0;
        float totalY = 0;
        for (int i = 0; i < points.size(); i++)
        {
            totalX += points.get(i).X;
            totalY += points.get(i).Y;
        }
        return new Vector(totalX / (float) points.size(), totalY / (float) points.size());
    }

    public void Offset(Vector v)
    {
        Offset(v.X, v.Y);
    }

    public void Offset(float x, float y)
    {
        for (int i = 0; i < points.size(); i++)
        {
            Vector p = points.get(i);
            points.set(i, new Vector(p.X + x, p.Y + y));
        }
    }

    @Override
    public String toString()
    {
        String result = "";
        for (int i = 0; i < points.size(); i++)
        {
            if (!Objects.equals(result, ""))
                result += " ";
            result += "{" + points.get(i).toString(true) + "}";
        }

        return result;
    }
}