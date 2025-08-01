package com.pu;

public class Vector3 {
    float x,y,z;
    Vector3(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
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
    public static Vector3 add(Vector3 a,Vector3 b){
        return new Vector3(a.x+b.x,a.y+b.y,a.z+b.z);
    }
    public static Vector3 mul(Vector3 a,float s){
        return new Vector3(a.x*s,a.y*s,a.z*s);
    }
}
