public class Main {
    public static void main(String[] args) {

        Base obj1 = new Base(new X());
        Base obj2 = new Derived(new Y(new Z()));

        Y y1 = new Y(new DerivedZ());
        y1.zobj.zval = 0;
        MyClass c1 = mk(obj1);
        System.out.println(c1);
        MyClass c2 = mk(obj2);
        int result1 = c2.getValue();
        assert (result1 != 0);
        int x = 100 / result1;
    }

    static MyClass mk(Base b) {
        return new MyClass(b);
    }
}

class MyClass {
    Base base;

    MyClass(Base base) {
        this.base = base;
    }

    int getValue() {
        assert (base.getClass() == Derived.class) && (base.getClass() != Base.class);
        return base.compute();
    }
}

class Base {
    X a1;
    protected int value;

    public Base() {
        value = 0;
    }

    public Base(X a1) {
        value = 0;
        this.a1 = a1;
    }

    public void setValue(int newValue) {
        value = newValue;
    }

    public int compute() {
        return value * a1.computeVal();
    }
}

class Derived extends Base {
    public Derived() {
        super();
    }

    public Derived(X base1) {
        value = 1;
        this.a1 = base1;
    }

    public int compute() {
        return value + a1.computeVal();
    }
}

class X {
    int val;

    public X() {
        val = -1;
    }

    public int computeVal() {
        return val * (-1);

    }
}

class Y extends X {
    Z zobj;

    public Y() {
        val = 5;
    }

    public Y(Z z1) {
        val = 5;
        zobj = z1;
    }

    public int computeVal() {
        assert (zobj.getClass() == Z.class) && (zobj.getClass() != DerivedZ.class);
        return val * zobj.getVal();
    }

}

class Z {
    int zval;

    public Z() {
        zval = 10;
    }

    public int getVal() {
        return zval * 10;
    }
}

class DerivedZ extends Z {
    public int getVal() {
        return zval;
    }
}