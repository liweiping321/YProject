package com.road.fire.fighting.util.collision;

/**
 * @author jianpeng.zhang
 * @since 2017/5/15. 矢量
 */
public class Vector
{
    public float X;
    public float Y;

    public Vector()
    {
    }

    public Vector(float x, float y) {
        this.X = x;
        this.Y = y;
    }

    public static Vector fromPoint(Point p)
    {
        return fromPoint(p.x, p.y);
    }

    public static Vector fromPoint(int x, int y)
    {
        return new Vector((float) x, (float) y);
    }

    public float magnitude()
    {
        return (float) Math.sqrt(X * X + Y * Y);
    }

    public void normalize() {
        float magnitude = magnitude();
        X = X / magnitude;
        Y = Y / magnitude;
    }

    public Vector getNormalized()
    {
        float magnitude = magnitude();
        return new Vector(X / magnitude, Y / magnitude);
    }

    /**
     * 计算投影点
     */
    public float dotProduct(Vector vector) {
        return this.X * vector.X + this.Y * vector.Y;
    }

    public float distanceTo(Vector vector)
    {
        return (float) Math.sqrt(Math.pow(vector.X - this.X, 2) + Math.pow(vector.Y - this.Y, 2));
    }

    public static Point cast(Vector p)
    {
        return new Point((int) p.X, (int) p.Y);
    }

    public static Vector operatorAdd(Vector a, Vector b)
    {
        return new Vector(a.X + b.X, a.Y + b.Y);
    }

    public static Vector operatorReverse(Vector a)
    {
        return new Vector(-a.X, -a.Y);
    }

    public static Vector operatorCut(Vector a, Vector b)
    {
        return new Vector(a.X - b.X, a.Y - b.Y);
    }

    public static Vector operatorMuti(Vector a, float b)
    {
        return new Vector(a.X * b, a.Y * b);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Vector))
        {
            return false;
        }
        Vector v = (Vector)obj;
        return X == v.X && Y == v.Y;
    }

    /**
     * 向量平行即相等
     */
    public boolean equals(Vector v)
    {
        return X * v.Y == Y * v.X;
    }

    @Override
    public int hashCode()
    {
        return ((int)X) ^ ((int)Y);
    }

    @Override
    public String toString()
    {
        return X + ", " + Y;
    }

    public String toString(boolean rounded)
    {
        if (rounded)
        {
            return Math.round(X) + ", " + Math.round(Y);
        }
        else
        {
            return toString();
        }
    }
}
