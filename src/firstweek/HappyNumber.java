package firstweek;

public class HappyNumber {
    public boolean isHappy(int n) {
        // 非happy数会有一个特点，在循环过程中会出现4这个值
        while (n != 1 && n != 4) {
            int t = 0;
            while (n != 0) {
                t += (n % 10) * (n % 10);
                n /= 10;
            }
            n = t;
        }
        return n == 1;
    }
}
