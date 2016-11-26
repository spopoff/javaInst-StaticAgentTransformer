/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spopoff.oursinstrument;

import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mrwc1264
 */
public class ApplicationOne {
    private Instrumentation inst;
    private static class ApplicationOneHolder {
        private final static ApplicationOne instance = new ApplicationOne();
    }
    public static ApplicationOne getInstance(){
        return ApplicationOneHolder.instance;
    }
    public void setInstrumentation(Instrumentation inst){
        this.inst = inst;
    }
    public void instrumentClass(String laClass){
        System.out.println("traite class="+laClass);
        Class[] lesClass = new Class[1];
        Class uneClass = null;
        try {
            uneClass = Class.forName(laClass);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(uneClass==null){
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, "classe vide pas dans loader ?");
            return;
        }
        System.out.println("retransform class="+uneClass.toGenericString());
        lesClass[0] = uneClass;
        if(inst==null){
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, "intrumentation vide");
            return;
        }
        try {
            inst.retransformClasses(lesClass);
        } catch (Exception ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
