class Base {
	protected int value;

	public Base() {
		value = 0;
	}

	public int compute() {
		return value;
	}
}

class Derived extends Base {
	public Derived() {
		value = 10;
	}

	public int compute() {
		return value * 2;
	}
}

class MyClass {
	Base base;

	MyClass(Base base) {
		this.base = base;
	}

	int getValue() {
		assert (this.base.getClass() == Derived.class && this.base.getClass() != Base.class);
		return base.compute();
	}
}

public class Main{
public static void main(String[] args)
{
Base obj1 =new Base();
Base obj2 =new Derived();
int result = 0;
MyClass c1 = mk(obj1);
System.out.println(c1);
MyClass c2 = mk(obj2);
result = c2.getValue();
assert(result != 0);
int x =100/result;
}
static MyClass mk(Base b)
{
	return new MyClass(b);
}
}


	

	
		
	

	
		
		
		
		
	
	
		
	

	
	
		
	

	
		
	
	
		
		
		
	