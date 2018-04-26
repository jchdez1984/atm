package nicebank;

public class CashSlot {
    private int contents;

    public int getContents() {
        return contents;
    }

    public void dispense(int dollars){
        contents = dollars;
    }

    public int contents(){
        return contents;
    }
}
