package net.oneandone.ejbcdiunit.relbuilder.code.closure;

import java.util.Set;

public class InitialConfiguration {

    public Class testClass;

    /**
     * classes initially defined to be started by Weld-SE. Producers and Themselves have priority over others
     * They get scanned for ejb-cdi-unit2-Annotations
     */
    public Set<Class<?>> initialClasses;
    /**
     * Classes to be included as testclasses. These are started. They themselves and their producers get priority over
     * SuT.
     */
    public Set<Class<?>> testClasses;
    /**
     * All Classes in the classpath of these classes are to be included as testclasses.
     * These are started. They themselves and their producers get priority over
     * SuT.
     */
    public Set<Class<?>> testClasspaths;

    /**
     * Classes to be declared as beans to CDI, if there is no collision with classes produced by the Testconfiguration.
     */
    public Set<Class<?>> suTClasses;
    /**
     * Classes of the packages of these classes are included if necessary to support Injects
     */
    public Set<Class<?>> suTPackages; // managed bean classes in packages of these classes will be created if necessary
    /**
     * Classes of the classpaths of these classes are included if necessary to support Injects
     */
    public Set<Class<?>> suTClasspath; // managed bean classes in classpath of these classes will be created if necessary

    /**
     * Classes which should be enabled Alternatives (makes only sense if they are annotated as @Alternative) or
     * stereotype-annoations which should define enabled Alternatives (makes only sense if they are annotated as @Alternative)
     */
    public Set<Class<?>> enabledAlternatives;

    /**
     * Classes which must be excluded if they would occure in the list of beanclasses used to start Weld-SE
     */
    public Set<Class<?>> excludedClasses;

}
