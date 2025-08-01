package com.pu;

public class Vector3 {
    float x,y,z,l;
    Vector3(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        CalLength();
    }
    public void CalLength(){
        this.l = (float)Math.sqrt(x*x+y*y+z*z);
    }

    public  Vector3 Sub(Vector3 b){
        return new Vector3(x-b.x,y-b.y,z-b.z);
    }
    public  Vector3 Div(float s){
        if(s==0){
            throw new ArithmeticException("Division by zero");
        }
        return new Vector3(x/s,y/s,z/s);
    }
    public  Vector3 Add(Vector3... a){
        float x = this.x,y=this.y,z=this.z;
        for (Vector3 v : a) {
            x+=v.x;
            y+=v.y;
            z+=v.z;
        }
        return new Vector3(x,y,z);
    }
    public  Vector3 Mul(float s){
        return new Vector3(x*s,y*s,z*s);
    }
    public Vector3 Normalize(){
        return new Vector3(x/l,y/l,z/l);
    }
    public String toString(){
        return String.format("(%f,%f,%f)",x,y,z);
    }







    public static Vector3 sub(Vector3 a,Vector3 b){
        return new Vector3(a.x-b.x,a.y-b.y,a.z-b.z);
    }
    public static Vector3 div(Vector3 v,float s){
        if(s==0){
            throw new ArithmeticException("Division by zero");
        }
        return new Vector3(v.x/s,v.y/s,v.z/s);
    }
    public static Vector3 add(Vector3... a){
        float x = 0,y=0,z=0;
        for (Vector3 v : a) {
            x+=v.x;
            y+=v.y;
            z+=v.z;
        }
        return new Vector3(x,y,z);
    }
    public static Vector3 mul(Vector3 a,float s){
        return new Vector3(a.x*s,a.y*s,a.z*s);
    }
    public static Vector3 cross(Vector3 a,Vector3 b){
        //return new Vector3(a.x*b.y-a.y*b.x,a.z*b.x-a.x*b.z,a.y*b.z-a.z*b.y);
        return new Vector3(a.y*b.z-a.z,a.z*b.x-a.z*b.z,a.x*b.y-a.y*b.x);
    }
    public static float dot(Vector3 a, Vector3 b){
        return a.x*b.x+a.y*b.y+a.z*b.z;
    }
    /*
    public static Vector3 rotate(Vector3 v,Vector3 axis,float rad){
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);
        return v.Add(Vector3.cross(axis.Mul(sin),v),Vector3.cross(axis.Mul(1-cos),Vector3.cross(axis,v)));
    }*/
    public static Vector3 rotate(Vector3 v, Vector3 axis, float rad){
        float sin = (float) Math.sin(rad);
        float cos = (float) Math.cos(rad);
        float oneMinusCos = 1 - cos;
        Vector3 term1 = v.Mul(cos);
        Vector3 term2 = Vector3.cross(axis.Mul(sin), v);
        Vector3 term3 = axis.Mul(Vector3.dot(axis, v) * oneMinusCos);

        return term1.Add(term2).Add(term3);
    }

}
