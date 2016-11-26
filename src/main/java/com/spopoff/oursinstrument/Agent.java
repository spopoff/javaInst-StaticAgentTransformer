/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spopoff.oursinstrument;

import java.lang.instrument.Instrumentation;
/**
 *
 * @author mrwc1264
 */
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Executing premain.........");
        inst.addTransformer(new OursTransformer());
    }
    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("Executing agentmain.........");
        inst.addTransformer(new OursTransformer(), true);
        ApplicationOne une = ApplicationOne.getInstance();
        une.setInstrumentation(inst);
    }
}
