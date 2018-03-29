package com.curioustake.sftm;

import java.lang.reflect.Method;

/**
 * Main for executing code in SFTM
 */
public class SftmMain
{
    public static void main( String[] args )
    {
        System.out.println( "Execute => SftmMain" );

        try {
            final String className = args[0];
            Class clazz =  Class.forName(className);
            Method method = clazz.getMethod("invoke", new Class[]{String[].class});
            method.invoke(clazz.newInstance(), new Object[]{args});
        } catch (final Exception e) {
            System.out.println("Something got screwed in Main");
            e.printStackTrace(System.out);
        }
    }
}
