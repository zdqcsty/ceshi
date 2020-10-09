package com.example.ceshi.bingfa;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public static void main(String[] args) {

/*
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        boolean expectedValue = true;
        boolean newValue      = false;

        boolean wasNewValueSet = atomicBoolean.compareAndSet(
                newValue, newValue);
        System.out.println(wasNewValueSet);
        System.out.println(atomicBoolean.get());*/

        String initialReference = "initial value referenced";

        AtomicReference<String> atomicStringReference =
                new AtomicReference<String>(initialReference);

        String newReference = "new value referenced";
        boolean exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
        System.out.println("exchanged: " + exchanged);
        System.out.println(atomicStringReference.get());
/*
        exchanged = atomicStringReference.compareAndSet(initialReference, newReference);
        System.out.println("exchanged: " + exchanged);
        System.out.println(atomicStringReference.get());*/

/*        String value = "hello world";
        ReferenceObject object = new AtomicReferenceTest.ReferenceObject(value);
        AtomicReference<ReferenceObject> atomicReference = new AtomicReference(object);
        final boolean b = atomicReference.compareAndSet(object, new ReferenceObject(value));
        System.out.println(b);
        System.out.println(atomicReference.get().getValue());*/

/*        // 顺道测试一下Integer的常量池
        AtomicReference<Integer> integerNewReference = new AtomicReference(new Integer(10));
        integerNewReference.compareAndSet(new Integer(10), new Integer(-10));
        System.out.println(integerNewReference.get().intValue());

        AtomicReference<Integer> integerValeOfReference = new AtomicReference(Integer.valueOf(10));
        integerValeOfReference.compareAndSet(new Integer(10), new Integer(-10));
        System.out.println(integerValeOfReference.get().intValue());

        integerValeOfReference.compareAndSet(Integer.valueOf(10), new Integer(-10));
        System.out.println(integerValeOfReference.get().intValue());*/
    }

    static class ReferenceObject {

        private String value;

        public ReferenceObject(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
