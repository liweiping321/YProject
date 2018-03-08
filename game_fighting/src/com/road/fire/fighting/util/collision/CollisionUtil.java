package com.road.fire.fighting.util.collision;

import java.util.List;

/**
 * @author jianpeng.zhang
 * @since 2017/5/16.
 * 分离轴定理检测碰撞
 */
public class CollisionUtil
{
    /**
     * 两个凸多边形的碰撞检测
     * @param pointsA A凸多边形的所有的点，必须逆时钟或顺时钟存储
     * @param pointsB B凸多边形的所有的点，必须逆时钟或顺时钟存储
     * @return 碰撞返回真
     */
    public static boolean checkPolygonCollision(List<Point> pointsA, List<Point> pointsB)
    {
        Polygon polygonA = new Polygon(pointsA);
        Polygon polygonB = new Polygon(pointsB);
        return checkPolygonCollision(polygonA, polygonB);
    }

    /**
     * 检测凸多边形是否碰撞
     * @return 碰撞返回真
     */
    public static boolean checkPolygonCollision(Polygon polygonA, Polygon polygonB)
    {
        int edgeCountA = polygonA.getEdges().size();
        int edgeCountB = polygonB.getEdges().size();

        // 多边行的边大于等于3
        if (polygonA.getPoints().size() < 3 || polygonA.getPoints().size() < 3)
        {
            return false;
        }

        Vector edge;

        // Loop through all the edges of both polygons
        for (int edgeIndex = 0; edgeIndex < edgeCountA + edgeCountB; edgeIndex++)
        {
            if (edgeIndex < edgeCountA)
            {
                edge = polygonA.getEdges().get(edgeIndex);
            }
            else
            {
                edge = polygonB.getEdges().get(edgeIndex - edgeCountA);
            }

            // ===== 1. Find if the polygons are currently intersecting =====
            // Find the axis perpendicular to the current edge
            Vector axis = new Vector(-edge.Y, edge.X);
            axis.normalize();

            // Find the projection of the polygon on the current axis
            float minA, minB, maxA, maxB;
            float[] A = projectPolygon(axis, polygonA);
            minA = A[0];
            maxA = A[1];

            float[] B = projectPolygon(axis, polygonB);
            minB = B[0];
            maxB = B[1];

            // Check if the polygon projections are currentlty intersecting
            if (IntervalDistance(minA, maxA, minB, maxB) > 0)
                return false;
        }
        return true;
    }

    /**
     * 检测凸多边形和圆形是否碰撞
     * @return 碰撞返回真
     */
    public static boolean checkCirclePolygonCollision(Circle circle, Polygon polygon)
    {
        int edgeCount = polygon.getEdges().size();
        if (circle.radius <= 0 || polygon.getPoints().size() < 3)
        {
            return false;
        }

        double dist = Double.MAX_VALUE, distance;
        Vector point = null;
        // 找出多边形离圆心最进的点，与圆心的连线作为园的投影的轴
        for (Vector vector : polygon.getPoints())
        {
            distance =Math.pow(vector.X - circle.x, 2) + Math.pow(vector.Y - circle.y, 2);
            if (distance < dist)
            {
                dist = distance;
                point = vector;
            }
        }
        circle.buildEdges(point);


        Vector edge;

        // Loop through all the edges of both polygons
        for (int edgeIndex = 0; edgeIndex < edgeCount + 1; edgeIndex++)
        {
            Vector axis;
            if (edgeIndex < 1)
            {
                axis = circle.getEdges().get(edgeIndex);
            }
            else
            {
                edge = polygon.getEdges().get(edgeIndex - 1);
                axis = new Vector(-edge.Y, edge.X);
            }

            // ===== 1. Find if the polygons are currently intersecting =====
            // Find the axis perpendicular to the current edge
            // Vector axis = new Vector(-edge.Y, edge.X);
            axis.normalize();

            // Find the projection of the polygon on the current axis
            float minA, minB, maxA, maxB;
            float[] A = projectPolygon(axis, circle);
            minA = A[0];
            maxA = A[1];

            float[] B = projectPolygon(axis, polygon);
            minB = B[0];
            maxB = B[1];

            // Check if the polygon projections are currentlty intersecting
            if (IntervalDistance(minA, maxA, minB, maxB) > 0)
                return false;
        }
        return true;
    }

