class Base {
protected int v;
public Base (){
 v=0;
}

public int compute (){
return v;
}
}

class Derived extends Base {
public Derived () {
v = 10;
}

public int compute () {
return v+(int) Math. pow (2,2);
}
}

class Wrap {
Base b;
Wrap (Base _b){
this.b = _b;
}

int getVal (){
assert (b instanceof Derived && b.getClass() != Base.class);
return b.compute ();
 }

 void desc () {
System.out.println(" Wrap.");
 }
}

public class Main{
 public static void main(String [] s){
 int n= Verifier.nondetInt(), r = 0, i = 0;

 Base o= new Base ();

 while (i <= n) {

    Wrap w1 = mk(o) ;

    w1. desc ();

    o = new Derived ();

    Wrap w2 = mk(o) ;

    r = w2.getVal ();

    assert (r != 0) ;

    i++;
}

 }

 static Wrap mk(Base o)
 {
  return new Wrap (o);
 }
}