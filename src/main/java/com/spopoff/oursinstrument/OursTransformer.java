/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spopoff.oursinstrument;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
/**
 *
 * @author mrwc1264
 */
public class OursTransformer implements ClassFileTransformer{

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        byte[] byteCode = classfileBuffer;
        boolean found = false;
        System.out.println("Instrumenting class="+className);
        if(className.equals("org/glassfish/grizzly/http/server/Request")){
            System.out.println("Instrumenting.Found.Request");
        }
        if (className.equals("org/glassfish/grizzly/http/server/HttpHandler")) {
            System.out.println("Instrumenting.HttpHandler0");
            found = true;
            ClassPool classPool = null;
            try{
                System.out.println("Instrumenting.HttpHandler1");
                classPool = ClassPool.getDefault();
            }catch(Exception e){
                System.err.println("Exception ClassPool: " + e);
            }
            if(classPool==null){
                System.err.println("ClassPool vide");
            }else{
                    try{
                        System.out.println("Instrumenting.HttpHandler4");
                        CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(
                                                        classfileBuffer));
                        System.out.println("Instrumenting.HttpHandler5");
                        CtMethod[] methods = ctClass.getDeclaredMethods();
                        System.out.println("Instrumenting.HttpHandler methods="+methods.length);
                        for (CtMethod method : methods) {
                            System.out.println("methode="+method.getName());
                            if(method.getName().equals("doHandle")){
                                method.insertBefore("System.out.println(\"getRequestURL= \" + request.getRequestURL().toString());");
                                method.insertBefore("if(!request.getServerName().equals(\"localhost\")) throw new Exception(\"pas de proxy\");");
                                byteCode = ctClass.toBytecode();
                                ctClass.detach();
                                break;
                            }
                        }
                    }catch(Exception  ex){
                        System.err.println("Exception: " + ex);
                    }
                
            }
        }
        if(found){
            System.out.println("Instrumenting.HttpHandler99");
        }
        return byteCode;
    }
    
}
