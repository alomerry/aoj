public class Test01 {
    public static void main(String[] a){
        A m = new B();
        m.tt();
    }
}
class A{
    public void tt(){
        System.out.println("A");
    }
}
class B extends A{
    public void tt(){
        System.out.println("B");
    }
}