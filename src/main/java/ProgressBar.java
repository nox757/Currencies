public class ProgressBar {
    private int num = 5;
    public void outProgress(){
        System.out.print("\r");
        for (int i = 0; i < num; i++) {
            System.out.print("*");
        }
        num += 5;
    }
}
