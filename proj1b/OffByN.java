public class OffByN (int N) {
   
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == N;
    }
}