    /**
     * 检测两个圆是否相交
     * @return 碰撞返回真
     */
    public static boolean checkCircleCollision(Circle circleA, Circle circleB)
    {
        int maxWidth = circleA.radius + circleB.radius;
        int x = circleA.x - circleB.x;
        int y = circleA.y - circleB.y;
        return Math.abs(x) < maxWidth && Math.abs(y) < maxWidth && Math.pow(x, 2) + Math.pow(y, 2) <= Math
                .pow(maxWidth, 2);
    }

    /**
     * Calculate the projection of a polygon on an axis and returns it as a [min, max] interval
     */
    private static float[] projectPolygon(Vector axis, Polygon polygon)
    {
        // To project a point on an axis use the dot product
        float min, max, d;
        min = max = d = axis.dotProduct(polygon.getPoints().get(0));

        for (int i = 0; i < polygon.getPoints().size(); i++)
        {
            d = polygon.getPoints().get(i).dotProduct(axis);
            if (d < min)
            {
                min = d;
            }
            else
            {
                if (d > max)
                {
                    max = d;
                }
            }
        }
        return new float[]{min, max};
    }

    /**
     * Calculate the projection of a polygon on an axis and returns it as a [min, max] interval
     */
    private static float[] projectPolygon(Vector axis, Circle circle)
    {
        // To project a point on an axis use the dot product
        float min, max;
        min = max = axis.dotProduct(Vector.fromPoint(circle.x, circle.y));
        min -= circle.radius;
        max += circle.radius;
        return new float[]{min, max};
    }

    /**
     * Calculate the distance between [minA, maxA] and [minB, maxB]
     * The distance will be negative if the intervals overlap
     * 检测两条投影是否重合
     */
    private static float IntervalDistance(float minA, float maxA, float minB, float maxB)
    {
        if (minA < minB)
        {
            return minB - maxA;
        }
        else
        {
            return minA - maxB;
        }
    }

    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();
        Polygon p = new Polygon();
        p.getPoints().add(new Vector(0, 0));
        p.getPoints().add(new Vector(5, 0));
        p.getPoints().add(new Vector(5, 5));
        p.getPoints().add(new Vector(0, 5));
        p.buildEdges();

        Polygon p1 = new Polygon();
        p1.getPoints().add(new Vector(8, 0));
        p1.getPoints().add(new Vector(0, 8));
        p1.getPoints().add(new Vector(8, 8));
        p1.getPoints().add(new Vector(16, 0));
        p1.buildEdges();
        for (int i =0; i < 500000; i++)
        {
        checkPolygonCollision(p, p1);
        }
        System.out.println(System.currentTimeMillis() - time);


        p1.Offset(3, 3);
        System.out.println(checkPolygonCollision(p, p1));
        p.Offset(1, 3);
        System.out.println(checkPolygonCollision(p, p1));


        // Polygon p2 = new Polygon();
        // p2.getPoints().add(new Vector(12, -3));
        // p2.getPoints().add(new Vector(11, 8));
        // p2.getPoints().add(new Vector(16, 15));
        // p2.buildEdges();
        // Circle circle = new Circle(5, 5, 7);
        // System.out.println(checkCirclePolygonCollision(circle, p2));
        // p2.Offset(1, 1);
        // System.out.println(checkCirclePolygonCollision(circle, p2));
        //
        //
        // Polygon p3 = new Polygon();
        // p3.getPoints().add(new Vector(11, 8));
        // p3.getPoints().add(new Vector(15, 15));
        // p3.getPoints().add(new Vector(13, 20));
        // p3.buildEdges();
        // System.out.println(checkCirclePolygonCollision(circle, p3));
        // p3.Offset(3, 3);
        // System.out.println(checkCirclePolygonCollision(circle, p3));
        //
        //
        // Circle circle1 = new Circle(5, 5, 6);
        // Circle circle2 = new Circle(12, 6, 1);
        // System.out.println(checkCircleCollision(circle1, circle2));

    }
}
