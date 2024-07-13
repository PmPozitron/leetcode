import org.w3c.dom.ls.LSOutput;

/*
https://leetcode.com/problems/first-bad-version/
 */
public class VersionControl {
    public static void main(String[] args) {
        System.out.println(firstBadVersion(2126753390));
    }
    protected static boolean isBadVersion(int version) {
        return version >= 1702766719;
    }

    /*
    123 4 56
    l=1,r=6,cur=3->false,l=4
     */
    public static int firstBadVersion(int n) {
        int left = 1;
        int right = n;
        while (true) {
            long l = ((long)right + left)/2;
            int cur = (int)l;
            // int cur = (right + left) / 2;
            System.out.println(cur);
            if (isBadVersion(cur)) {
                System.out.printf("left %d, right %d, cur %d\n", left, right, cur);
                if (right == left)
                    return right;
                right = cur;
            } else {
                left = cur+1;
            }
        }
    }
}
