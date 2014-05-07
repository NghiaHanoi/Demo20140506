package org.wiperdog.test;

import java.lang.*;
import java.io.*;
import java.net.*;
import java.util.*;
import groovy.lang.*;
import groovy.util.*;

@org.junit.runner.RunWith(value=org.ops4j.pax.exam.junit.PaxExam.class) @org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy(value=org.ops4j.pax.exam.spi.reactors.PerClass.class) public class TestQuartzJob
  extends java.lang.Object  implements
    groovy.lang.GroovyObject {
public TestQuartzJob
() {}
public  groovy.lang.MetaClass getMetaClass() { return (groovy.lang.MetaClass)null;}
public  void setMetaClass(groovy.lang.MetaClass mc) { }
public  java.lang.Object invokeMethod(java.lang.String method, java.lang.Object arguments) { return null;}
public  java.lang.Object getProperty(java.lang.String property) { return null;}
public  void setProperty(java.lang.String property, java.lang.Object value) { }
@org.ops4j.pax.exam.Configuration() public  org.ops4j.pax.exam.Option[] config() { return (org.ops4j.pax.exam.Option[])null;}
@org.junit.Before() public  void prepare() { }
@org.junit.After() public  void finish() { }
@org.junit.Test() public  void testJobDone()throws java.lang.Exception { }
public class TestInterruptableJob
  extends java.lang.Object  implements
    org.quartz.InterruptableJob,    groovy.lang.GroovyObject {
public  groovy.lang.MetaClass getMetaClass() { return (groovy.lang.MetaClass)null;}
public  void setMetaClass(groovy.lang.MetaClass mc) { }
public  java.lang.Object invokeMethod(java.lang.String method, java.lang.Object arguments) { return null;}
public  java.lang.Object getProperty(java.lang.String property) { return null;}
public  void setProperty(java.lang.String property, java.lang.Object value) { }
public  void execute(org.quartz.JobExecutionContext arg0)throws org.quartz.JobExecutionException { }
public  void interrupt()throws org.quartz.UnableToInterruptJobException { }
}
public class TestJobListener
  extends java.lang.Object  implements
    org.quartz.JobListener,    groovy.lang.GroovyObject {
public  groovy.lang.MetaClass getMetaClass() { return (groovy.lang.MetaClass)null;}
public  void setMetaClass(groovy.lang.MetaClass mc) { }
public  java.lang.Object invokeMethod(java.lang.String method, java.lang.Object arguments) { return null;}
public  java.lang.Object getProperty(java.lang.String property) { return null;}
public  void setProperty(java.lang.String property, java.lang.Object value) { }
public  java.lang.String getName() { return (java.lang.String)null;}
public  void jobToBeExecuted(org.quartz.JobExecutionContext context) { }
public  void jobExecutionVetoed(org.quartz.JobExecutionContext context) { }
public  void jobWasExecuted(org.quartz.JobExecutionContext context, org.quartz.JobExecutionException jobException) { }
}
}
