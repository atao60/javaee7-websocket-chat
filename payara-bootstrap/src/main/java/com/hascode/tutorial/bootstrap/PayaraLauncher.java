package com.hascode.tutorial.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import fish.payara.micro.BootstrapException;
import fish.payara.micro.PayaraMicro;
import fish.payara.micro.PayaraMicroRuntime;

public class PayaraLauncher {
	
	private static final Logger LOG = Logger.getLogger(currentClass().getName());
	
	private final String[] args;

    public static void main(final String[] args) throws Exception {
    	new PayaraLauncher(args).doLaunch();
    }
    
    private PayaraLauncher(String[] args) {
    	this.args = args;
    }
    
    private PayaraLauncher doLaunch() {

    	try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(warFileName()))
        {
        	PayaraMicroRuntime runtime = PayaraMicro.getInstance()
        			.setHttpPort(webPort())
        			.bootStrap();
        	runtime.deploy("test", contextPath(), is);
        	
        	
        } catch (IOException | BootstrapException ex)
        {
           LOG.log(Level.SEVERE, null, ex);
        } 
        return this;
    }
    
    private static Class<?> currentClass() {
    	return MethodHandles.lookup().lookupClass();
    }
    
    private String warFileName() {
    	return args[0];
    }
    
    private int webPort() {
    	return Integer.valueOf(args[1]);
    }
    
    private String contextPath() {
    	return args[2];
    }
    
}
