package com.example.demo;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class MyArrayList<E>  extends ArrayList<E> implements Externalizable {

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
