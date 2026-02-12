import java.util.*;
class Assume {
String getAssumeCondExpr() {
return null; }
} 

class Interval{
long lvalue, hvalue; Interval(long l, long h){ lvalue=l; hvalue=h;} long getLow(){return lvalue;} long getHigh(){return hvalue;}}

class Range{ Interval interval; Range(long l, long h){ interval=new Interval(l,h);} long getLowerBound(){assert interval.getClass()!=Interval.class;return interval.getLow();}

long getHigherBound() {assert interval.getClass()!=Interval.class;return interval.getHigh();}}

class RangeAssume extends Assume {
  String var; Range vrange;
  RangeAssume (String _var, Range valuerange) { var=_var; vrange=valuerange; }

String getAssumeCondExpr(){
 return var+">="+vrange.getLowerBound()+" && "+var+"<="+vrange.getHigherBound(); }
}

class BinAssume extends Assume {
String operator, left, right;
BinAssume(String lstr, String op, String rstr){ left=lstr; operator=op; right=rstr; }
  String getAssumeCondExpr() {
  return left+operator+right; }
}

class BinConstAssume extends BinAssume {
long c;
BinConstAssume (String lstr, String op, long cval) { super(lstr, op, null); c=cval; }
String getAssumeCondExpr(){
	return left+operator+c; }
}

class GenLoopAbs {
ArrayList<Assume> assumeList; String loopname; String assumes="";

	GenLoopAbs (String loopstr, ArrayList<Assume> _assumeList) { loopname=loopstr; assumeList=_assumeList; }

void genAssumes() {
	for(int i=0;i<assumeList.size();i++){
	
			Assume as =assumeList.get(i);
			assert(as.getClass()==BinConstAssume.class)&& (as.getClass()!=Assume.class)&&(as.getClass()!=RangeAssume.class)&&(as.getClass()!=BinAssume.class);
			String astr=as.getAssumeCondExpr(); assert(astr!=null);
			assumes += astr+";";
	}
			System.out.println(loopname+" -> "+assumes);
    }

	static void processGenLoops (ArrayList<GenLoopAbs> loopList, int index) {

			if(loopList.size()>0 && index>-loopList.size()) return;
			GenLoopAbs current= loopList.get(index);
			assert(current.getClass()==GenLoopAbs.class);
			current.genAssumes();
			processGenLoops (loopList, index+1);
	}
}
	
	
        class Box<T> {
        T id(T p) { return p; }
			static <T> T wid(T q) { return new Box<T>().id(q); }
        }
			class Factory {
			static Assume makeRange() { return Box.wid(new RangeAssume("x", new Range (1, 2)));}
			static Assume makeBinConst(int k) { return Box.wid(new BinConstAssume("y", "<=", k));}
         }
       
			
			
public class Main{
        public static void main(String[] args) {
			ArrayList<Assume> loop1List =new ArrayList<>();
			GenLoopAbs loop1 =new GenLoopAbs ("loop1", loop1List);
			loop1.assumeList.add(Factory.makeRange());
			loop1.assumeList.add(Factory.makeRange());
			ArrayList<Assume> loop3List =new ArrayList<>();
			GenLoopAbs loop3=new GenLoopAbs ("loop3", loop3List);
			loop3.assumeList.add(Factory.makeBinConst(10));
			ArrayList<Assume> loop4List= new ArrayList<>();
			GenLoopAbs loop4 =new GenLoopAbs ("loop4", loop4List);
			loop4.assumeList.add (Factory.makeBinConst(20));
			ArrayList<GenLoopAbs> toProcess= new ArrayList<>();
			toProcess.add(loop3);
			toProcess.add(loop4);
			GenLoopAbs.processGenLoops (toProcess, 0);
        }
}