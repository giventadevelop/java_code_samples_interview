package com.boot2;


/**
 * This code demonstrates a key concept about constructor inheritance and accessibility in Java.
 * The commented-out private InheritanceParent() constructor proves that if a parent class has only
 * a private constructor, child classes cannot extend it because they cannot call the parent's constructor
 * (even implicitly). The InheritanceChild class shows that without a public or protected constructor in the
 * parent, inheritance becomes impossible, illustrating how constructor visibility directly controls whether a
 * class can be inherited from. This is a fundamental principle of Java's inheritance
 * mechanism where child classes must be able to access their parent's constructor.
 */
public class InheritanceParent {
    /*with private InheritanceParent()  you won't be able to extend'*/
   /* private InheritanceParent() {
    }*/
}
